/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ia.project.mmm.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Redirects to Signin page if user has no session
 * @author Mohamed Kamal
 */
public class LoginFilter implements Filter{

    public String[] loginUrls = {"/login.html", "/login", "/signup.html", "/signup"};
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse res = (HttpServletResponse)response;
        
        String url = req.getServletPath();
        
        boolean hasSession = (req.getSession(false) != null);
        
        boolean goingToLogin = false;
        for(String loginUrl : loginUrls){
            goingToLogin |= url.equals(loginUrl);
        }
        
        if(hasSession && goingToLogin ){
            res.sendRedirect("index.jsp");
        }
        else if(!hasSession && !goingToLogin){
            res.sendRedirect("login.html");
        }
        else
            chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
    
}
