package org.idchavan.common;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter("/*")
public class CharacterEncodingFilter implements Filter {

    @Override
    public void init(FilterConfig config) throws ServletException {
        // NOOP.
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        if(isSessionExpired((HttpServletRequest)request)) {
        	System.out.println("================Session has been expired==============================");
        	((HttpServletResponse) response).sendRedirect(((HttpServletRequest) request).getContextPath() + "/logout.jsp");
            response.flushBuffer();
        }else {
        	chain.doFilter(request, response);
        }
        
    }
    
    private boolean isSessionExpired( HttpServletRequest req ) {
    	return req.getSession() == null ? true : false;
    }

    @Override
    public void destroy() {
        // NOOP.
    }
}
