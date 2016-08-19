package org.ech.phr.util;

import javax.servlet.ServletContext;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.WebApplicationContextUtils;


public class SpringUtil {

	public static <T extends Object> T getBean(Class<T> clazz) {
        T bean = null;
        ServletContext sc = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession().getServletContext();
        ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(sc);
        if (applicationContext != null) {
            bean = applicationContext.getBean(clazz);
        }
        return bean;
    }

    public static Object getBean(String name) {
        Object bean = null;
        ServletContext sc = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession().getServletContext();
        ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(sc);
        if (applicationContext != null) {
            bean = applicationContext.getBean(name);
        }
        return bean;
    }
    

}
