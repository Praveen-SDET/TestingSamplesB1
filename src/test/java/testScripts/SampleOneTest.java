package testScripts;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.bouncycastle.crypto.CryptoServicesRegistrar.Property;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.github.bonigarcia.wdm.WebDriverManager;

public class SampleOneTest {
	
	WebDriver driver;
	Properties prop;
	@Parameters("browser")
	@BeforeMethod
	public void setup(String strBrowser) {
		if(strBrowser.equalsIgnoreCase("chrome")) {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		}
		driver.manage().window().maximize();
		
	}
	
  @Test(priority=1)
  public void javaSearchTest() {
	//    System.setProperty("wendriver.chrome.driver", "D:\\SDET_WS\\WebDriver\\chromedriver");
	//	WebDriver driver = new ChromeDriver();
	//	driver.manage().window().maximize();
	  
	//	driver.get("https://www.google.com/"); 
	     // to read from the file we use this 
	  driver.get(prop.getProperty("url"));
	  
		
		
		WebElement searchBox = driver.findElement(By.name("q"));
		searchBox.sendKeys("Java Tutorial");
		searchBox.submit();
		Assert.assertEquals(driver.getTitle(), "Java Tutorial - Google Search");
		
	}
//  @Test
//  public void seleniumSearchTest() {
////	  System.setProperty("wendriver.chrome.driver", "D:\\SDET_WS\\WebDriver\\chromedriver");
////		WebDriver driver = new ChromeDriver();
////		driver.manage().window().maximize();
//		driver.get("https://www.google.com/");
//		WebElement searchBox = driver.findElement(By.name("q"));
//// this next line is created a sofrassert and make the test cases baesed on the lates assert		
//		SoftAssert softAssert = new SoftAssert();
//		softAssert.assertEquals(driver.getTitle(), "Google Page");  // if we gave Page it will fail but in case 
//		                                                    //of softassert it it passes because of updated selenium assert
//		
//		searchBox.sendKeys("Selenium Tutorial");
//		searchBox.submit();
//		Assert.assertEquals(driver.getTitle(), "Selenium Tutorial - Google Search");
//		softAssert.assertAll();
//	
//	}
  @Test(priority=3)
public void seleniumSearchTest() {
	  driver.get(prop.getProperty("url"));
		WebElement searchBox = driver.findElement(By.name("q"));
		searchBox.sendKeys("Selenium Tutorial");
		searchBox.submit();
		Assert.assertEquals(driver.getTitle(), "Selenium Tutorial - Google Search");
	
}
  @Test(enabled =false)                  //(priority=2)
  public void cucumberSearchTest(){
	  driver.get(prop.getProperty("url"));
		WebElement searchBox = driver.findElement(By.name("q"));
		searchBox.sendKeys("Cucumber Tutorial");
		searchBox.submit();
		Assert.assertEquals(driver.getTitle(), "Cucumber Tutorial - Google Search");
  }
  @BeforeTest
	public void readFromProp() throws IOException {
		String path = System.getProperty("user.dir")+"//src//test//resources//configFiles//config.properties";
		prop = new Properties();
		FileInputStream fin = new FileInputStream(path);
		prop.load(fin);
		
	}	
 @AfterMethod
  public void teardown() {
	  driver.close();
  }
}
