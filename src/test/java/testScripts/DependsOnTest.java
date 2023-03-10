package testScripts;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DependsOnTest {
		WebDriver driver;
		ExtentReports reports;
		ExtentSparkReporter spark;
		ExtentTest extentTest;
		@BeforeMethod
		public void setup() {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			driver.manage().window().maximize();
		}
		
		@BeforeTest
		public void setupExtent() {
			reports = new ExtentReports();
			spark = new ExtentSparkReporter("target//DependsOnTest.html");
			reports.attachReporter(spark);
			
		}
	//	@Test(dependsOnMethods="seleniumSearchTest")   
		@Test(alwaysRun=true, dependsOnMethods="seleniumSearchTest")
		public void javaSearchTest() {
			
			extentTest=reports.createTest("JavaTest");
				driver.get("https://www.google.com/");
				WebElement searchBox = driver.findElement(By.name("q"));
				searchBox.sendKeys("Java Tutorial");
				searchBox.submit();
				Assert.assertEquals(driver.getTitle(), "Java Tutorial - Google Search");	
			}
		@Test
		public void seleniumSearchTest() {
			
			extentTest=reports.createTest("SeleniumTest");
			  driver.get("https://www.google.com/");
				WebElement searchBox = driver.findElement(By.name("q"));
				searchBox.sendKeys("Selenium Tutorial");
				searchBox.submit();
				Assert.assertEquals(driver.getTitle(), "Selenium Tutorial - Google Search");
			
		}
		 @AfterTest
		  public void finishExtent() {
			  reports.flush();
			  driver.close();
		  }

	}


