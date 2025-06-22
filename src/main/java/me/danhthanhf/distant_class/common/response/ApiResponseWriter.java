package me.danhthanhf.distant_class.common.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import me.danhthanhf.distant_class.common.constant.AppConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class ApiResponseWriter {
    private final ObjectMapper objectMapper;

    public void failure(HttpServletResponse response, HttpStatus status, String message) throws IOException {
        ApiResponse<Void> error = ApiResponse.error(message);
        response.setContentType(AppConstants.CONTENT_TYPE_JSON);
        response.setCharacterEncoding(AppConstants.CHARSET_UTF_8);
        response.setStatus(status.value());
        objectMapper.writeValue(response.getWriter(), error);
    }
}
