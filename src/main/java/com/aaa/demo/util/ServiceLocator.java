package com.aaa.demo.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Created by ruosu on 2018/4/26.
 */
@Component
public class ServiceLocator implements ApplicationContextAware {

    private static ApplicationContext applicationContext = null;

    // 获取ApplicationContext对象
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ServiceLocator.applicationContext = applicationContext;
    }

    /**
     * @Title: getBeanByName
     * @Description: 通过bean的名字来获取Spring容器中的bean
     * @param beanName
     * @return: Object
     */
    public static Object getBeanByName(String beanName) {
        if (applicationContext == null){
            return null;
        }
        return applicationContext.getBean(beanName);
    }

    public static <T> T getBean(Class<T> type) {
        return applicationContext.getBean(type);
    }
}
