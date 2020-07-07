package com.itheima.logdemo.utils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


@Configuration
@Order(1)
@WebFilter(filterName = "logFilter", urlPatterns = "/*")
public class LogFilter implements Filter {

    //知识点：被哪个app引⽤，当前from的⽇志记录就是当前app的名字
    @Value("${spring.application.name}")
    String appName;

    //知识点：slf4j的好处，utils被其他项⽬引⽤时不会给对⽅的⽇志产⽣⼲扰
    private Logger logger = LoggerFactory.getLogger("kafka");

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        String cookieVal = null;
        Cookie[] cookies = httpServletRequest.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("sid".equals(cookie.getName())) {
                    cookieVal = cookie.getValue();
                    break;
                }
            }
        }

        String rid = StringUtils.defaultIfBlank(httpServletRequest.getHeader("rid"), CommonUtils.getRandomStr(10));
        String tid = StringUtils.defaultIfBlank(httpServletRequest.getHeader("tid"), CommonUtils.getDevice(httpServletRequest.getHeader("User-Agent")));
        String ip = StringUtils.defaultIfBlank(httpServletRequest.getHeader("ip"), CommonUtils.getIpAddress(httpServletRequest));
        String url = "java:" + httpServletRequest.getRequestURI();

        String sid = StringUtils.defaultIfBlank(httpServletRequest.getHeader("sid"), cookieVal);

        logger.info(new LogBean(rid,sid,tid,appName,"I am filter", ip, url).toString());
        chain.doFilter(request, response);

    }
}
