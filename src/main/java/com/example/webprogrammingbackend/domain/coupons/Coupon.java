package com.example.webprogrammingbackend.domain.coupons;

import com.example.webprogrammingbackend.domain.customers.Customer;
import com.example.webprogrammingbackend.domain.shops.Shop;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "coupon_id", length = 32, updatable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id")
    private Shop shop;

    private Integer stamps;

    private Integer maxStamps;

    private String reward;

    @Builder
    public Coupon(UUID id, Customer customer, Shop shop, Integer stamps, Integer maxStamps, String reward) {
        this.id = id;
        this.customer = customer;
        this.shop = shop;
        this.stamps = stamps;
        this.maxStamps = maxStamps;
        this.reward = reward;
    }
}
