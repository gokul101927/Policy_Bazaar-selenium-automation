package com.policybazaar.pageclass;

import java.io.IOException;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.policybazaar.baseclass.BaseClass;
import com.policybazaar.utils.FileHandler;

public class HealthInsurance extends BaseClass {
	public static String returnText = "";

	private WebDriver driver;

	public HealthInsurance(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public void userDetails(String title, String name, String number) {

		xpathlocator = "//*[@id='form1']/form/div[1]/div[1]/div/label[1]/p/span/i";
		wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathlocator)));
		clickButton(xpathlocator);

		try {
			locateAndFill("//*[@id='fullname']", FileHandler.getJson(title, name));
			locateAndFill("//*[@id='pmobile']", FileHandler.getJson(title, number));

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		xpathlocator = "//*[@id='continuecta1']";
		driver.findElement(By.xpath(xpathlocator)).click();

		reportPass("User details entered");
	}

	public void familyDetails() {
		xpathlocator = "//*[@id='form2']/div[1]/label[3]/p";
		wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathlocator)));
		clickButton(xpathlocator);

		Select elderAge = new Select(driver.findElement(By.xpath("//*[@id='eldestMemberAge']")));
		elderAge.selectByValue("22");

		Select childAge = new Select(driver.findElement(By.xpath("//*[@id='eldestChildAge']")));
		childAge.selectByValue("0");

		xpathlocator = "//*[@id='continuecta2']";
		clickButton(xpathlocator);

		reportPass("User family details entered");
	}

	public void cityDetails() {
		xpathlocator = "//*[@id='form3']/div[2]/div/label[3]/span";
		wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathlocator)));
		clickButton(xpathlocator);
	}

	public void medication() {
		xpathlocator = "//*[@id='nopedselection']";
		wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathlocator)));
		clickButton(xpathlocator);

		reportPass("Other details entered");
	}

	public void extractingMenuDetils() {

		wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("QuoteSorting")));
		driver.findElement(By.id("QuoteSorting")).click();

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		ArrayList<String> sortList = new ArrayList<String>();
		String sortByRevelance = locateAndGetText("//*[@id='SortRelevance']/span");

		String sortBy = locateAndGetText("//*[@id='SortLow']/span");

		clickButton("//*[@id='QuoteSorting']/div/div[2]/div[2]/div[1]");

		sortList.add(sortByRevelance);
		sortList.add(sortBy);
		System.out.println("Sorting menu : \n");
		for (String sort : sortList) {
			System.out.println(sort);
		}

		clickButton("//*[@id='ClickInsurerList']/a");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		System.out.println("_ _ _ _ _ _ _ _ _ _ \n");
		int iCount = 0;
		xpathlocator = "//*[@class='insurer_div']";
		iCount = driver.findElements(By.xpath(xpathlocator)).size();
		System.out.println("Insurer company :\n");
		ArrayList<String> insurerMenu = new ArrayList<String>();
		for (int i = 1; i <= iCount; i++) {

			String insureCompany = "//*[@id='ClickInsurerList']/div[2]/div[2]/div[" + i + "]/div/label/div[2]/span";
			returnText = locateAndGetText(insureCompany);
			insurerMenu.add(returnText);

		}

		for (String displayinsureCompany : insurerMenu) {
			System.out.println(displayinsureCompany);
		}

		System.out.println("_ _ _ _ _ _ _ _ _ _ \n");
		clickButton("//*[@id='ClickInsurerList']/div[2]/div[3]/div[1]");
		clickButton("//*[@id='SumInusredFilter']");
		System.out.println("Covers :\n");
		ArrayList<String> coverMenu = new ArrayList<String>();
		for (int k = 1; k <= 2; k++) {
			for (int i = 1; i <= 3; i++) {
				String cover = "//*[@id='SumInusredFilter']/div[2]/div[2]/div[" + k + "]/span[" + i + "]";
				returnText = locateAndGetText(cover);
				coverMenu.add(returnText);
			}
		}

		for (String displayCover : coverMenu) {
			System.out.println(displayCover);
		}

		System.out.println("_ _ _ _ _ _ _ _ _ _ \n");
		clickButton("//*[@id='SumInusredFilter']/div[2]/div[2]/div[4]/div[1]");
		clickButton("//*[@id='FeaturesFilter']/span");
		System.out.println("Features :\n");
		ArrayList<String> featureMenu = new ArrayList<String>();
		for (int i = 1; i <= 3; i++) {

			String feature = "//*[@id='FeaturesFilter']/div[2]/div[2]/div/span[" + i + "]";
			returnText = locateAndGetText(feature);
			featureMenu.add(returnText);
			feature = "//*[@id='FeaturesFilter']/div[2]/div[2]/div/div[2]/div[" + i + "]/label/span[2]";
			returnText = locateAndGetText(feature);
			featureMenu.add(returnText);
		}

		for (int i = 3; i <= 5; i++) {
			String feature = "//*[@id='FeaturesFilter']/div[2]/div[2]/div/div[" + i + "]/label/span";
			returnText = locateAndGetText(feature);
			featureMenu.add(returnText);

		}
		for (String displayfeature : featureMenu) {
			System.out.println(displayfeature);
		}

		System.out.println("_ _ _ _ _ _ _ _ _ _ \n");
		clickButton("//*[@id='ApplyFeatureFilter']");
		clickButton("//*[@id='ClickOnPremiumFilter']/span[2]");
		ArrayList<String> premiumMenu = new ArrayList<String>();
		System.out.println("Monthly premium : \n");
		for (int k = 1; k <= 2; k++) {
			for (int i = 1; i <= 3; i++) {

				String premium = "//*[@id='ClickOnPremiumFilter']/div[2]/div[2]/div[" + k + "]/span[" + i + "]";
				returnText = locateAndGetText(premium);
				premiumMenu.add(returnText);

			}

		}

		for (String displayPremium : premiumMenu) {
			System.out.println(displayPremium);
		}

		System.out.println("_ _ _ _ _ _ _ _ _ _ \n");
		clickButton("//*[@id = 'ClickOnPremiumFilter']/div[2]/div[2]/div[4]/div[1]");
		clickButton("//div[@id='ClickonPlanFilter']");

		xpathlocator = "//div[@class = 'filter_plan_name']";
		iCount = driver.findElements(By.xpath(xpathlocator)).size();
		System.out.println("Base plan type : \n");
		ArrayList<String> basePlanMenu = new ArrayList<String>();
		for (int i = 2; i <= iCount + 1; i++) {

			String basePlan = "//*[@id='ClickonPlanFilter']/div[2]/div[" + i + "]/div/span";
			returnText = locateAndGetText(basePlan);
			basePlanMenu.add(returnText);

		}
		for (String displayBase : basePlanMenu) {
			System.out.println(displayBase);
		}
		System.out.println(
				"_______________________________________________________________________________________________________________________ \n");

		reportPass("Menu items extracted and stored in a list");
	}

}
