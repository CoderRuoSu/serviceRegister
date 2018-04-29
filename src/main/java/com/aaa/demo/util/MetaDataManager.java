package com.aaa.demo.util;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.Getter;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * Created by ruosu on 2018/4/26.
 */
public class MetaDataManager {
    @Getter
    public Map<String, Method>     methodCache = Maps.newHashMap();
    @Getter
    public Map<String, Class<?>[]> cache       = Maps.newHashMap();

    public MetaDataManager() {}

    public MetaDataManager(String[] pkgs) {
        try {
            scan(pkgs);
        }catch(Exception e) {
            ;
        }
    }

    @SuppressWarnings("unchecked")
    public MetaDataManager scan(String[] pkgs) throws Exception {
        //MetaDataManager manager = new  MetaDataManager();
        if (pkgs == null || pkgs.length == 0)
            return null;
        LoadPackageClasses loader = new LoadPackageClasses(pkgs, AutoDispatch.class);
        Set<Class<?>> sets = loader.getClassSet();
        Set<Class<?>> serviceInterfaces = Sets.newHashSet();
        for (Class<?> cls : sets) {
            AutoDispatch autoDispatch = cls.getAnnotation(AutoDispatch.class);
            for (Class<?> service : autoDispatch.serviceInterface()) {
                if (!serviceInterfaces.contains(service))
                    serviceInterfaces.add(service);
            }
            cache.put(cls.getName(), autoDispatch.serviceInterface());
        }

        for (Class<?> cls : serviceInterfaces) {
            for (Method m : cls.getMethods())
                methodCache.put(cls.getName().concat(m.getName()), m);
        }

        methodCache = Collections.unmodifiableMap(methodCache);
        cache = Collections.unmodifiableMap(cache);
        return this;
    }

}
