package org.example.admin.model.result;

public record UserLoginResult(String token,
                              String refreshToken,
                              String username,
                              Boolean superAdmin,
                              String avatar) {
}
