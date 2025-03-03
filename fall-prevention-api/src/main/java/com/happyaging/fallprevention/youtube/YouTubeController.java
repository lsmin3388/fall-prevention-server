package com.happyaging.fallprevention.youtube;

import com.happyaging.fallprevention.youtube.service.YouTubeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/youtube")
@RequiredArgsConstructor
public class YouTubeController {

	private final YouTubeService youTubeService;

	/**
	 * 특정 유튜브 동영상의 제목과 썸네일 가져오기 API
	 * @param videoId 유튜브 동영상 ID
	 * @return 제목과 썸네일 URL을 포함한 JSON 응답
	 * @throws IOException API 호출 중 오류 발생 시
	 */
	@GetMapping("/video/details")
	public ResponseEntity<Map<String, String>> getVideoDetails(
		@RequestParam(name = "videoId") String videoId) throws IOException {
		return ResponseEntity.ok(youTubeService.getVideoDetails(videoId));
	}

	/**
	 * 특정 유튜브 재생목록의 동영상 목록 가져오기 API
	 * @param playlistId 유튜브 재생목록 ID
	 * @param maxResults 최대 결과 수 (기본값: 5)
	 * @return 동영상 제목과 썸네일 URL을 포함한 JSON 응답 목록
	 * @throws IOException API 호출 중 오류 발생 시
	 * PLcgCdNTzpN1iHjLXuON6k_lkg29cHDCMT(해피에이징 플레이리스트 ID 중 하나)
	 */
	@GetMapping("/playlist/videos")
	public ResponseEntity<List<Map<String, String>>> getPlaylistVideos
	(@RequestParam(name = "playlistId") String playlistId,
		@RequestParam(name = "maxResults", required = false, defaultValue = "10") long maxResults
	) throws IOException {
		return ResponseEntity.ok(youTubeService.getPlaylistVideos(playlistId, maxResults));
	}

}
