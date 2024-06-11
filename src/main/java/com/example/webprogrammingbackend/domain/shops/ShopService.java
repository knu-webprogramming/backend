package com.example.webprogrammingbackend.domain.shops;

import com.amazonaws.services.s3.AmazonS3Client;
import com.example.webprogrammingbackend.domain.coupons.CouponRepository;
import com.example.webprogrammingbackend.domain.members.Member;
import com.example.webprogrammingbackend.domain.members.MemberRepository;
import com.example.webprogrammingbackend.global.exception.DomainException;
import com.example.webprogrammingbackend.global.exception.ErrorCode;
import com.example.webprogrammingbackend.global.s3.AWSS3FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.example.webprogrammingbackend.global.s3.AWSS3FileService.extractExt;
import static com.example.webprogrammingbackend.global.s3.AWSS3FileService.getImageUrl;

@Service
@RequiredArgsConstructor
public class ShopService {

    private final ShopRepository shopRepository;
    private final AWSS3FileService awss3FileService;
    private final MemberRepository memberRepository;


    public ShopDto shopEnroll(ShopEnrollForm shopEnrollForm, UUID userId) throws DomainException {
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new DomainException(ErrorCode.NOT_FOUND_DATA, "존재하지 않는 유저입니다."));

        Optional<Shop> existingShop = shopRepository.findByMemberId(userId);

        Shop shop;
        if (existingShop.isPresent()) {
            // Update existing customer
            shop = existingShop.get();
            shop.setName(shopEnrollForm.getName());
            shop.setPhoneNum(shopEnrollForm.getPhoneNum());
            shop.setOwnerName(shopEnrollForm.getOwnerName());
            shop.setMember(member);
            shop.setReward(shopEnrollForm.getReward());
            shop.setMaxStamps(shopEnrollForm.getMaxStamps());
            shop.setProfileImage(userId.toString() + extractExt(shopEnrollForm.getImage().getOriginalFilename()));
            shop.setY(shopEnrollForm.getY());
            shop.setX(shopEnrollForm.getX());
            shop.setAddressName(shopEnrollForm.getAddressName());
            shop.setPlaceName(shopEnrollForm.getPlaceName());
        } else {
            // Create new customer
            shop = new Shop();
            shop.setName(shopEnrollForm.getName());
            shop.setPhoneNum(shopEnrollForm.getPhoneNum());
            shop.setOwnerName(shopEnrollForm.getOwnerName());
            shop.setMember(member);
            shop.setReward(shopEnrollForm.getReward());
            shop.setMaxStamps(shopEnrollForm.getMaxStamps());
            shop.setProfileImage(userId.toString() + extractExt(shopEnrollForm.getImage().getOriginalFilename()));
            shop.setY(shopEnrollForm.getY());
            shop.setX(shopEnrollForm.getX());
            shop.setAddressName(shopEnrollForm.getAddressName());
            shop.setPlaceName(shopEnrollForm.getPlaceName());
        }


        Shop savedShop = shopRepository.save(shop);
        awss3FileService.putObject(shopEnrollForm.getImage(), "shop", userId.toString()  + extractExt(shopEnrollForm.getImage().getOriginalFilename()));
        return ShopDto.builder()
                .name(savedShop.getName())
                .reward(savedShop.getReward())
                .ownerName(savedShop.getOwnerName())
                .phoneNum(savedShop.getPhoneNum())
                .maxStamps(savedShop.getMaxStamps())
                .shopId(savedShop.getId())
                .profileImageUrl(getImageUrl("shop", savedShop.getProfileImage()))
                .y(shopEnrollForm.getY())
                .x(shopEnrollForm.getX())
                .addressName(shopEnrollForm.getAddressName())
                .placeName(shopEnrollForm.getPlaceName())
                .build();
    }

    public ShopDto getShop(UUID userId) throws DomainException {
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new DomainException(ErrorCode.NOT_FOUND_DATA, "존재하지 않는 유저입니다."));
        Shop shop = member.getShop();
        if (shop == null) {
            throw new DomainException(ErrorCode.NOT_FOUND_DATA, "해당 유저는 상점을 가지고 있지 않습니다.");
        }
        return ShopDto.builder()
                .shopId(shop.getId())
                .name(shop.getName())
                .reward(shop.getReward())
                .phoneNum(shop.getPhoneNum())
                .maxStamps(shop.getMaxStamps())
                .ownerName(shop.getOwnerName())
                .y(shop.getY())
                .x(shop.getX())
                .addressName(shop.getAddressName())
                .placeName(shop.getPlaceName())
                .profileImageUrl(getImageUrl("shop",shop.getProfileImage()))
                .build();
    }

    public List getAllShop() {
        List<ShopDto> shopDtos = new ArrayList<>();
        shopRepository.findAll().forEach(
                (shop) -> {
                    shopDtos.add(
                            ShopDto.builder()
                                    .shopId(shop.getId())
                                    .name(shop.getName())
                                    .reward(shop.getReward())
                                    .phoneNum(shop.getPhoneNum())
                                    .maxStamps(shop.getMaxStamps())
                                    .ownerName(shop.getOwnerName())
                                    .y(shop.getY())
                                    .x(shop.getX())
                                    .addressName(shop.getAddressName())
                                    .placeName(shop.getPlaceName())
                                    .profileImageUrl(getImageUrl("shop",shop.getProfileImage()))
                                    .build());
                }

        );
        return shopDtos;
    }

}
