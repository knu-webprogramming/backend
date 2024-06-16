package com.example.webprogrammingbackend.domain.shops;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ShopEnrollForm {

    private String name;

    // TODO 주소 추가

    private String phoneNum;

    private String ownerName;

    private Integer maxStamps;

    private String reward;

    private MultipartFile image;

    private String placeName;

    private String addressName;

    private String y;

    private String x;

    private String stampType;

}
