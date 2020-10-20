/**
 * fshows.com
 * Copyright (C) 2013-2020 All Rights Reserved.
 */
package me.wujn.geektime.javaweek01.test;

import me.wujn.geektime.javaweek01.HelloClassLoader;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author wujn
 * @version HelloClassLoaderTest.java, v 0.1 2020-10-20 12:03 PM wujn
 */
public class HelloClassLoaderTest {

    @Test
    public void loadClassTest() throws ClassNotFoundException,
            NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {

        ClassLoader currentLoader = Thread.currentThread().getContextClassLoader();

        HelloClassLoader helloClassLoader = new HelloClassLoader(currentLoader);

        Class<?> helloClass = helloClassLoader.loadClass("Hello");
        if (helloClass == null) {
            System.out.println("Hello class is null");
            return;
        }
        Method method = helloClass.getMethod("hello", null);
        if (method == null) {
            System.out.printf("method hello not found");
            return;
        }
        method.invoke(helloClass.newInstance(), null);
    }
}
