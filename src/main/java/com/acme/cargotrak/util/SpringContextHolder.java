package com.acme.cargotrak.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Static holder for the Spring ApplicationContext so legacy code (DaoFactory, etc.)
 * can grab beans without DI.
 *
 * @author Rajesh Kumar 2009-04
 */
public class SpringContextHolder implements ApplicationContextAware {

    private static ApplicationContext context;

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    public static ApplicationContext getContext() {
        return context;
    }

    public static Object getBean(String name) {
        return context == null ? null : context.getBean(name);
    }
}
