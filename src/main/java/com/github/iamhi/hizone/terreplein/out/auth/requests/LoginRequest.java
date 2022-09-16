package com.github.iamhi.hizone.terreplein.out.auth.requests;

public record LoginRequest(
    String username,
    String password
) {
}
