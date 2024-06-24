package com.example.webprogrammingbackend.domain.customers;

import com.example.webprogrammingbackend.domain.members.Member;
import com.example.webprogrammingbackend.domain.members.MemberRepository;
import com.example.webprogrammingbackend.domain.shops.Shop;
import com.example.webprogrammingbackend.domain.shops.ShopDto;
import com.example.webprogrammingbackend.global.exception.DomainException;
import com.example.webprogrammingbackend.global.exception.ErrorCode;
import com.example.webprogrammingbackend.global.s3.AWSS3FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.w3c.dom.DOMException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.example.webprogrammingbackend.global.s3.AWSS3FileService.extractExt;
import static com.example.webprogrammingbackend.global.s3.AWSS3FileService.getImageUrl;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final MemberRepository memberRepository;
    private final CustomerRepository customerRepository;
    private final AWSS3FileService awss3FileService;

    public CustomerDto enrollCustomer(UUID userId, CustomerEnrollForm customerEnrollForm) throws DomainException {
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new DomainException(ErrorCode.NOT_FOUND_DATA, "존재하지 않는 유저입니다."));

        Optional<Customer> existingCustomer = customerRepository.findByMemberId(userId);

        Customer customer;
        if (existingCustomer.isPresent()) {
            // Update existing customer
            customer = existingCustomer.get();
            customer.setName(customerEnrollForm.getName());
            if(customerEnrollForm.getImage() != null)
                customer.setProfileImage(userId.toString()  + extractExt(customerEnrollForm.getImage().getOriginalFilename()));
        } else {
            // Create new customer
            customer = new Customer();
            customer.setMember(member);
            customer.setName(customerEnrollForm.getName());
            if(customerEnrollForm.getImage() != null)
                customer.setProfileImage(userId.toString()  + extractExt(customerEnrollForm.getImage().getOriginalFilename()));
        }

        if(customerEnrollForm.getImage() != null)
            awss3FileService.putObject(customerEnrollForm.getImage(), "cus", userId.toString()  + extractExt(customerEnrollForm.getImage().getOriginalFilename()));

        Customer savedCustomer = customerRepository.save(customer);
        return CustomerDto.builder()
                .name(savedCustomer.getName())
                .customerId(customer.getId())
                .profileImageUrl(getImageUrl("cus", savedCustomer.getProfileImage()))
                .build();
    }

    public CustomerDto getCustomer(UUID userId) throws DomainException {
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new DomainException(ErrorCode.NOT_FOUND_DATA, "존재하지 않는 유저입니다."));
        Customer customer = member.getCustomer();
        if (customer == null) {
            throw new DomainException(ErrorCode.NOT_FOUND_DATA, "해당 유저는 고객정보를 가지고 있지 않습니다.");
        }
        return CustomerDto.builder()
                .name(customer.getName())
                .customerId(customer.getId())
                .profileImageUrl(getImageUrl("cus", customer.getProfileImage() ))
                .build();
    }

    public List getShops(UUID userId) throws DomainException {
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new DomainException(ErrorCode.NOT_FOUND_DATA, "존재하지 않는 유저입니다."));
        Customer customer = member.getCustomer();
        ArrayList<ShopDto> shops = new ArrayList<>();
        customer.getCoupons().forEach(
                (coupon) -> {
                    Shop shop = coupon.getShop();
                    shops.add(ShopDto.builder()
                            .name(shop.getName())
                            .phoneNum(shop.getPhoneNum())
                            .maxStamps(shop.getMaxStamps())
                            .ownerName(shop.getOwnerName())
                            .reward(shop.getReward())
                            .profileImageUrl(getImageUrl("shop", shop.getProfileImage()))
                            .y(shop.getY())
                            .x(shop.getX())
                            .shopId(shop.getId())
                            .addressName(shop.getAddressName())
                            .placeName(shop.getPlaceName())
                            .build());
                });
        return shops;
    }
}
