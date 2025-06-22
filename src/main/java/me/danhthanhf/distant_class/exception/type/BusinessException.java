package me.danhthanhf.distant_class.exception.type;

public class BusinessException extends BaseException{
    protected BusinessException(String errorCode, String message) {
        super(errorCode, message);
    }

    protected BusinessException(String errorCode, String message, Throwable cause) {
        super(errorCode, message, cause);
    }
}
