package com.example.webprogrammingbackend.domain.coupons;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;


public interface CouponRepository extends JpaRepository<Coupon, UUID> {
}
