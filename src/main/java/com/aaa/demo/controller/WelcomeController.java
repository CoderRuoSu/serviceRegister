package com.aaa.demo.controller;

import com.aaa.demo.util.AutoDispatch;
import com.aaa.demo.util.MetaDataManager;
import com.aaa.demo.util.ServiceLocator;
import com.aaa.demo.vo.ResultVo;
import com.google.common.base.CaseFormat;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by ruosu on 2018/4/26.
 */
@Component
@Slf4j
public class WelcomeController {

    @Autowired
    MetaDataManager metaDataManager;

    protected Object autoDispatch(HttpServletRequest request, HttpServletResponse response, String methodName, Map<String, ?> model) {
        if(request.getMethod().equalsIgnoreCase("GET")) {
        //model = request.getParameterMap();
        }
        AutoDispatch annotation = getClass().getAnnotation(AutoDispatch.class);
        Class<?>[] services = annotation.serviceInterface();
        String preKey = null, postKey = null;
        String key = null;
        Class<?> service = null;
        Map<String, Method> methodCache = metaDataManager.getMethodCache();
        for (Class<?> cls : services) {
            if (metaDataManager.getMethodCache().containsKey(key = cls.getName()
                    .concat(methodName))) {
                preKey = key.replace(methodName, "pre" + methodName);
                postKey = key.replace(methodName, "post" + methodName);
                service = cls;
                break;
            }
        }
        if(key != null) {
            Object o = ServiceLocator.getBeanByName(CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, service.getSimpleName()));
            Object ret = null;

            //1. preCondition process
            ResultVo preCheck = null;
            Method pre = metaDataManager.getMethodCache().get(preKey);
            if (pre != null) {
                try {
                    int pCount = pre.getParameterTypes().length;
                    if(pCount == 1)
                        preCheck = (ResultVo) pre.invoke(o, model);
                    else
                        preCheck = (ResultVo) pre.invoke(o);
                }catch (Exception e) {

                    log.debug("preCondition check error:" + e.getCause().getMessage());
                }

                if (!ResultVo.isSuccess(preCheck))
                    throw new RuntimeException((String)preCheck.getMessage());
            }

            //2. business process
            Method m = metaDataManager.getMethodCache().get(key);
            try {
                int pCount = m.getParameterTypes().length;
                if(pCount == 1)
                    ret = m.invoke(o, model);
                else
                    ret = m.invoke(o);
            } catch (Exception e) {

                e.printStackTrace();

            } finally {
                //3. postCondition process
                Method post = metaDataManager.getMethodCache().get(postKey);
                if(post != null) {
                    try {
                        post.invoke(o, ret);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            return ret;
        }
        else
            throw new UnsupportedOperationException();
    }
}
