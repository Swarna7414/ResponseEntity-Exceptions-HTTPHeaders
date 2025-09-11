package com.HTTPHeaders.Practise.web;


import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ApiKeyFilter implements Filter {

    @Value("${app.api-key}")
    private String expected;


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;

        String method=request.getMethod();


        if (method.equals("POST") || method.equals("PUT") || method.equals("DELETE")){
            String auth = request.getHeader(HttpHeaders.AUTHORIZATION);
            String apikey=request.getHeader("X-API-Key");

            boolean ok = false;

            if (auth !=null && auth.startsWith("Bearer ")){
                ok= expected.equals(auth.substring("Bearer ".length()));
            }

            if (apikey !=null){
                ok=ok || expected.equals(apikey);
            }

            if (!ok) {
                ((HttpServletResponse) servletResponse ).sendError(HttpServletResponse.SC_UNAUTHORIZED,
                        "Missing/invalid API key. Use Authorization: Bearer " + expected + " or X-API-KEY: " + expected);
                return;
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);

    }

}