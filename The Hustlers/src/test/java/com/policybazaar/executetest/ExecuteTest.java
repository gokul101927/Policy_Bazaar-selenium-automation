package com.policybazaar.executetest;

import java.util.Scanner;

import org.testng.annotations.AfterSuite;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.policybazaar.baseclass.BaseClass;
import com.policybazaar.baseclass.MainPageClass;
import com.policybazaar.pageclass.CarInsurance;
import com.policybazaar.pageclass.HealthInsurance;
import com.policybazaar.pageclass.TravelInsurance;

public class ExecuteTest extends BaseClass {

	static String healthTitle = "Health Insurance";
	static String healthName = "name";
	static String mobnoForHealth = "mobileNo";
	static String TravelTitle = "Travel Insurance";
	static String mobnoForTravel = "mobileNo";
	static String travelName = "name";
	static String travelMail = "gmail";
	static String ageOfStd1 = "age1";
	static String ageOfStd2 = "age2";
	static String countryToVisit = "country";
	static String CarTitle = "Car Insurance";
	static String carName = "name";
	static String carMail = "gmail";
	static String mobnoForCar = "mobileNo";

	BaseClass base;
	MainPageClass bazaar;
	TravelInsurance travel;
	HealthInsurance health;
	CarInsurance car;

	@BeforeSuite(groups = "smoke")
	public void setup() {
		System.out.println("Which browser you want to choose :");
		Scanner sc = new Scanner(System.in);
		browser = sc.nextLine();
		sc.close();
		base = new BaseClass();
		logger = report.createTest("Test");
		base.openBrowser(browser);
		reportInfo("Browser opened");
		bazaar = base.openSite();
		reportInfo("Website opened");
	}

	@Test(priority = 1, groups = {"smoke", "Regression"})
	public void navigatingTravelInsurance() {
		travel = bazaar.clickTravel();
		reportPass("Navigated to Travel insurance page");
	}
	
	@Test(priority = 2, groups = "smoke")
	public void executingTravelInsurance() {
		travel.travelDetails(TravelTitle, countryToVisit, ageOfStd1, ageOfStd2);
		travel.userDetails(TravelTitle, travelName, mobnoForTravel, travelMail);
		travel.sortBy();
		travel.extractingDetails();
	}
	
	@Test(priority = 3, groups = {"smoke", "Regression"})
	public void navigatingCarInsurance() {
		car = bazaar.clickCar();
		car.carDetails();
		reportPass("Navigated to Car insurance page");
	}
	
	@Test(priority = 4, groups = "smoke")
	public void executingCarInsurance() {
		car.userDetails(CarTitle, carName, carMail, mobnoForCar);

		car.displayError();
		car.takingScreenShot();
		car.closePage();
	}

	@Test(priority = 5, groups = {"smoke", "Regression"})
	public void navigatingHealthInsurance() {
		health = bazaar.clickHealth();
		reportPass("Navigated to Health insurance page");
	}
	
	@Test(priority = 6, groups = "smoke")
	public void executingHealthInsurance() {

		health.userDetails(healthTitle, healthName, mobnoForHealth);

		health.familyDetails();
		health.cityDetails();
		health.medication();
		health.extractingMenuDetils();
	}

	@AfterSuite(groups = "smoke")
	public void tearDown() {
		reportInfo("Browser Closed!");
		base.closeBrowser("Successfully Closed!", browser);
		base.endReport();
	}
}
