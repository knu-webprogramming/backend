package com.example.webprogrammingbackend.domain.customers;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CustomerEnrollForm {

    private String name;

    private MultipartFile image;
}
