package com.appium.reporting;

import com.appium.config.ConfigReader;
import io.qameta.allure.Allure;
import org.testng.ITestListener;
import org.testng.ITestResult;

/**
 * Adds run context labels after Allure has started the test case.
 */
public final class AllureLabelsListener implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {
        Allure.label("platform", System.getProperty("platform", "android"));
        Allure.label("env", System.getProperty("env", "local"));

        String sampleApp = ConfigReader.get("sample.app");
        if (sampleApp != null && !sampleApp.isBlank()) {
            Allure.label("sample.app", sampleApp);
        }
    }
}
