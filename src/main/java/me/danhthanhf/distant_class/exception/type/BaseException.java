package me.danhthanhf.distant_class.exception.type;

public abstract class BaseException extends RuntimeException{
    private final String errorCode;
    private final String message;

    protected BaseException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.message = message;
    }

    protected BaseException(String errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
        this.message = message;
    }
}
