package me.danhthanhf.distant_class.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import me.danhthanhf.distant_class.common.constant.AppConstants;
import me.danhthanhf.distant_class.common.constant.ErrorConstants;
import me.danhthanhf.distant_class.common.response.ApiResponseWriter;
import me.danhthanhf.distant_class.common.util.JwtUtil;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final ApiResponseWriter responseWriter;
    private final EnableSpringDataWebSupport.SpringDataWebConfigurationImportSelector springDataWebConfigurationImportSelector;

    private boolean checkIfUrlInWhiteList(String url) {
        return AppConstants.WHITE_LIST_API.stream().anyMatch(url::startsWith);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String uri = request.getRequestURI();
        String url = request.getRequestURL().toString();
        System.out.println("Request URL: " + url);
        System.out.println("Request URI: " + uri);

        // ignore public APIs
        if (checkIfUrlInWhiteList(url)) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String token = extractToken(request);
            UserPrincipal principal = jwtUtil.extractUserPrincipal(token);

            var context = SecurityContextHolder.getContext();

            //check if the user is authenticated
            if (context.getAuthentication() == null) {
                // set authentication for the user was authenticated
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());
                context.setAuthentication(authentication);
            }

        } catch (MalformedJwtException e) {
            handleFailureJwt(response, ErrorConstants.ERR_TOKEN_INVALID);
        } catch (ExpiredJwtException e) {
            handleFailureJwt(response, ErrorConstants.ERR_TOKEN_EXPIRED);
        } catch (SignatureException e) {
            handleFailureJwt(response, ErrorConstants
                    .ERR_TOKEN_SIGNATURE_INVALID
            );
        } catch (IllegalArgumentException e) {
            handleFailureJwt(response, ErrorConstants.ERR_TOKEN_MISSING);
        } catch (Exception e) {
            handleFailureJwt(response, ErrorConstants.ERR_GENERIC_ERROR);
        }
    }

    private void handleFailureJwt(HttpServletResponse
                                          response, String msg) throws IOException {
        responseWriter.failure(response, HttpStatus.UNAUTHORIZED, msg);
    }

    private String extractToken(HttpServletRequest request) {
        String header = request.getHeader(AppConstants.AUTHORIZATION_HEADER);
        if (header != null && header.startsWith(AppConstants.JWT_TOKEN_PREFIX)) {
            return header.substring(AppConstants.JWT_TOKEN_PREFIX.length());
        }
        return null;
    }
}
