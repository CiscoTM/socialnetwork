package com.example.socialnetwork;

import org.junit.jupiter.api.Test;

public class TestTemporal {
    //@Test
    void printHome() {
        System.out.println("HOME = " + System.getProperty("user.home"));
    }

   // @Test
    void printTestcontainersConfig() {
        System.out.println("TC Reuse: " + System.getProperty("testcontainers.reuse.enable"));
    }
   // @Test
    void printUserHome() {
        System.out.println("user.home = " + System.getProperty("user.home"));
    }

    //@Test
    void printAllSystemProperties() {
        System.getProperties().forEach((k, v) -> {
            if (k.toString().contains("testcontainers")) {
                System.out.println(k + " = " + v);
            }
        });
    }





}
