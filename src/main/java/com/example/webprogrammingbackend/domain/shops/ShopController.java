package com.example.webprogrammingbackend.domain.shops;


import com.example.webprogrammingbackend.domain.members.MemberRepository;
import com.example.webprogrammingbackend.domain.members.UserPrincipal;
import com.example.webprogrammingbackend.global.exception.DomainException;
import com.example.webprogrammingbackend.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ShopController {

    private final ShopService shopService;
    @PostMapping("/shop")
    public ResponseEntity enrollShopInfo(ShopEnrollForm shopEnrollForm) throws DomainException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UUID userId = UUID.fromString(authentication.getName());
        ShopDto shopDto = shopService.shopEnroll(shopEnrollForm, userId);
        return ResponseEntity.ok().body(shopDto);
    }

    @GetMapping("/shop")
    public ResponseEntity getShop()  {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UUID userId = UUID.fromString(authentication.getName());
        try {
            return ResponseEntity.ok(shopService.getShop(userId));
        } catch(DomainException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


    @GetMapping("/shop/all")
    public ResponseEntity getAllShop(){
        return ResponseEntity.ok( shopService.getAllShop());
    }

}
