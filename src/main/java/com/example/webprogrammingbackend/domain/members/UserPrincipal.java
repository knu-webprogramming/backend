package com.example.webprogrammingbackend.domain.members;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;

public class UserPrincipal implements UserDetails {

    private UUID id;
    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public UserPrincipal(UUID id, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    // 사용자의 권한 목록을 반환합니다.
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    // 계정 만료, 잠김, 비밀번호 만료, 사용 가능 여부에 대한 메서드들...
    // 이 예시에서는 모든 메서드를 true로 반환하도록 설정합니다.
    // 실제 구현에서는 사용자 엔티티의 상태를 반영하도록 로직을 수정해야 합니다.

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    // User 객체로부터 UserPrincipal 객체를 생성하는 정적 메서드
    public static UserPrincipal create(Member member) {
        Set<SimpleGrantedAuthority> authorities = Collections
                .singleton(new SimpleGrantedAuthority(member.getRole().getKey()));

        return new UserPrincipal(
                member.getId(),
                member.getId().toString(),  // 유저네임 대신 임시 사용
                null,
                authorities
        );
    }

}
