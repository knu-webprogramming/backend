package com.example.webprogrammingbackend.domain.coupons;

import com.example.webprogrammingbackend.global.exception.DomainException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;

    @PostMapping("/coupon/{shopId}")
    public ResponseEntity enrollCoupon(@PathVariable UUID shopId) throws DomainException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UUID userId = UUID.fromString(authentication.getName());
        CouponDto couponDto = couponService.enrollCoupon(userId, shopId);
        return ResponseEntity.ok(couponDto);
    }

    @GetMapping("/coupon/{shopId}")
    public ResponseEntity getCoupon(@PathVariable UUID shopId) throws DomainException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UUID userId = UUID.fromString(authentication.getName());
        CouponDto couponDto = couponService.getCoupon(userId, shopId);
        return ResponseEntity.ok(couponDto);
    }

    @GetMapping("/coupon")
    public ResponseEntity getCoupons() throws DomainException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UUID userId = UUID.fromString(authentication.getName());
        List couponsDto = couponService.getCoupons(userId);
        return ResponseEntity.ok(couponsDto);
    }

    @PostMapping("/coupon/stamp/{shopId}")
    public ResponseEntity couponStamp(@PathVariable UUID shopId) throws DomainException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UUID userId = UUID.fromString(authentication.getName());
        CouponDto couponDto = couponService.couponStamp(userId, shopId);
        return ResponseEntity.ok(couponDto);
    }

    @PostMapping("/coupon/use/{shopId}")
    public ResponseEntity useCoupon(@PathVariable UUID shopId) throws DomainException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UUID userId = UUID.fromString(authentication.getName());
        CouponDto couponDto = couponService.useCoupon(userId, shopId);
        return ResponseEntity.ok(couponDto);
    }
}
