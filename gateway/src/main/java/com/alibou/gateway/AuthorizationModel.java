package com.alibou.gateway;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthorizationModel {
    private String url;
    private String method;
    private String token;
}
