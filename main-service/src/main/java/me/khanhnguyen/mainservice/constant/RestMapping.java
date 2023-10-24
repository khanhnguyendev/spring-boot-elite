package me.khanhnguyen.mainservice.constant;


import java.util.Arrays;
import java.util.List;

public class RestMapping {
    public static final String[] WHITELIST = {
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/actuator1/**",
    };

    public static final List<String> REST_ORIGINS =
            Arrays.asList(
                    "http://localhost:3000",
                    "http://localhost:8080"
            );
}
