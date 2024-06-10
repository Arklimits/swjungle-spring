package com.example.demo.component.filter;

import com.example.demo.component.handler.GlobalExceptionHandler;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class GlobalExceptionFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try{
            chain.doFilter(request, response);
        } catch(ServletException | IOException e){
            handleException((HttpServletRequest) request, (HttpServletResponse) response, e);
        }
    }

    @Override
}
