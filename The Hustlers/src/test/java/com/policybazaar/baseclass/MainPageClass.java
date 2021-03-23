package com.policybazaar.baseclass;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.policybazaar.pageclass.CarInsurance;
import com.policybazaar.pageclass.HealthInsurance;
import com.policybazaar.pageclass.TravelInsurance;

public class MainPageClass extends BaseClass {

	@FindBy(xpath = "/html/body/cclink/div[4]/div[1]/div/ul/li[2]/div/div/div[4]/ul/li[1]/a/span")
	public WebElement travelPage;

	@FindBy(xpath = "/html/body/cclink/main/div[2]/section/div[2]/a/div[1]")
	public WebElement healthPage;

	@FindBy(xpath = "/html/body/cclink/main/div[2]/section/div[4]/a/div[1]")
	public WebElement carPage;

	@FindBy(xpath = "//*[@id='root']/main/div/header/nav/div/a[1]")
	public WebElement mainPage;

	private WebDriver driver;

	public MainPageClass(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public TravelInsurance clickTravel() {

		Actions actions = new Actions(driver);
		WebElement topmenu = driver.findElement(By.xpath("/html/body/cclink/div[4]/div[1]/div/ul/li[2]/a"));
		actions.moveToElement(topmenu).perform();

		travelPage.click();
		
		driver.manage().deleteAllCookies();
		return PageFactory.initElements(driver, TravelInsurance.class);
	}

	public CarInsurance clickCar() {
		mainPage.click();
		BaseClass.windowHandle();

		driver.switchTo().window(BaseClass.tabs.get(1));
		reportInfo("Handle Switched to new tab");
		carPage.click();
		driver.manage().deleteAllCookies();
		return PageFactory.initElements(driver, CarInsurance.class);
	}

	public HealthInsurance clickHealth() {
		mainPage.click();
		BaseClass.windowHandle();

		driver.switchTo().window(BaseClass.tabs.get(1));
		reportInfo("Handle Switched to new tab");
		healthPage.click();
		reportPass("Health insurance Clicked!");
		driver.manage().deleteAllCookies();
		return PageFactory.initElements(driver, HealthInsurance.class);
	}
}
