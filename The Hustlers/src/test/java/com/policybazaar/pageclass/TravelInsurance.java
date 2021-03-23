package com.policybazaar.pageclass;

import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.policybazaar.baseclass.BaseClass;
import com.policybazaar.utils.FileHandler;

public class TravelInsurance extends BaseClass {

	private WebDriver driver;

	public TravelInsurance(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public void travelDetails(String title, String country, String age1, String age2) {

		xpathlocator = "//*[@id='topForm']/section/div[2]/article/ul/li[3]/a";
		wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathlocator)));
		clickButton(xpathlocator);

		driver.manage().timeouts().implicitlyWait(10000, TimeUnit.MILLISECONDS);
		xpathlocator = "//*[@id='destination-autocomplete']";
		try {
			locateAndFill(xpathlocator, FileHandler.getJson(title, country));
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		driver.manage().timeouts().implicitlyWait(10000, TimeUnit.MILLISECONDS);
		xpathlocator = "//*[@id='navigatorType']/body/ul/li[1]";
		clickButton(xpathlocator);

		reportPass("Country selected");

		try {
			WebElement std1 = driver.findElement(By.id("memage1"));
			std1.click();
			std1.sendKeys(FileHandler.getJson(title, age1));
			WebElement std2 = driver.findElement(By.id("memage2"));
			std2.click();
			std2.sendKeys(FileHandler.getJson(title, age2));
			reportPass("Age of two students given");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		

		driver.findElement(By.id("startdate")).click();

		xpathlocator = "//*[@id='navigatorType']/body/div[8]/div[2]/div[1]/table/tbody/tr[5]/td[4]";
		clickButton(xpathlocator);

		xpathlocator = "//*[@id='navigatorType']/body/div[9]/div[2]/div[1]/table/tbody/tr[5]/td[6]";
		clickButton(xpathlocator);

		driver.findElement(By.id("proceedStepOne")).click();
		
		reportPass("Trip dates given");

	}

	public void userDetails(String title, String name, String mobileNo, String gmail) {

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("travelgender")));
		Select gender = new Select(driver.findElement(By.id("travelgender")));
		gender.selectByValue("1");

		try {
			driver.findElement(By.id("travelname")).sendKeys(FileHandler.getJson(title, name));
			driver.findElement(By.id("travelmobile")).sendKeys(FileHandler.getJson(title, mobileNo));
			driver.findElement(By.id("travelemail")).sendKeys(FileHandler.getJson(title, gmail));
			
			reportPass("User details given");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		xpathlocator = "//*[@id='topForm']/section/div[2]/div[2]/div[1]/div[2]/div/a[2]";
		clickButton(xpathlocator);
	}

	public void sortBy() {

		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//*[@id='root']/main/div/div[2]/div/div[1]/div[4]/ul/li[5]/div/select")));
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		Select select1 = new Select(
				driver.findElement(By.xpath("//*[@id='root']/main/div/div[2]/div/div[1]/div[4]/ul/li[5]/div/select")));
		select1.selectByValue("1");

		reportPass("Sorted by price low to high");
	}

	public void extractingDetails() {
		int iCount = 0;
		xpathlocator = "//div[@class = 'main_quotes blank_si']";
		iCount = driver.findElements(By.xpath(xpathlocator)).size();

		Map<String, String> map = new HashMap<String, String>();
		for (int i = 1; i <= iCount; i++) {
			String company = driver
					.findElement(By.xpath("//*[@class='quoteLedtSide col-md-9']/div[7]/following-sibling::div[" + i
							+ "]//div[contains(@class, 'Logo')]//div"))
					.getAttribute("class").substring(5);
			String price = "//*[@class='quoteLedtSide col-md-9']/div[7]/following-sibling::div[" + i
					+ "]//following::button[2]";

			String returnText = locateAndGetText(price);
			map.put(returnText, company);

		}

		TreeMap<String, String> sorted = new TreeMap<String, String>(map);

		Set<Entry<String, String>> set = sorted.entrySet();

		List<Entry<String, String>> list = new ArrayList<Entry<String, String>>(set);
		System.out.println(
				"Three lowest international  travel insurance plan with amount and insurance provider company : \n");
		for (int i = 0; i <= 2; i++) {

			System.out.println(list.get(i).toString().substring(8) + " : ₹ " + list.get(i).toString().substring(2, 7));

		}
		System.out.println(
				"_______________________________________________________________________________________________________________________ \n");
		reportPass("Travel insurance details extracted");
	}
}
