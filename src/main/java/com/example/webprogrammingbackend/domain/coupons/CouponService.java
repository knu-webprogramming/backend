package com.example.webprogrammingbackend.domain.coupons;

import com.example.webprogrammingbackend.domain.customers.Customer;
import com.example.webprogrammingbackend.domain.customers.CustomerRepository;
import com.example.webprogrammingbackend.domain.members.Member;
import com.example.webprogrammingbackend.domain.members.MemberRepository;
import com.example.webprogrammingbackend.domain.shops.Shop;
import com.example.webprogrammingbackend.domain.shops.ShopRepository;
import com.example.webprogrammingbackend.global.exception.DomainException;
import com.example.webprogrammingbackend.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;
    private final MemberRepository memberRepository;
    private final ShopRepository shopRepository;
    private final CustomerRepository customerRepository;

    public CouponDto enrollCoupon(UUID userId, UUID shopId) throws DomainException {
        Member member = memberRepository.findById(userId)
                .orElseThrow(()-> new DomainException(ErrorCode.NOT_FOUND_DATA, "존재하지 않는 유저입니다."));
        Customer customer = member.getCustomer();
        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new DomainException(ErrorCode.NOT_FOUND_DATA, "존재하지 않는 가게입니다."));
        Coupon coupon = Coupon.builder()
                .stamps(0)
                .reward(shop.getReward())
                .maxStamps(shop.getMaxStamps())
                .shop(shop)
                .customer(customer)
                .build();
        Coupon savedCoupon = couponRepository.save(coupon);
        return CouponDto.builder()
                .stamps(0)
                .maxStamps(savedCoupon.getMaxStamps())
                .reward(savedCoupon.getReward())
                .build();
    }

    public CouponDto getCoupon(UUID userID, UUID shopId) throws DomainException {
        Member member = memberRepository.findById(userID)
                .orElseThrow(() -> new DomainException(ErrorCode.NOT_FOUND_DATA, "존재하지 않는 유저입니다."));
        Customer customer = member.getCustomer();
        List<Coupon> coupons = customer.getCoupons();
        for (Coupon coupon : coupons) {
            if(coupon.getShop().getId().toString().equals(shopId.toString())){
                return CouponDto.builder()
                        .reward(coupon.getReward())
                        .stamps(coupon.getStamps())
                        .maxStamps(coupon.getMaxStamps())
                        .build();
            }
        }
        return new CouponDto();
    }

    public List getCoupons(UUID userId) throws DomainException {
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new DomainException(ErrorCode.NOT_FOUND_DATA, "존재하지 않는 유저입니다."));
        Customer customer = member.getCustomer();
        List<CouponDto> coupons = new ArrayList();
        customer.getCoupons().forEach((coupon) -> coupons.add(
                CouponDto.builder()
                        .stamps(coupon.getStamps())
                        .maxStamps(coupon.getMaxStamps())
                        .reward(coupon.getReward())
                        .build()
        ) );
        return coupons;
    }
}
