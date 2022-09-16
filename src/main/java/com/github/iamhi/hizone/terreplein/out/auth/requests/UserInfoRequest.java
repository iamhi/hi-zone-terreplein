package com.github.iamhi.hizone.terreplein.out.auth.requests;

public record UserInfoRequest (
    String token,
    String userToken
) {
}
