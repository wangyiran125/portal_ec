package com.chinalbs.service.aop;

import java.lang.reflect.Method;

import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;

import com.chinalbs.framework.datasource.DataSourceContextHolder;
import com.chinalbs.framework.datasource.datatype.DataSourceType;

public class OwnDatasourceAop implements MethodBeforeAdvice, AfterReturningAdvice {

    @Override
    public void afterReturning (Object returnValue, Method method, Object [] args, Object target) throws Throwable {

        DataSourceContextHolder.setDataSourceType (DataSourceType.CUST);

    }

    @Override
    public void before (Method method, Object [] args, Object target) throws Throwable {

        DataSourceContextHolder.setDataSourceType (DataSourceType.OWN);
    }
}
