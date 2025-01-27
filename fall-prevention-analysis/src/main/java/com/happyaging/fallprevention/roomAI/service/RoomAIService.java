package com.happyaging.fallprevention.roomAI.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.happyaging.fallprevention.account.entity.Account;
import com.happyaging.fallprevention.gpt.service.GPTService;
import com.happyaging.fallprevention.roomAI.dto.RoomAIRequest;
import com.happyaging.fallprevention.roomAI.dto.RoomAIResponse;
import com.happyaging.fallprevention.roomAI.entity.RoomAI;
import com.happyaging.fallprevention.roomAI.entity.RoomAIPrompt;
import com.happyaging.fallprevention.roomAI.repository.RoomAIImageRepository;
import com.happyaging.fallprevention.roomAI.repository.RoomAIPromptRepository;
import com.happyaging.fallprevention.roomAI.repository.RoomAIRepository;
import com.happyaging.fallprevention.roomAI.usecase.RoomAIUseCase;
import com.happyaging.fallprevention.storage.service.StorageService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoomAIService implements RoomAIUseCase {

	private final RoomAIRepository roomAIRepository;
	private final RoomAIPromptRepository roomAIPromptRepository;
	private final RoomAIImageRepository roomAIImageRepository;
	private final StorageService storageService;
	private final GPTService gptService;

	@Override
	@Transactional
	public RoomAIResponse analyzeRoom(Account account, List<RoomAIRequest> roomAIRequest) {
		// 1) RoomAI 엔티티 우선 생성
		RoomAI roomAI = RoomAI.builder()
			.roomAIPrompt(new ArrayList<>())
			.account(account)
			.build();
		roomAI = roomAIRepository.save(roomAI);

		// 2) RoomAIPrompt 엔티티 생성
		List<RoomAIPrompt> roomAIPrompt = RoomAIRequest.toRoomAiPrompt(roomAIRequest);

		for (RoomAIPrompt pt : roomAIPrompt) {
			// 3) RoomAIPrompt 기반으로 Prompt 생성
			String prompt = createPrompt(pt);

			// 4) GPT API 질문 후 응답 저장
			String gptResult = gptService.callGPTVisionApi(prompt, pt.getImagesFilename());
			pt.updateResponse(gptResult);
		}

		// 5) RoomAIPrompt 저장
		roomAIPrompt = roomAIPromptRepository.saveAll(roomAIPrompt);

		// 6) RoomAI에 RoomAIPrompt 연관관계 연결
		roomAI.connectRoomAIPrompt(roomAIPrompt);

		// 7) RoomAI 저장
		roomAI = roomAIRepository.save(roomAI);

		// 8) DTO 변환
		return RoomAIResponse.from(roomAI);
	}

	private String createPrompt(RoomAIPrompt prompt) {
		try (InputStream is = getClass().getResourceAsStream("/RoomAI/prompt.txt")) {
			if (is == null) {
				throw new FileNotFoundException("prompt.txt not found in resources/RoomAI/");
			}
			String template = new String(is.readAllBytes(), StandardCharsets.UTF_8);

			template = template.replace("${RoomName}", prompt.getRoomName());
			template = template.replace("${RoomCategory}", prompt.getRoomCategory().name());

			return template;
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}
}
