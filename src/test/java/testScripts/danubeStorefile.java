package testScripts;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import io.github.bonigarcia.wdm.WebDriverManager;

public class danubeStorefile {
	WebDriver driver;
	Properties prop;
	//for creating Extent Reports following 3 lines 
	ExtentReports reports;
	ExtentSparkReporter spark;
	ExtentTest extentTest;
	@BeforeTest
	public void setup() throws IOException
	{
		WebDriverManager.chromedriver().setup();
		driver=new ChromeDriver();
		driver.manage().window().maximize();
		String path=System.getProperty("user.dir")+"\\src\\test\\resources\\ConfigFiles\\config.properties";
		 prop=new Properties();
		FileInputStream obtained = new FileInputStream(path);
		prop.load(obtained);
		driver.get(prop.getProperty("url1"));
		
		//For creating Extent Reports
		
		reports = new ExtentReports();
		spark = new ExtentSparkReporter("target//SparkDanube.html");
		reports.attachReporter(spark);
		
	}
	
	@Test(dataProvider="Register")
	public void register(String name, String surname, String email, String password, String company) throws InterruptedException, InvalidFormatException, IOException
	{
		extentTest=reports.createTest("RegisterTest in Danube");
	
		driver.findElement(By.id(readXLData("Signup"))).click();
		driver.findElement(By.id(readXLData("Name"))).sendKeys(name);
		driver.findElement(By.id(readXLData("Surname"))).sendKeys(surname);
		driver.findElement(By.id(readXLData("Email"))).sendKeys(email);
		driver.findElement(By.id(readXLData("Password"))).sendKeys(password);
		driver.findElement(By.id(readXLData("Company"))).sendKeys(company);
		driver.findElement(By.id(readXLData("Myself"))).click();
		driver.findElement(By.id(readXLData("Agreement"))).click();
		driver.findElement(By.id(readXLData("Policy"))).click();
		driver.findElement(By.xpath(readXLData("Register"))).click();		
	}
	@Test
	public void search() throws InterruptedException, SAXException, IOException, ParserConfigurationException {
		
		extentTest=reports.createTest("Search Test in Danube");
//		driver.get(prop.getProperty("url"));
		driver.findElement(By.name(readXMLData("sear"))).sendKeys(prop.getProperty("search"));
		driver.findElement(By.className(readXMLData("searchbtn"))).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath(readXMLData("addcart"))).click();
		driver.findElement(By.xpath(readXMLData("checkout"))).click();
		Thread.sleep(3000);
		driver.findElement(By.id(readXMLData("FName"))).sendKeys(prop.getProperty("name"));
		driver.findElement(By.id(readXMLData("SName"))).sendKeys(prop.getProperty("surname"));
		driver.findElement(By.id(readXMLData("Add"))).sendKeys(prop.getProperty("address"));
		driver.findElement(By.id(readXMLData("Zipp"))).sendKeys(prop.getProperty("zipcode"));
		driver.findElement(By.id(readXMLData("City"))).sendKeys(prop.getProperty("city"));
		driver.findElement(By.id(readXMLData("ComX"))).sendKeys(prop.getProperty("company"));
		driver.findElement(By.id(readXMLData("shi"))).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath(readXMLData("buy"))).click();
		Thread.sleep(2000);
	}
	
	public String readXLData(String objName) throws InvalidFormatException, IOException {
		  String objPath="";
		  String path=System.getProperty("user.dir")+"//src//test//resources//testData//danubeData.xlsx";
		  XSSFWorkbook workbook= new XSSFWorkbook(new File(path));
		  XSSFSheet sheet=workbook.getSheet("sheet1");
		  int numRows=sheet.getLastRowNum();
		  for(int i=0; i<=numRows; i++)
		  {
			  XSSFRow row=sheet.getRow(i);
			  if(row.getCell(0).getStringCellValue().equalsIgnoreCase(objName))
				  objPath=row.getCell(1).getStringCellValue();
		  }
		  return objPath;
	}
	public String readXMLData(String tagname) throws SAXException, IOException, ParserConfigurationException {
		  String path=System.getProperty("user.dir")+"//src//test//resources//testData//danubeXML.xml";
		  File file= new File(path);
		  DocumentBuilderFactory factory= DocumentBuilderFactory.newInstance();
		  DocumentBuilder build=factory.newDocumentBuilder();
		  Document document= build.parse(file);
		  NodeList List= document.getElementsByTagName("Danube");
		  Node node1=List.item(0);
		  Element elem=(Element)node1;
		  return elem.getElementsByTagName(tagname).item(0).getTextContent();
	  
	  }
	
	 @DataProvider(name="Register")
	  public Object[][] getData() throws CsvValidationException, IOException{
		  String path=System.getProperty("user.dir")+"//src//test//resources//testData//danube.csv";
		  String[] cols;
		  CSVReader reader = new CSVReader(new FileReader(path));
		  ArrayList<Object> dataList=new ArrayList<Object>();
		  while((cols=reader.readNext())!=null)
		  {
			  Object[] record= {cols[0], cols[1], cols[2], cols[3], cols[4]};
			  dataList.add(record);
		  }
		  return dataList.toArray(new Object[dataList.size()][]);
		  
	  }
	 @AfterTest
	  public void finishExtent() {
		  reports.flush();
		  driver.close();
	  }
	 
}
