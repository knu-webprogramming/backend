package com.example.webprogrammingbackend.domain.coupons;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CouponDto {

    private Integer stamps;

    private Integer maxStamps;

    private String reward;

    @Builder
    public CouponDto(Integer stamps, Integer maxStamps, String reward) {
        this.stamps = stamps;
        this.maxStamps = maxStamps;
        this.reward = reward;
    }
}
