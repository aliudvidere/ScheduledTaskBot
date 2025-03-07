package com.schedule.scheduledtaskbot.service;

import com.schedule.scheduledtaskbot.config.ApplicationContextProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;

@Slf4j
@Service
public class PeriodicTaskExecutor {

    public void executeTask(String className, String methodName) {
        try {
            // Load the class dynamically
            Class<?> clazz = Class.forName(className);

            // Create an instance of the class
            Object instance = ApplicationContextProvider.getApplicationContext().getBean(clazz);

            // Get the method
            Method method = clazz.getMethod(methodName);

            // Invoke the method
            method.invoke(instance);

        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
