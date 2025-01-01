package com.happyaging.fallprevention.senior.entity;

import com.happyaging.fallprevention.account.entity.Account;
import com.happyaging.fallprevention.base.BaseAuditEntity;
import com.happyaging.fallprevention.senior.usecase.dto.SeniorUpdateDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Senior extends BaseAuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "senior_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private Integer birthYear;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SeniorSex sex;

    @Pattern(regexp = "^\\+?[0-9]{1,3}-?[0-9]{3,4}-?[0-9]{4}$")
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "relation", nullable = false)
    private Relationship relation;

    @Column(name = "memo", length = 500)
    private String memo;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @Builder
    public Senior(String name, String address, Integer birthYear, SeniorSex sex, String phoneNumber,
        Relationship relation,
        String memo, Account account) {
        this.name = name;
        this.address = address;
        this.birthYear = birthYear;
        this.sex = sex;
        this.phoneNumber = phoneNumber;
        this.relation = relation;
        this.memo = memo;
        this.account = account;
    }

    public void update(SeniorUpdateDto updateDto) {
        if (updateDto.name() != null) this.name = updateDto.name();
        if (updateDto.address() != null) this.address = updateDto.address();
        if (updateDto.birthYear() != null) this.birthYear = updateDto.birthYear();
        if (updateDto.sex() != null) this.sex = updateDto.sex();
        if (updateDto.phoneNumber() != null) this.phoneNumber = updateDto.phoneNumber();
        if (updateDto.relationship() != null) this.relation = updateDto.relationship();
        if (updateDto.memo() != null) this.memo = updateDto.memo();
    }
}
