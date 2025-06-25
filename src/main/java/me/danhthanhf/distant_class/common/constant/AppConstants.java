package me.danhthanhf.distant_class.common.constant;

import org.springframework.beans.factory.annotation.Value;

import java.util.List;

public final class AppConstants {
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String JWT_TOKEN_PREFIX = "Bearer ";
    // header
    public static final String CONTENT_TYPE_JSON = "application/json";
    public static final String CHARSET_UTF_8 = "UTF-8";
    // Qualifiers
    public static final String REDIS_TEMPLATE_STRING = "redisTemplateString";
    public static final String REDIS_TEMPLATE_OBJECT = "redisTemplateObject";
    public static final String REDIS_TEMPLATE_GENERIC = "redisTemplateGeneric";

    // Api, header, security constants
    @Value("${api.base.path}")
    public static String API_BASE_PATH;
    public static final List<String> WHITE_LIST_API = List.of(
            API_BASE_PATH + "/auth/**",
            API_BASE_PATH + "/public/**"
    );


    private AppConstants() {
        throw new IllegalStateException("This is a constant class and cannot be instantiated");
    }
}
