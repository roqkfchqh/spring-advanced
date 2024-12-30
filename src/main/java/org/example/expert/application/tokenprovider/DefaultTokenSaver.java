package org.example.expert.application.tokenprovider;

import org.springframework.stereotype.Component;

@Component
public class DefaultTokenSaver implements TokenSaver {

    @Override
    public void save(String token, Long userId) {
        //토큰을 DB에 저장
    }
}