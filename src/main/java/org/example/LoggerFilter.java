package org.example;


import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.example.interfaces.RequestLogger;
import org.example.wrappers.RequestWrapper;

import java.io.IOException;

public class LoggerFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("logger");
        if(servletRequest instanceof HttpServletRequest){
            RequestLogger logger = new ConsoleRequestLogger((HttpServletRequest) servletRequest);
            logger.print();

            filterChain.doFilter(servletRequest, servletResponse);
        }
        else{
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {
    }
}
