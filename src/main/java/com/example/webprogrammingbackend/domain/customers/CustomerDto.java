package com.example.webprogrammingbackend.domain.customers;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
public class CustomerDto {

    private String name;

    private String profileImageUrl;

    private UUID customerId;

    @Builder

    public CustomerDto(String name, String profileImageUrl, UUID customerId) {
        this.name = name;
        this.profileImageUrl = profileImageUrl;
        this.customerId = customerId;
    }
}
