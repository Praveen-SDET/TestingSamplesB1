package testScripts;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ParaBank {
	WebDriver driver;
	String accCheck;
	ExtentReports reports;
	ExtentSparkReporter spark;
	ExtentTest extentTest;
	@BeforeTest	
	public void first(){
		WebDriverManager.chromedriver().setup();
		driver=new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://parabank.parasoft.com/");
		
		reports = new ExtentReports();
		spark = new ExtentSparkReporter("target//SparkParaBank.html");
		reports.attachReporter(spark);
		
	}
	@Test(priority=1)
	public void register() throws InterruptedException
	{
		 extentTest=reports.createTest("Register Test");
		driver.findElement(By.partialLinkText("Register")).click();
		Thread.sleep(3000);
		driver.findElement(By.id("customer.firstName")).sendKeys("Mohan");
		driver.findElement(By.id("customer.lastName")).sendKeys("Rajamani");
		driver.findElement(By.id("customer.address.street")).sendKeys("Venkatraman Street");
		driver.findElement(By.id("customer.address.city")).sendKeys("Calefornia");
		driver.findElement(By.id("customer.address.state")).sendKeys("Goa");
		driver.findElement(By.id("customer.address.zipCode")).sendKeys("1235445");
		driver.findElement(By.id("customer.phoneNumber")).sendKeys("2722889898");
		driver.findElement(By.id("customer.ssn")).sendKeys("4872865");
		driver.findElement(By.id("customer.username")).sendKeys("Zucians");
		driver.findElement(By.id("customer.password")).sendKeys("Zuci");
		driver.findElement(By.xpath("//td/input[@id='repeatedPassword']")).sendKeys("Zuci");
		Thread.sleep(3000);
		driver.findElement(By.xpath("//td/input[@class='button']")).click();
		Thread.sleep(3000);
		
	}

	
 /* @Test(dependsOnMethods="register")
  public void loginPage() {
	  driver.findElement(By.name("username")).sendKeys("jack");
	  driver.findElement(By.name("password")).sendKeys("sparrow");
	  driver.findElement(By.xpath("//input[@class='button']")).click();
  }*/
  
  @Test(priority=2)
  public void openaccount() throws InterruptedException
  {
	  extentTest=reports.createTest("Open Account Test");
//	  Thread.sleep(3000);
	  driver.findElement(By.xpath("//li/a[contains(text(),'Open New Account')]")).click();
	  Select newaccount=new Select(driver.findElement(By.xpath("(//select)[1]")));
	  newaccount.selectByVisibleText("SAVINGS");
//	  Thread.sleep(3000);
	  driver.findElement(By.xpath("//input[@class='button']")).submit();
	  Thread.sleep(3000);
	  Assert.assertEquals("Account Opened!", driver.findElement(By.xpath("//h1")).getText());
	  accCheck=  driver.findElement(By.id("newAccountId")).getText();
	  System.out.println(accCheck);
  }
  
  @Test(priority=3)
  public void overview() throws InterruptedException
  {
	  extentTest=reports.createTest("Over View Test ");
	  driver.findElement(By.partialLinkText("Overview")).click();
	  Thread.sleep(5000);
	 List<WebElement> list= driver.findElements(By.xpath("//td/a[@class='ng-binding']"));
//		 System.out.println(list.size());
  	  Assert.assertEquals(list.get(list.size()-1).getText(), accCheck);
  }
  
  @Test (dependsOnMethods="openaccount")
  public void Transferfund() throws InterruptedException {
	  driver.findElement(By.xpath("//li/a[contains(text(),'Fund')]")).click();
	  Thread.sleep(5000);
	  driver.findElement(By.xpath("//form//p/input")).sendKeys("1000");
	Select from=new Select(driver.findElement(By.id("fromAccountId")));
	from.selectByIndex(0);
	Select to=new Select(driver.findElement(By.id("fromAccountId")));
	to.selectByIndex(1);
	driver.findElement(By.xpath("//input[@class='button']")).submit();
	  Thread.sleep(5000);
	  Assert.assertEquals(driver.findElement(By.xpath("//h1")).getText(), "Transfer Complete!");
	  extentTest=reports.createTest("Transfer Fund Test");
  }
  
  
  @AfterTest
  public void close()
  {
	  reports.flush();
	  driver.close();
  }
}