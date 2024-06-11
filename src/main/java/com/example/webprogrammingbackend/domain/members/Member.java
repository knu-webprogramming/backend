package com.example.webprogrammingbackend.domain.members;

import com.example.webprogrammingbackend.domain.common.entity.BaseEntity;
import com.example.webprogrammingbackend.domain.customers.Customer;
import com.example.webprogrammingbackend.domain.shops.Shop;
import com.example.webprogrammingbackend.global.security.OAuth2Attribute;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Table(uniqueConstraints={
        @UniqueConstraint(
                name="attribute_id_auth_uk",
                columnNames={"attribute_id", "auth"} // DB 상의 column name 을 작성해야한다. (변수명X)
        )
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Member extends BaseEntity {

    /* 서비스 파라메터 */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "member_id", length = 36)
    private UUID id;
    private String nickname;
    @Enumerated(EnumType.STRING)
    private RoleType role;


    /* 간편로그인 파라메터 */
    @Column
    private String attributeId;     // 간편로그인 시 받는 고유 ID
    @Column(length = 30)
    private String socialName;
    @Enumerated(EnumType.STRING)
    private AuthType auth;

    @Column()
    private String email;
    @Column(length = 11)
    private String phone;
    @Column()
    private String profileImage;

    @OneToOne(mappedBy = "member")
    private Customer customer;

    @OneToOne(mappedBy = "member")
    private Shop shop;


    /* 토큰 관련 */
//    @OneToOne(mappedBy = "member")
//    private Token token;

    @Builder
    public Member(UUID id, String nickname, RoleType role,
                  String attributeId, String socialName, AuthType auth,
                  String email, String phone, String profileImage) {
        this.id = id;
        this.nickname = nickname;
        this.role = role;
        this.attributeId = attributeId;
        this.socialName = socialName;
        this.auth = auth;
        this.email = email;
        this.phone = phone;
        this.profileImage = profileImage;
    }

    public static Member toEntity(OAuth2Attribute attribute) {
        return Member.builder()
                .role(RoleType.USER)
                .attributeId(attribute.getAttributeId())
                .socialName(attribute.getName())
                .auth(attribute.getAuth())
                .email(attribute.getEmail())
                .profileImage(attribute.getPicture())
                .build();
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
