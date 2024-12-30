package org.example.expert.application.tokenprovider;

public interface TokenSaver {

    void save(String token, Long userId);
}
