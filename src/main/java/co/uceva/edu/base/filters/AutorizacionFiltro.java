package co.uceva.edu.base.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "Autorizacion",urlPatterns = {"*.xhtml"})
public class AutorizacionFiltro implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest= (HttpServletRequest)servletRequest;
        HttpServletResponse httpServletResponse =(HttpServletResponse)servletResponse;
        HttpSession session= httpServletRequest.getSession(false);
        String url = httpServletRequest.getRequestURI();
        System.out.println(url);

        if(url.contains("/login.xhtml") && session!=null && session.getAttribute("username")!=null){
            System.out.println("Ingresa Filtro login");
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath()+"/faces/admin.xhtml");
        }

        if(url.contains("/admin.xhtml") && (session==null || session.getAttribute("username")==null)){
            System.out.println("Ingresa Filtro admin");
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath()+"/faces/login.xhtml");
        }
        System.out.println("Sale de los filtros");
        System.out.println(session);
        filterChain.doFilter(servletRequest,servletResponse);

    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
