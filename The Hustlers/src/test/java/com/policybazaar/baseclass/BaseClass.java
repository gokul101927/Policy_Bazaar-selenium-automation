package com.policybazaar.baseclass;

/*java -Dwebdriver.chrome.driver=C:\Users\hp\Desktop\SeleniumGrid\chromedriver.exe  -jar selenium-grid.jar -role webdriver -hub http://192.168.1.9:4444/grid/register/ -port 5556 -browser browserName=chrome*/
import java.io.File;


import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.policybazaar.utils.FileHandler;

public class BaseClass {
	public static String mainWindowHandle;
	public static RemoteWebDriver driver = null;
	public static ArrayList<String> tabs;
	public static String xpathlocator = null;
	public static WebDriverWait wait;
	public static ExtentReports report = BaseClass.initiateReport();;
	public static ExtentTest logger;
	public static ExtentHtmlReporter htmlReporter;
	public static String browser = "";

	public void openBrowser(String browser) {

		System.out.println("*******Invoking " + browser + "************");

		// Decide which browser need to invoke
		if (browser.equalsIgnoreCase("firefox")) {
			FirefoxOptions fox = new FirefoxOptions();
			fox.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);

			try {
				driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), fox);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		} else if (browser.equalsIgnoreCase("chrome")) {
			ChromeOptions cap = new ChromeOptions();
			cap.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);

			try {
				driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), cap);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}

		getmainWindow();
	}

	public MainPageClass openSite() {
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		if (FileHandler.getPropData("Url") == null) {

			reportFail("URL not Loaded");
		} else {

			driver.get(FileHandler.getPropData("Url"));
			reportPass("URL successfully Navigated to policy bazaar site");
		}

		return PageFactory.initElements(driver, MainPageClass.class);
	}

	public void closeBrowser(String message, String browser) {
		System.out.println(FileHandler.getPropData(browser) + " " + message);
		driver.quit();
	}

	public void getmainWindow() {
		mainWindowHandle = driver.getWindowHandle();
	}

	public static void windowHandle() {
		tabs = new ArrayList<String>(driver.getWindowHandles());
		driver.manage().deleteAllCookies();
	}

	public void locateAndFill(String xpathLocator, String sendKeysValue) {
		validateAndLocateXpath(xpathLocator).sendKeys(sendKeysValue);
	}

	public void clickButton(String xpathLocator) {
		validateAndLocateXpath(xpathLocator).click();

	}

	public String locateAndGetText(String xpathLocator) {
		String text = validateAndLocateXpath(xpathLocator).getText();
		return text;
	}

	public WebElement validateAndLocateXpath(String xpathLocator) {

		try {
			WebElement locateElement = null;
			locateElement = driver.findElement(By.xpath(xpathLocator));
			return locateElement;
		} catch (NoSuchElementException e) {
			reportFail("Can not Locate Element with Locator " + xpathLocator);
			closeBrowser("Terminating the Process due to some error! Please resolve the error!", browser);
			return null;
		}

		catch (Exception e) {
			reportFail("Description: " + e.getMessage());
			closeBrowser("Terminating the Process due to an error! Please resolve the error!", browser);
			return null;
		}
	}

	public static ExtentReports initiateReport() {

		File extentConfigFile = new File(".//reports//extent-config.html");
		htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + extentConfigFile);
		report = new ExtentReports();
		report.attachReporter(htmlReporter);

		report.setSystemInfo("OS", "Windows 10 21H1");
		report.setSystemInfo("Environment", "policybazaar site");
		report.setSystemInfo("Build Number", "10.8.1");

		htmlReporter.config().setDocumentTitle("Policy Bazaar Report");
		htmlReporter.config().setReportName("Test Report");
		htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);

		return report;
	}

	public static void reportPass(String message) {
		logger.log(Status.PASS, message);
	}

	public void reportInfo(String message) {
		logger.log(Status.INFO, message);
	}

	public void endReport() {
		report.flush();
	}

	public void reportFail(String message) {
		logger.log(Status.FAIL, message);
		takeScreenShotOnFailure();
		Assert.fail(message);
	}

	public void takeScreenShotOnFailure() {
		TakesScreenshot captured = (TakesScreenshot) driver;
		File src = captured.getScreenshotAs(OutputType.FILE);

		String filePath = System.getProperty("user.dir") + "\\Screenshots\\" + "Test_Failure.png";
		File dest = new File(filePath);

		try {
			FileUtils.copyFile(src, dest);
			logger.addScreenCaptureFromPath(filePath);
			reportInfo("Screenshot Captured Successfully!");
		} catch (IOException e) {
			logger.log(Status.FAIL, e.getMessage());
		}
	}
}
