package com.example.webprogrammingbackend.domain.shops;

import com.example.webprogrammingbackend.domain.coupons.Coupon;
import com.example.webprogrammingbackend.domain.images.Image;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "shop_id", length = 32, updatable = false)
    private UUID id;

    @OneToMany(mappedBy = "shop", fetch = FetchType.LAZY)
    private List<Coupon> coupons;

    @OneToOne(fetch = FetchType.LAZY)
    private Image profileImage;
}
