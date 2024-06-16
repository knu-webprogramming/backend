package com.example.webprogrammingbackend.domain.shops;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ShopDto {

    private UUID shopId;

    private String name;

    private String phoneNum;

    private String ownerName;

    private Integer maxStamps;

    private String reward;

    private String profileImageUrl;

    private String placeName;

    private String addressName;

    private String y;

    private String x;

    private String stampType;

    @Builder

    public ShopDto(UUID shopId, String name, String phoneNum, String ownerName, Integer maxStamps, String reward, String profileImageUrl, String placeName, String addressName, String y, String x, String stampType) {
        this.shopId = shopId;
        this.name = name;
        this.phoneNum = phoneNum;
        this.ownerName = ownerName;
        this.maxStamps = maxStamps;
        this.reward = reward;
        this.profileImageUrl = profileImageUrl;
        this.placeName = placeName;
        this.addressName = addressName;
        this.y = y;
        this.x = x;
        this.stampType = stampType;
    }
}
