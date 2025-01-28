package com.happyaging.fallprevention.senior;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.happyaging.fallprevention.security.details.PrincipalDetails;
import com.happyaging.fallprevention.senior.usecase.SeniorValidationUseCase;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MySeniorValidator implements ConstraintValidator<MySenior, Long> {
	private final SeniorValidationUseCase seniorValidationUseCase;

	@Override
	public boolean isValid(Long seniorId, ConstraintValidatorContext context) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !(authentication.getPrincipal() instanceof PrincipalDetails principalDetails)) {
			throw new UnauthorizedSeniorAccessException();
		}

		Long accountId = principalDetails.account().getId();
		boolean isValid = seniorValidationUseCase.isMySenior(accountId, seniorId);
		if (!isValid) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
				.addConstraintViolation();
			throw new UnauthorizedSeniorAccessException();
		}

		return true;
	}
}

