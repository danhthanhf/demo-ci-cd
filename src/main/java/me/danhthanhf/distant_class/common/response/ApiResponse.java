package me.danhthanhf.distant_class.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
public class ApiResponse <T>{
    private String message;
    private T data;
    private boolean success;
    private Object errors; // contains error details ex: validation errors, etc.

    public static <T> ApiResponse<T> success(String message, T data) {
       ApiResponse<T> res = new ApiResponse<>();
       res.setSuccess(true);
       res.setMessage(message);
       res.setData(data);
       return res;
    }

    public static <T> ApiResponse<T> success(String message) {
        ApiResponse<T> res = new ApiResponse<>();
        res.setMessage(message);
        res.setSuccess(true);
        return res;
    }

    public static <T> ApiResponse<T> error(String message) {
        ApiResponse<T> res = new ApiResponse<>();
        res.setMessage(message);
        res.setSuccess(false);
        return res;
    }

    public static <T> ApiResponse<T> error(String message, Object errors) {
        ApiResponse<T> res = new ApiResponse<>();
        res.setMessage(message);
        res.setSuccess(false);
        res.setErrors(errors);
        return res;
    }
}
