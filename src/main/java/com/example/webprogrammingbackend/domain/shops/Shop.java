package com.example.webprogrammingbackend.domain.shops;

import com.example.webprogrammingbackend.domain.coupons.Coupon;
import com.example.webprogrammingbackend.domain.members.Member;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "shop_id", length = 32, updatable = false)
    private UUID id;

    private String name;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    // TODO 주소 추가

    private String phoneNum;

    private String ownerName;

    @OneToMany(mappedBy = "shop", fetch = FetchType.LAZY)
    private List<Coupon> coupons;

    private Integer maxStamps;

    private String reward;

    private String profileImage;

    private String placeName;

    private String addressName;

    private String y;

    private String x;

    @Builder

    public Shop(UUID id, String name, Member member, String phoneNum, String ownerName, List<Coupon> coupons, Integer maxStamps, String reward, String profileImage, String placeName, String addressName, String y, String x) {
        this.id = id;
        this.name = name;
        this.member = member;
        this.phoneNum = phoneNum;
        this.ownerName = ownerName;
        this.coupons = coupons;
        this.maxStamps = maxStamps;
        this.reward = reward;
        this.profileImage = profileImage;
        this.placeName = placeName;
        this.addressName = addressName;
        this.y = y;
        this.x = x;
    }
}
