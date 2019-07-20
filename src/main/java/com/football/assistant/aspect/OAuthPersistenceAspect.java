package com.football.assistant.aspect;

import com.football.assistant.domain.User;
import com.football.assistant.service.UserService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Aspect
@Component
public class OAuthPersistenceAspect {

    @Autowired
    private OAuth2AuthorizedClientService authorizedClientService;

    @Autowired
    private UserService userService;

    @Before("execution(public * oauthAOP*(..))")
    public void test(JoinPoint joinPoint){
            OAuth2AuthenticationToken authentication = null;
            for (Object arg : joinPoint.getArgs()) {
                if (arg instanceof OAuth2AuthenticationToken) {
                    authentication = (OAuth2AuthenticationToken) arg;
                }
            }
            if (authentication == null) {
                return;
            }
            OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient(
                    authentication.getAuthorizedClientRegistrationId(), authentication.getName());
            String userInfoEndpointUri = client.getClientRegistration()
                    .getProviderDetails().getUserInfoEndpoint().getUri();
        if (!StringUtils.isEmpty(userInfoEndpointUri)) {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + client.getAccessToken()
                    .getTokenValue());
            HttpEntity entity = new HttpEntity("", headers);
            ResponseEntity<Map> response = restTemplate
                    .exchange(userInfoEndpointUri, HttpMethod.GET, entity, Map.class);
            Map userAttributes = response.getBody();
            User userInDB = userService.findUserByEmail(userAttributes.get("email").toString());
            if (userInDB == null) {
                User newUser = new User();
                newUser.setEmail(userAttributes.get("email").toString());
                newUser.setNickname(userAttributes.get("name").toString());
                newUser.setOauth2id(authentication.getName());
                newUser.setPassword("oauthtoken");
                userService.saveUser(newUser, "NORMAL");
            }
        }
    }
}
