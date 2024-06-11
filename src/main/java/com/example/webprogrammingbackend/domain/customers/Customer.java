package com.example.webprogrammingbackend.domain.customers;

import com.example.webprogrammingbackend.domain.common.entity.BaseEntity;
import com.example.webprogrammingbackend.domain.coupons.Coupon;
import com.example.webprogrammingbackend.domain.members.Member;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
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
public class Customer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "customer_id", length = 32, updatable = false)
    private UUID id;

    private String name;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    private List<Coupon> coupons;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String profileImage;

    @Builder
    public Customer(UUID id, String name, List<Coupon> coupons, Member member, String profileImage, Map map) {
        this.id = id;
        this.name = name;
        this.coupons = coupons;
        this.member = member;
        this.profileImage = profileImage;
    }
}
