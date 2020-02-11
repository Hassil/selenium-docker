package com.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.net.MalformedURLException;
import java.net.URL;

public class BaseTest {

    protected WebDriver driver;

    @BeforeTest
    public void setupDriver(ITestContext ctxt) throws MalformedURLException {

        String host = "localhost";
        DesiredCapabilities dc;

        if (System.getProperty("BROWSER") != null &&
                System.getProperty("BROWSER").equalsIgnoreCase("firefox")) {
            dc = DesiredCapabilities.firefox();
        } else {
            dc = DesiredCapabilities.chrome();
        }

        if (System.getProperty("HUB_HOST") != null ) {
            host = System.getProperty("HUB_HOST");
        }

        String testName = ctxt.getCurrentXmlTest().getName();

        String completeUrl = "http://" + host + ":4444/wd/hub";

        dc.setCapability("name", testName);

        this.driver = new RemoteWebDriver(new URL(completeUrl), dc);

        //System.setProperty("webdriver.chrome.driver", "/home/fvalenzuelas/bin/chromedriver");
        //this.driver = new ChromeDriver();
    }

    @AfterTest
    public void quitDriver() {
        this.driver.quit();
    }
}
