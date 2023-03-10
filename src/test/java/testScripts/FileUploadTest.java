package testScripts;

import java.io.FileInputStream;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;
@Test
public class FileUploadTest {
	WebDriver driver;
	
	@Test
	public void fileUploadsTest() throws InterruptedException {
	WebDriverManager.chromedriver().setup();
	WebDriver driver  = new ChromeDriver();
	driver.manage().window().maximize();
	driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
	driver.get("https://blueimp.github.io/jQuery-File-Upload/");
	WebElement addFile = driver.findElement(By.xpath(".//input[@type='file']"));
	String filepath= System.getProperty("user.dir")+"FullScreen.png";
	addFile.sendKeys(filepath);
	
	driver.findElement(By.xpath("//a[text()='Start upload']")).click();
	Thread.sleep(2000);
	if(driver.findElement(By.xpath("//[text()='FullScreen.png']")).isDisplayed())
	{
		Assert.assertTrue(true, "Image Uploaded");
	}
	else  {
		Assert.assertTrue(false, "Image not Uploaded");
	}
	
	
}
}

