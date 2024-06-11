package com.example.webprogrammingbackend.domain.customers;

import com.example.webprogrammingbackend.domain.members.Member;
import com.example.webprogrammingbackend.domain.members.MemberRepository;
import com.example.webprogrammingbackend.global.exception.DomainException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;


    @PostMapping("/customer")
    public ResponseEntity enrollCustomer(CustomerEnrollForm customerEnrollForm) throws DomainException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UUID userId = UUID.fromString(authentication.getName());
        return ResponseEntity.ok(customerService.enrollCustomer(userId,customerEnrollForm));
    }

    @GetMapping("/customer")
    public ResponseEntity getCustomer()  {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UUID userId = UUID.fromString(authentication.getName());
        try {
            CustomerDto customerDto = customerService.getCustomer(userId);
            return ResponseEntity.ok(customerDto);
        } catch (DomainException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/customer/shops")
    public ResponseEntity getShops() throws DomainException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UUID userId = UUID.fromString(authentication.getName());
        List shops = customerService.getShops(userId);
        return ResponseEntity.ok(shops);
    }

}
