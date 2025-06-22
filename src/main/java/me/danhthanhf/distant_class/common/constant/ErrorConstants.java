package me.danhthanhf.distant_class.common.constant;


public final class ErrorConstants {
    private ErrorConstants() {
        throw new IllegalStateException("This is a constant class and cannot be instantiated");
    }

    // Jwt error messages
    public static final String ERR_TOKEN_EXPIRED = "Token has expired";
    public static final String ERR_TOKEN_INVALID = "Invalid token format";
    public static final String ERR_TOKEN_MISSING = "Token is missing";
    public static final String ERR_TOKEN_SIGNATURE_INVALID = "Token signature is invalid";
    public static final String ERR_GENERIC_ERROR = "An unexpected error occurred while processing the request";
}
