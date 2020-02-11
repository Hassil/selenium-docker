package com.newtours.tests;

import com.newtours.pages.*;
import com.tests.BaseTest;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class BookFlightTest extends BaseTest {

    private String noOfPassengers;
    private String expectedPrice;

    @BeforeTest
    @Parameters({"noOfPassengers", "expectedPrice"})
    public void setUpParameters(String noOfPassengers, String expectedPrice) {
        this.noOfPassengers = noOfPassengers;
        this.expectedPrice = expectedPrice;

    }

    @Test
    public void registrationTest() throws InterruptedException {
        RegistrationPage registrationPage = new RegistrationPage(driver);
        registrationPage.goTo();
        registrationPage.enterUserDetails("selenium", "docker");
        registrationPage.enterUserCredentials("selenium", "docker");

        Thread.sleep(20000);

        registrationPage.submit();

    }

    @Test(dependsOnMethods = "registrationTest")
    public void registrationConfirmationTest() {

        RegistrationConfirmationPage registrationConfirmationPage = new RegistrationConfirmationPage(driver);
        registrationConfirmationPage.goToFlightDetailsPage();

    }

    @Test(dependsOnMethods = "registrationConfirmationTest")
    public void flightDetailsTest() {
        FlightDetailsPage flightDetailsPage = new FlightDetailsPage(driver);
        flightDetailsPage.selectPassengers(noOfPassengers);
        flightDetailsPage.goToFindFligthsPage();
    }

    @Test(dependsOnMethods = "flightDetailsTest")
    public void findFlightTest() {
        FindFlightPage findFlightPage = new FindFlightPage(driver);
        findFlightPage.submitFindFlightrPage();
        findFlightPage.goToOrderConfirmationPage();
    }

    @Test(dependsOnMethods = "findFlightTest")
    public void printConfirmationTest() {
        FlightConfirmationPage flightConfirmationPage = new FlightConfirmationPage(driver);
        String actualPrice = flightConfirmationPage.getPrice();
        Assert.assertEquals(actualPrice, expectedPrice);

    }

    @AfterTest
    public void quitBrowser() {
        this.driver.quit();
    }
}
