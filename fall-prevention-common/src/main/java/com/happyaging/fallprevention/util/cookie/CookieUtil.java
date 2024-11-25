package com.happyaging.fallprevention.util.cookie;

import jakarta.servlet.http.Cookie;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CookieUtil {
	public static Cookie createCookie(String key, String value, int expiry, String path, boolean isHttpOnly, boolean isSecure) {
		Cookie cookie = new Cookie(key, value);
		cookie.setMaxAge(expiry);
		cookie.setPath(path);
		cookie.setHttpOnly(isHttpOnly);
		cookie.setSecure(isSecure);

		return cookie;
	}

	public static Cookie deleteCookie(String key, String path, boolean isHttpOnly, boolean isSecure) {
		Cookie cookie = new Cookie(key, "");
		cookie.setMaxAge(0);
		cookie.setPath(path);
		cookie.setHttpOnly(isHttpOnly);
		cookie.setSecure(isSecure);
		return cookie;
	}
}