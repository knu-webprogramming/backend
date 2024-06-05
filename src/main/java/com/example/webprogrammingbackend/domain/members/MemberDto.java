package com.example.webprogrammingbackend.domain.members;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MemberDto {

    private UUID id;
    private String nickname;
    private RoleType role;
    private AuthType auth;
    private LocalDateTime createdAt;

    @Builder
    public MemberDto(UUID id, String nickname, RoleType role, AuthType auth, LocalDateTime createdAt) {
        this.id = id;
        this.nickname = nickname;
        this.role = role;
        this.auth = auth;
        this.createdAt = createdAt;
    }

    public static MemberDto toDto(Member entity) {
        return MemberDto.builder()
                .id(entity.getId())
                .nickname(entity.getNickname())
                .role(entity.getRole())
                .auth(entity.getAuth())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
