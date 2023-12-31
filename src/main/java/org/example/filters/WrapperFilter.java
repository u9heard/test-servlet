package org.example.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.example.wrappers.RequestWrapper;

import java.io.IOException;

public class WrapperFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if(servletRequest instanceof HttpServletRequest){
            HttpServletRequestWrapper wrapper = new RequestWrapper((HttpServletRequest) servletRequest);
            filterChain.doFilter(wrapper, servletResponse);
        }
        else{
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}
