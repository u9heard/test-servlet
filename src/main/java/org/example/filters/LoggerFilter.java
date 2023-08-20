package org.example.filters;


import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.example.interfaces.RequestLogger;
import org.example.loggers.ConsoleRequestHistoryLogger;

import java.io.IOException;

public class LoggerFilter implements Filter {

    private RequestLogger logger;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
        logger = new ConsoleRequestHistoryLogger();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if(servletRequest instanceof HttpServletRequest){
            logger.log((HttpServletRequest) servletRequest);

            filterChain.doFilter(servletRequest, servletResponse);
        }
        else{
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}
