package com.happyaging.fallprevention.roomAI.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.happyaging.fallprevention.gpt.service.GPTService;
import com.happyaging.fallprevention.roomAI.entity.RoomAI;
import com.happyaging.fallprevention.roomAI.entity.RoomAIPrompt;
import com.happyaging.fallprevention.roomAI.exception.RoomAINotFoundException;
import com.happyaging.fallprevention.roomAI.repository.RoomAIPromptRepository;
import com.happyaging.fallprevention.roomAI.repository.RoomAIRepository;
import com.happyaging.fallprevention.roomAI.usecase.RoomAIUseCase;
import com.happyaging.fallprevention.roomAI.usecase.dto.request.RoomAIRequest;
import com.happyaging.fallprevention.roomAI.usecase.dto.response.RoomAIResponse;
import com.happyaging.fallprevention.senior.entity.Senior;
import com.happyaging.fallprevention.senior.persistence.SeniorRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoomAIService implements RoomAIUseCase {
	private final RoomAIRepository roomAIRepository;
	private final RoomAIPromptRepository roomAIPromptRepository;
	private final SeniorRepository seniorRepository;
	private final GPTService gptService;

	@Override
	@Transactional
	public RoomAIResponse analyzeRoom(Long seniorId, List<RoomAIRequest> roomAIRequest) {
		// 0) Senior 존재 확인
		Senior senior = seniorRepository.findById(seniorId)
			.orElseThrow(RoomAINotFoundException::new);

		// 1) RoomAI 엔티티 우선 생성
		RoomAI roomAI = RoomAI.builder()
			.senior(senior)
			.build();
		roomAI = roomAIRepository.save(roomAI);

		// 2) RoomAIPrompt 엔티티 생성
		Set<RoomAIPrompt> roomAIPrompt = RoomAIRequest.toRoomAiPrompt(roomAIRequest);

		for (RoomAIPrompt pt : roomAIPrompt) {
			// 3) RoomAIPrompt 기반으로 Prompt 생성
			String prompt = createPrompt(pt);

			// 4) GPT API 질문
			String gptResult = gptService.callGPTVisionApi(prompt, pt.getImagesFilename());

			// 5) RoomAIPrompt 엔티티 업데이트
			pt.updateResponse(gptResult);
			pt.connectRoomAI(roomAI);
		}

		// 6) RoomAIPrompt 저장
		List<RoomAIPrompt> savedRoomAIPrompt = roomAIPromptRepository.saveAll(roomAIPrompt);

		// 8) DTO 변환
		return RoomAIResponse.from(roomAI, savedRoomAIPrompt);
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

	@Override
	public List<String> getAnalysisDateList(Long seniorId) {
		List<LocalDateTime> analysisDateList = roomAIRepository.findCreatedAtBySeniorId(seniorId);
		if (analysisDateList.isEmpty()) return List.of();

		return analysisDateList.stream()
			.map(LocalDateTime::toString)
			.collect(Collectors.toList());
	}

	@Override
	public RoomAIResponse getAnalysisResult(Long seniorId, String date) {
		LocalDateTime createdAt = LocalDateTime.parse(date);
		RoomAI roomAI = roomAIRepository.findBySenior_IdAndCreatedAt(seniorId, createdAt)
			.orElseThrow(RoomAINotFoundException::new);

		return RoomAIResponse.from(roomAI);
	}
}
