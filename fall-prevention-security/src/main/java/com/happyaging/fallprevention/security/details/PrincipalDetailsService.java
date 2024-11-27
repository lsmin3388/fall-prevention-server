package com.happyaging.fallprevention.security.details;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.happyaging.fallprevention.account.entity.Account;
import com.happyaging.fallprevention.account.persistence.AccountRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

	private final AccountRepository accountRepository;

	@Override
	public PrincipalDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Account account = accountRepository.findByEmail(email)
			.orElseThrow(() -> new UsernameNotFoundException("ACCOUNT_NOT_FOUND"));

		return new PrincipalDetails(account);
	}
}
