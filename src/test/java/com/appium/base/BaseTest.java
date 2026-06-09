package com.appium.base;

import com.appium.config.ConfigReader;
import com.appium.drivers.DriverFactory;
import com.appium.drivers.DriverManager;
import com.appium.reporting.AllureAttachments;
import com.appium.support.AppReadyWait;
import io.appium.java_client.AppiumDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.net.MalformedURLException;

public abstract class BaseTest {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    @BeforeClass(alwaysRun = true)
    public void setUpClass() throws MalformedURLException {
        if (reuseSession() && !DriverManager.hasDriver()) {
            startSession();
        }
    }

    @BeforeMethod(alwaysRun = true)
    public void setUp() throws MalformedURLException {
        if (!reuseSession()) {
            startSession();
        } else if (!DriverManager.hasDriver()) {
            startSession();
        }
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        if (reuseSession()) {
            captureFailureArtifacts(result);
            return;
        }
        quitSession(result);
    }

    @AfterClass(alwaysRun = true)
    public void tearDownClass() {
        if (reuseSession() && DriverManager.hasDriver()) {
            quitSession(null);
        }
    }

    private void startSession() throws MalformedURLException {
        AppiumDriver driver = DriverFactory.createDriver();
        DriverManager.setDriver(driver);
        AppReadyWait.waitForCatalog(driver);
        log.info("Session started: {}", driver.getSessionId());
    }

    private void quitSession(ITestResult result) {
        try {
            captureFailureArtifacts(result);
        } finally {
            DriverManager.quitDriver();
            log.info("Session closed");
        }
    }

    private void captureFailureArtifacts(ITestResult result) {
        if (result != null
                && result.getStatus() == ITestResult.FAILURE
                && DriverManager.hasDriver()) {
            AllureAttachments.captureFailure(DriverManager.getDriver());
        }
    }

    private static boolean reuseSession() {
        return ConfigReader.getBoolean("reuse.session", false);
    }
}
