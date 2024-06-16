package com.example.webprogrammingbackend.domain.coupons;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CouponDto {

    private Integer stamps;

    private Integer maxStamps;

    private String reward;

    private String stampType;

    private UUID shopId;

    @Builder

    public CouponDto(Integer stamps, Integer maxStamps, String reward, String stampType, UUID shopId) {
        this.stamps = stamps;
        this.maxStamps = maxStamps;
        this.reward = reward;
        this.stampType = stampType;
        this.shopId = shopId;
    }
}
