package com.happyaging.fallprevention.account.domain;

/**
 * 역할
 * MANAGER_PENDING: 매니저 승인 대기 (시니어를 관리하려고 신청한 사람)
 * MANAGER: 매니저 (시니어를 관리하는 사람)
 * ADMIN: 관리자 (시스템 관리자)
 */
public enum AccountRole {
    MANAGER_PENDING,
    MANAGER,
    ADMIN
}
