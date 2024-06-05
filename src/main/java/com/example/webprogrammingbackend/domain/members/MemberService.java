package com.example.webprogrammingbackend.domain.members;

import com.example.webprogrammingbackend.global.exception.DomainException;
import com.example.webprogrammingbackend.global.exception.ErrorCode;
import com.example.webprogrammingbackend.global.security.OAuth2Attribute;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @SneakyThrows
    public MemberDto getMember(UUID memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new DomainException(ErrorCode.NOT_FOUND_DATA));

        return MemberDto.toDto(member);

    }
    public Member upsertMemberByOAuth(OAuth2Attribute attribute) {
        String attributeId = attribute.getAttributeId();
        AuthType auth = attribute.getAuth();


        Member member = memberRepository.findMemberByAttributeIdAndAuth(attributeId, auth)
                .orElseGet(() -> Member.toEntity(attribute));

        if (member.getNickname() == null) {
            member.setNickname(generateRandomNickname());
        }

        return memberRepository.save(member);

    }

    public Member getMemberByAuthentication(Authentication authentication) throws DomainException {
        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
        String registrationId = oauthToken.getAuthorizedClientRegistrationId();
        AuthType authType = AuthType.valueOfKey(registrationId);
        String attributeId = authentication.getName();
        return memberRepository.findMemberByAttributeIdAndAuth(attributeId, authType)
                .orElseThrow(() -> new DomainException(ErrorCode.NOT_FOUND_DATA));
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UUID memberId = UUID.fromString(username);
        //TODO 추후 캐싱 기능 추가 - 매번 API 조회떄마다 멤버를 DB에서 조회하는 것이 비효율적
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new UsernameNotFoundException(ErrorCode.NOT_FOUND_DATA.getDetail()));
        return UserPrincipal.create(member);
    }


    private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWER = UPPER.toLowerCase();
    private static final String DIGITS = "0123456789";
    private static final String ALPHANUM = UPPER + LOWER + DIGITS;
    private static final int LENGTH = 8; // 닉네임 길이를 설정할 수 있습니다.

    private final Random random = new Random();

    private String generateRandomNickname() {
        StringBuilder nickname = new StringBuilder(LENGTH);
        for (int i = 0; i < LENGTH; i++) {
            nickname.append(ALPHANUM.charAt(random.nextInt(ALPHANUM.length())));
        }
        return nickname.toString();
    }
}