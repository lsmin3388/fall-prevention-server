package com.happyaging.fallprevention.youtube.service;

import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.PlaylistItem;
import com.google.api.services.youtube.model.PlaylistItemListResponse;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoListResponse;
import com.happyaging.fallprevention.youtube.exception.VideoNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class YouTubeService {

	private final YouTube youtube;
	private final String apiKey;

	public YouTubeService(@Value("${spring.youtube.api.key}") String apiKey) {
		this.apiKey = apiKey;
		this.youtube = new YouTube.Builder(
			new com.google.api.client.http.javanet.NetHttpTransport(),
			GsonFactory.getDefaultInstance(),  // JacksonFactory 대신 GsonFactory 사용
			request -> {}
		).setApplicationName("spring-youtube-api").build();
	}

	/**
	 * 특정 유튜브 동영상의 제목과 썸네일 가져오기
	 * @param videoId 유튜브 동영상 ID
	 * @return 제목과 썸네일 URL을 포함한 Map
	 * @throws IOException API 호출 중 오류 발생 시
	 */
	public Map<String, String> getVideoDetails(String videoId) throws IOException {
		try {
			YouTube.Videos.List videoRequest = youtube.videos().list("snippet");
			videoRequest.setKey(apiKey);
			videoRequest.setId(videoId);

			VideoListResponse videoResponse = videoRequest.execute();
			List<Video> videoList = videoResponse.getItems();

			if (videoList.isEmpty()) {
				throw new VideoNotFoundException();
			}

			Video video = videoList.get(0);
			String title = video.getSnippet().getTitle();
			String thumbnailUrl = video.getSnippet().getThumbnails().getDefault().getUrl();

			Map<String, String> videoDetails = new HashMap<>();
			videoDetails.put("title", title);
			videoDetails.put("thumbnailUrl", thumbnailUrl);

			return videoDetails;
		} catch (IOException e) {
			System.err.println("YouTube API 호출 중 오류 발생: " + e.getMessage());
			throw e;
		}
	}

	/**
	 * 특정 재생목록의 동영상 목록 가져오기
	 * @param playlistId
	 * @param maxResults
	 * @return
	 * @throws IOException
	 */
	public List<Map<String, String>> getPlaylistVideos(String playlistId, long maxResults) throws IOException {
		try {
			// 재생목록에서 동영상 가져오기
			YouTube.PlaylistItems.List playlistRequest = youtube.playlistItems().list("snippet");
			playlistRequest.setKey(apiKey);
			playlistRequest.setPlaylistId(playlistId);
			playlistRequest.setMaxResults(maxResults);

			PlaylistItemListResponse playlistResponse = playlistRequest.execute();
			List<PlaylistItem> videoItems = playlistResponse.getItems();

			// 동영상 목록 생성
			List<Map<String, String>> videoList = new ArrayList<>();
			for (PlaylistItem item : videoItems) {
				Map<String, String> videoDetails = new HashMap<>();
				videoDetails.put("title", item.getSnippet().getTitle());
				videoDetails.put("thumbnailUrl", item.getSnippet().getThumbnails().getDefault().getUrl());
				videoDetails.put("videoId", item.getSnippet().getResourceId().getVideoId());
				videoList.add(videoDetails);
			}

			return videoList;
		} catch (IOException e) {
			System.err.println("YouTube API 호출 중 오류 발생: " + e.getMessage());
			throw e;
		}
	}
}
