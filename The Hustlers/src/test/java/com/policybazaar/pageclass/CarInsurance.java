package com.policybazaar.pageclass;

import java.io.File;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.commons.io.FileUtils;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.policybazaar.baseclass.BaseClass;
import com.policybazaar.utils.FileHandler;

public class CarInsurance extends BaseClass {

	private WebDriver driver;

	public CarInsurance(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public void carDetails() {
		xpathlocator = "//*[@id='before-tp']/div[2]/a";
		wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathlocator)));
		clickButton(xpathlocator);

		reportPass("Proceed without car number selected");

		xpathlocator = "//*[@id='spn9']";
		wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathlocator)));
		clickButton(xpathlocator);

		xpathlocator = "//*[@id='section5']/ul/li[15]/span";
		clickButton(xpathlocator);

		xpathlocator = "//*[@id='dvMake']/div/ul/div/li[2]/span";
		wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathlocator)));
		clickButton(xpathlocator);

		xpathlocator = "//*[@id='modelScroll']/li[19]/span"; 
		wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathlocator)));
		clickButton(xpathlocator);

		xpathlocator = "//*[@id='Diesel']";
		wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathlocator)));
		clickButton(xpathlocator);

		xpathlocator = "//*[@id='variantScroll']/li[1]/span";
		wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathlocator)));
		clickButton(xpathlocator);

		xpathlocator = "//*[@id='dvRegYear']/ul/div/li[2]/span";
		wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathlocator)));
		clickButton(xpathlocator);

		reportPass("Car details entered");
	}

	public void userDetails(String title, String name, String mail, String number) {
		try {
			locateAndFill("//*[@id='name']", FileHandler.getJson(title, name));
			locateAndFill("//*[@id='email']", FileHandler.getJson(title, mail));
			locateAndFill("//*[@id='mobileNo']", FileHandler.getJson(title, number));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		clickButton("//*[@id='btnLeadDetails']");

		reportPass("Invalid user details entered");
	}

	public void displayError() {
		String err1 = locateAndGetText("//*[@id='dvVariant']/div[2]/div[1]/div[1]/div[1]");
		String err2 = locateAndGetText("//*[@id='dvVariant']/div[2]/div[1]/div[2]/div[2]");
		String err3 = locateAndGetText("//*[@id='dvVariant']/div[2]/div[1]/div[3]/div[2]");

		System.out.println("Error displaying : \n\n" + err1 + "\n" + err2 + "\n" + err3);
		reportPass("Error displayed!");
	}

	public void takingScreenShot() {

		Calendar date = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MMM-dd HH-MM-SS");
		String dateNow = formatter.format(date.getTime());
		String fileName = ".\\screenshot\\screenshot" + dateNow + ".jpeg";
		File scr = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(scr, new File(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("\nScreenShot Captured of the same!\n");
		System.out.println(
				"_______________________________________________________________________________________________________________________ \n");
		reportPass("Screenshot of the error captured!");
	}

	public void closePage() {
		driver.close();
		driver.switchTo().window(BaseClass.tabs.get(0));
		reportInfo("Child tab closed");
		reportInfo("Handle Switched to Main Tab");
	}
}
