package com.nguyenklinh.shopapp.filters;

import com.nguyenklinh.shopapp.components.JwtTokenUtil;
import com.nguyenklinh.shopapp.models.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {
    @Value("${api.prefix}")
    private String prefix;
    private final UserDetailsService userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;
    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request,
                                    @NotNull HttpServletResponse response,
                                    @NotNull FilterChain filterChain) throws ServletException, IOException {
        try{
            if(isByPassToken(request)){
                filterChain.doFilter(request,response);//enable bypass
                return;
            }
            final String authHeader = request.getHeader("Authorization");
            if(authHeader == null || !authHeader.startsWith("Bearer ")){
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
                return;
            }
            final String token = authHeader.substring(7);
            final String phoneNumber = jwtTokenUtil.extractPhoneNumber(token);

            if (phoneNumber != null
                    && SecurityContextHolder.getContext().getAuthentication() == null){

                User userDetails = (User) userDetailsService.loadUserByUsername(phoneNumber);

                if (jwtTokenUtil.isValidateToken(token,userDetails)){
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
            filterChain.doFilter(request,response);//enable bypass
        }catch (Exception e){
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Unauthorized");
        };
    }
    private boolean isByPassToken(@NotNull HttpServletRequest request){
        final List<Pair<String,String>> bypassTokens = Arrays.asList(
                Pair.of(String.format("%s/roles",prefix), "GET"),
                Pair.of(String.format("%s/products**",prefix), "GET"),
                Pair.of(String.format("%s/categories",prefix),"GET"),
                Pair.of(String.format("%s/users/register",prefix),"POST"),
                Pair.of(String.format("%s/users/login",prefix),"POST"),
                Pair.of(String.format("%s/orders**",prefix),"GET")
        );
        String requestPath = request.getServletPath();
        String requestMethod = request.getMethod();

        for (Pair<String, String> bypassToken : bypassTokens) {
            //  wildcard `**` to regex `.*`
            String regexPath = bypassToken.getFirst().replace("**", ".*");

            // Kiểm tra nếu requestPath khớp với regex và method khớp
            if (requestPath.matches(regexPath) && requestMethod.equalsIgnoreCase(bypassToken.getSecond())) {
                return true;
            }
        }

        return false;
    }
}
