package org.andy.chatfybackend.auth.auth;

import lombok.Builder;

import java.io.Serializable;


@Builder
public record JwtResponse(String token) implements Serializable {
}
