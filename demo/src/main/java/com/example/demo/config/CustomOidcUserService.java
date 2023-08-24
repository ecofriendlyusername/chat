package com.example.demo.config;

import com.example.demo.entity.Member;
import com.example.demo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

@Component
@Primary
@RequiredArgsConstructor
public class CustomOidcUserService extends OidcUserService {
    private final MemberRepository memberRepository;

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUser defaultOidcUser = super.loadUser(userRequest);

        OidcIdToken idToken = defaultOidcUser.getIdToken();

        Map<String,Object> claims = idToken.getClaims();

        String email = (String) claims.get("email");
        String name = (String) claims.get("name");

        for (String key : claims.keySet()) {
            System.out.println("key : " + key + ", value : " + claims.get(key).toString());
        }

        Member member;

        if (!memberRepository.existsByEmail(email)) {
            member = Member.builder()
                    .name(name)
                    .mainChannelDestination("mc-" + UUID.randomUUID())
                    .email(email)
                    .build();
            memberRepository.save(member);
        }

        return defaultOidcUser;
    }

}
