package com.example.socialnetwork.delivery.shared.logging;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class ControllerLogging {

    public ControllerLogging() {
    }

    public static Logger getLogger(Class<?> clazz) {
        return (Logger) LoggerFactory.getLogger(clazz); }
}
