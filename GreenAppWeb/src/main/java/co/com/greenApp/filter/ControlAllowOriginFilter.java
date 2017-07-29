package co.com.greenApp.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author wsalazar - wsalazar@ias.com.co
 */
public class ControlAllowOriginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        ((HttpServletResponse) servletResponse).setHeader("Access-Control-Allow-Origin", "*");
        ((HttpServletResponse) servletResponse).setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE, OPTIONS");
        ((HttpServletResponse) servletResponse).setHeader("Access-Control-Max-Age", "3600");
        ((HttpServletResponse) servletResponse).setHeader("Access-Control-Allow-Headers", "Access-Control-Allow-Headers: Origin, X-Requested-With, Content-Type, Accept, Key");
        chain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
    }

}
