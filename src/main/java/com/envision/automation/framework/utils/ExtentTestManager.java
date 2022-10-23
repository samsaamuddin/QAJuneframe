package com.envision.automation.framework.utils;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

import java.util.HashMap;
import java.util.Map;

public class ExtentTestManager {
    static ExtentReports report = ExtentManager.getReporter();
    static ThreadLocal<ExtentTest> extent_Test = new ThreadLocal();
    static ThreadLocal<Integer> testId = new ThreadLocal();
    static Map<Integer, ExtentTest> testMap = new HashMap();

    public ExtentTestManager() {
    }

    public static synchronized ExtentTest getTest() {
        return (ExtentTest)testMap.get((int)Thread.currentThread().getId());
    }

    public static synchronized ExtentTest startTest(String testName, String description) {
        ExtentTest test = report.startTest(testName);
        extent_Test.set(test);
        testMap.put((int)Thread.currentThread().getId(), extent_Test.get());
        return test;
    }

    public static synchronized void stopTest() {
        report.endTest((ExtentTest)testMap.get((int)Thread.currentThread().getId()));
    }

}
