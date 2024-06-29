package com.ecommerceProject.ecom.config;


import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Filter;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SimpleCorsFilter extends OncePerRequestFilter {

    @Value("${app.client.url}")
    private String clientAppUrl="";

    public SimpleCorsFilter(){

    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws ServletException, IOException {

        HttpServletResponse response=(HttpServletResponse)res;
        HttpServletRequest request=(HttpServletRequest)req;
        Map<String,String> map=new HashMap<>();
        String originHeader =request.getHeader("origin");
        response.setHeader("Access-Control-Allow-Origin", originHeader);
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "*");

        if ("OPTIONS".equalsIgnoreCase(request.getMethod())){
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            chain.doFilter(req,res);
        }
    }

//    @Override
//    public void doFilterInternal(HttpServletRequest req, HttpServletResponse  res, FilterChain  chain) throws ServletException, IOException{
//
//    }

}
