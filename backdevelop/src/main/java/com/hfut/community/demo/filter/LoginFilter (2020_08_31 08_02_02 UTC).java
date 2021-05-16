package com.hfut.community.demo.filter;

import com.hfut.community.demo.domain.Admin;


import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.Filter;
import javax.servlet.FilterConfig;

/**
 */
@WebFilter(urlPatterns = "/Admin/*", filterName = "adminFilter")
public class LoginFilter implements Filter {
    /**
     */
    private static final String RETURNPAGE = "../";
    /**
     */
    private static final String SUPERADMIN = "0";
    /**
     */
    private static final String ADMIN = "1";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpSession session = ((HttpServletRequest) request).getSession();
        Admin admin = (Admin) session.getAttribute("phone");
        if (admin != null && (SUPERADMIN.equals(admin.getPower()) || ADMIN.equals(admin.getPower()))) {
            chain.doFilter(request, response);
        } else {
            ((HttpServletResponse) response).sendRedirect(RETURNPAGE);
        }
    }

    @Override
    public void destroy() {
    }
}
