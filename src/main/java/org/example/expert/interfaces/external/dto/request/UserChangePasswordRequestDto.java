package org.example.expert.interfaces.external.dto.request;

public record UserChangePasswordRequestDto(String oldPassword, String newPassword) {

}
