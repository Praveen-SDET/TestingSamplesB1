package testScripts;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class danube_Searchitem_Ex {
	WebDriver driver;
	Properties prop;
	@BeforeTest
	public void readFromProp() throws IOException {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://danube-webshop.herokuapp.com/account");
		
			String path = System.getProperty("user.dir")+"//src//test//resources//configFiles//config.properties";
			prop = new Properties();
			FileInputStream fin = new FileInputStream(path);
			prop.load(fin);
	}
	
	@Test(priority=1)
	public void signup() throws InterruptedException {
		driver.findElement(By.cssSelector("#signup")).click();
		Thread.sleep(2000);
		driver.findElement(By.cssSelector("#s-name")).sendKeys("Vinayak");
		driver.findElement(By.cssSelector("#s-surname")).sendKeys("Mahadev");
		driver.findElement(By.cssSelector("#s-email")).sendKeys("Zucians@gmail");
		driver.findElement(By.cssSelector("#s-password2")).sendKeys("Zuci");
		driver.findElement(By.cssSelector("#s-company")).sendKeys("xxxx");
		driver.findElement(By.cssSelector("#myself")).click();
		driver.findElement(By.cssSelector("#privacy-policy")).click();
		driver.findElement(By.cssSelector("#register-btn")).click();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		
		
	}
	@Test(priority=2,dependsOnMethods="signup")
	public void search() {
		driver.findElement(By.xpath("//input[@name='searchbar']")).sendKeys("Parry Hotter");
		driver.findElement(By.cssSelector("#button-search")).click();
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		driver.findElement(By.xpath("//li[@class='preview']")).click();
		
		driver.findElement(By.xpath("(//button[@class='call-to-action'])[2]")).click();
		driver.findElement(By.xpath("(//button[@class='call-to-action'])[2]")).click();
	}	
//	@Test
//	public void login() {
//		driver.findElement(By.cssSelector("#login")).click();
//		
//		driver.findElement(By.xpath("(//input[@class='textfield-modal'])[1]")).sendKeys(prop.getProperty("email"));
//		driver.findElement(By.xpath("(//input[@class='textfield-modal'])[2]")).sendKeys(prop.getProperty("password"));
//		
//		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(40));
//		driver.findElement(By.id("goto-signin-btn")).click();
//	}
	@Test(priority=3,dependsOnMethods="search")
	public void chechout() {
		driver.findElement(By.id("s-name")).sendKeys("Vinayak");
		driver.findElement(By.id("s-surname")).sendKeys("Mahadev");
		driver.findElement(By.id("s-address")).sendKeys("Dubai kuruku Sandhu");
		driver.findElement(By.id("s-zipcode")).sendKeys("121132");
		driver.findElement(By.id("s-city")).sendKeys("Dubai");
		driver.findElement(By.id("s-company")).sendKeys("xxxx");
		driver.findElement(By.cssSelector("#asap")).click();
		driver.findElement(By.xpath("(//button[@class='call-to-action'])[2]")).click();
	}

}
