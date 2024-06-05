package com.example.webprogrammingbackend.domain.images;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Image {

    @Id
    @GeneratedValue
    @Column(name = "image_id")
    private Long id;

    private String originalFileName;

    private String storeFileName;

}