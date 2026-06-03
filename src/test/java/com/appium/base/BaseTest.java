package com.appium.base;

import com.appium.drivers.DriverFactory;
import com.appium.drivers.DriverManager;
import io.appium.java_client.AppiumDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.net.MalformedURLException;

public abstract class BaseTest {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    @BeforeMethod(alwaysRun = true)
    public void setUp() throws MalformedURLException {
        AppiumDriver driver = DriverFactory.createDriver();
        DriverManager.setDriver(driver);
        log.info("Session started: {}", driver.getSessionId());
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        DriverManager.quitDriver();
        log.info("Session closed");
    }
}
