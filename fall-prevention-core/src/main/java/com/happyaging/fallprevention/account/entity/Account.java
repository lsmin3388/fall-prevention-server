package com.happyaging.fallprevention.account.entity;

import java.util.ArrayList;
import java.util.List;

import com.happyaging.fallprevention.base.BaseAuditEntity;
import com.happyaging.fallprevention.region.entity.Region;
import com.happyaging.fallprevention.senior.entity.Senior;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account extends BaseAuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long id;

    @NotBlank
    @Column(name = "username", nullable = false)
    private String username;

    @Email(message = "이메일 형식이 아닙니다.")
    @NotBlank
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @NotBlank
    @Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
        message = "비밀번호는 최소 8자리이며, 대소문자, 숫자, 특수문자를 포함해야 합니다."
    )
    @Column(name = "password", nullable = false)
    private String password;

    @NotBlank
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private AccountRole role;

    @ManyToOne
    @JoinColumn(name = "region_id")
    private Region region;

    @OneToMany(mappedBy = "account")
    private List<Senior> senior = new ArrayList<>();

    @Builder
    public Account(String username, String email, String password, String phoneNumber, AccountRole role, Region region,
        List<Senior> senior) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.region = region;
        this.senior = senior;
    }
}
