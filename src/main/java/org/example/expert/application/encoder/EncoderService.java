package org.example.expert.application.encoder;

public interface EncoderService {

    String encode(String password);
    boolean matches(String password, String encodedPassword);
}
