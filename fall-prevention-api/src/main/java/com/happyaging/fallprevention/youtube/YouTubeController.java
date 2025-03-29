package com.happyaging.fallprevention.youtube;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.happyaging.fallprevention.util.api.ApiResponse;
import com.happyaging.fallprevention.util.api.ApiSuccessResult;
import com.happyaging.fallprevention.youtube.service.YouTubeService;

import lombok.RequiredArgsConstructor;

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
	@PreAuthorize("hasRole('USER') and isAuthenticated()")
	public ResponseEntity<ApiSuccessResult<Map<String, String>>> getVideoDetails(
		@RequestParam(name = "videoId") String videoId
	) throws IOException {
		Map<String, String> videoDetails = youTubeService.getVideoDetails(videoId);
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(ApiResponse.success(HttpStatus.OK, videoDetails));
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
	@PreAuthorize("hasRole('USER') and isAuthenticated()")
	public ResponseEntity<ApiSuccessResult<List<Map<String, String>>>> getPlaylistVideos(
		@RequestParam(name = "playlistId") String playlistId,
		@RequestParam(name = "maxResults", required = false, defaultValue = "10") long maxResults
	) throws IOException {
		List<Map<String, String>> playlistVideos = youTubeService.getPlaylistVideos(playlistId, maxResults);
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(ApiResponse.success(HttpStatus.OK, playlistVideos));
	}

}
