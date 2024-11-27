package com.happyaging.fallprevention.region.entity;

import com.happyaging.fallprevention.base.BaseAuditEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Region extends BaseAuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "region_id")
    private Long id;

    @Column(name = "region_name", nullable = false)
    private String regionName;

    @Builder
    public Region(String regionName) {
        this.regionName = regionName;
    }
}
