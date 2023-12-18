package top.reviewx.core.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import top.reviewx.core.common.CommonAuthContext;
import top.reviewx.core.common.CommonResponse;
import top.reviewx.entities.user.UserEntity;
import top.reviewx.rest.user.user.UUserRepository;

import java.io.IOException;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {
    private final Log LOGGER = LogFactory.getLog(getClass());
    private final UUserRepository userRepository;
    private final CommonAuthContext authContext;

    @Value("${application.jwt.secret-key}")
    private String SECRET_KEY;

    @Value("${application.api-key}")
    private String API_KEY;

    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        CommonResponse res = CommonResponse.builder()
                .success(false)
                .errorCode(HttpStatus.UNAUTHORIZED.value())
                .build();
        response.setContentType("application/json");

        if (Pattern.compile("^(/swagger-ui.*)|(/v3/api-docs.*)$")
                .matcher(request.getServletPath()).find()) {
            filterChain.doFilter(request, response);
        }
//        else if (!StringUtils.hasText(request.getHeader("api-key"))) {
//            res.setMessage("api-key is empty");
//            new ObjectMapper().writeValue(response.getOutputStream(), res);
//        } else if (!request.getHeader("api-key").equals(API_KEY)) {
//            res.setMessage("api-key is wrong");
//            new ObjectMapper().writeValue(response.getOutputStream(), res);
//        }
        else if (Pattern.compile("^/basic/.*$")
                .matcher(request.getServletPath()).find()) {
            filterChain.doFilter(request, response);
        } else if (!StringUtils.hasText(request.getHeader(AUTHORIZATION))) {
            res.setMessage("token is empty");
            new ObjectMapper().writeValue(response.getOutputStream(), res);
        } else {
            try {
                String token = request.getHeader(AUTHORIZATION).substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY.getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(token);

                UserEntity userEntity = userRepository.findById(decodedJWT.getSubject()).orElse(null);
                assert userEntity != null;
                SecurityContextHolder.getContext().setAuthentication(
                        new UsernamePasswordAuthenticationToken(null, null, userEntity.getRoles().stream()
                                .map(role -> new SimpleGrantedAuthority(role.name()))
                                .collect(Collectors.toList())));
                authContext.set(userEntity.getId(), userEntity.getLocal().getName(), userEntity.getLocal().getAvatar());
                filterChain.doFilter(request, response);
            } catch (Exception exception) {
                res.setMessage(exception.getMessage());
                new ObjectMapper().writeValue(response.getOutputStream(), res);
            }
        }
    }
}
