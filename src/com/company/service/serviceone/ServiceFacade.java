package com.company.service.serviceone;

import com.company.service.util.AuthException;
import com.company.service.util.ITokenObserver;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ServiceFacade {
    private static ServiceFacade instance;

    private String currentToken;
    private AuthService authService;
    private ISubServiceOne subServiceOne;
    private ISubServiceTwo subServiceTwo;
    private List<ITokenObserver> tokenObservers;

    public static ServiceFacade getInstance() {
        if (instance == null) {
            instance = new ServiceFacade();
            instance.authService = new AuthService();
            instance.currentToken = instance.authService.getAccessToken();
        }

        return instance;
    }

    private ServiceFacade() {
        this.tokenObservers = new ArrayList<>();
    }

    public ISubServiceOne subServiceOne() {
        if (instance.subServiceOne == null) {
            instance.subServiceOne = getProxyForSubService(new SubServiceOne(instance.currentToken));
        }

        return instance.subServiceOne;
    }

    public ISubServiceTwo subServiceTwo() {
        if (instance.subServiceTwo == null) {
            instance.subServiceTwo = getProxyForSubService(new SubServiceTwo(instance.currentToken));
        }

        return instance.subServiceTwo;
    }

    /**
     * Can move into a separate proxy factory class
     * to be used by all services
     * @param object new instance of subservice
     * @param <T> interface type of subservice
     * @return Proxy instance of type interface of subservice
     */
    private <T> T getProxyForSubService(T object) {
        List<Class<?>> interfaces = new ArrayList<>();
        Collections.addAll(interfaces, object.getClass().getInterfaces());
        Class<?> c = object.getClass();

        while (c.getSuperclass() != null) {
            c = c.getSuperclass();
            Collections.addAll(interfaces, c.getInterfaces());
        }

        ClassLoader classLoader = object.getClass().getClassLoader();

        T proxyInstance = (T) Proxy.newProxyInstance(classLoader, interfaces.toArray(Class[]::new), (proxy, method, args) -> {
            int tries = 0;
            Throwable finalThrowable = null;

            // may be replaced with failsafe
            while (tries < 3) {
                try {
                    return method.invoke(object, args);
                } catch (Throwable t) {
                    tries++;
                    finalThrowable = t.getCause();
                    System.out.println(t.getCause().getMessage());

                    // check for auth exception - may be coded into authservice interface
                    if (finalThrowable instanceof AuthException) {
                        currentToken = instance.authService.refreshAccessToken();
                        instance.tokenObservers.forEach(s -> s.updateToken(currentToken));
                    }
                }
            }

            throw finalThrowable;
        });

        instance.tokenObservers.add((ITokenObserver) proxyInstance);

        return proxyInstance;
    }
}
