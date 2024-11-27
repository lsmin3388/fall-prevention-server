package com.happyaging.fallprevention.region.exception;

import com.happyaging.fallprevention.exception.support.security.UnauthorizedException;

public class RegionNotFoundException extends UnauthorizedException {
	private static final String errorMsg = "REGION_NOT_FOUND";
	public RegionNotFoundException() {
		super(errorMsg);
	}
}
