package com.qa.demoopencart.base;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.asserts.SoftAssert;

import com.qa.demoopencart.factory.DriverFactory;
import com.qa.demoopencart.pages.AccountsPage;
import com.qa.demoopencart.pages.LoginPage;
import com.qa.demoopencart.pages.ProductInfoPage;
import com.qa.demoopencart.pages.RegistrationPage;
import com.qa.demoopencart.pages.ResultPage;

public class BaseTest {

	public WebDriver driver; //this is initialised just to use driver inside the method and initially it is null
	 //driver is already initialised in DriverInitialise in DriverFactory class so create a objecct for driver factory to use the driver
	
	public DriverFactory df;
	
	public Properties prop;
	
	public LoginPage loginPage;
	
	public AccountsPage accPage;//object reference of accountspage
	
	public ResultPage resultsPage;//object reference of resultpage
	
	public ProductInfoPage productInfoPage;//object reference of productinfopage
	
	public RegistrationPage registrationPage;
	
	public SoftAssert softAssert;
	
	@BeforeTest
	
	public void setUp() {
		df=new DriverFactory(); //created object to use the initialised driver.
		prop=df.initProperty();//we are calling the initProperty method using driverfactory class so that we can remove hardcoded values(URL/Username/Password etc
		//driver=df.initDriver("chrome");   //return type of initdriver= Webdriver so making driver as a reference and now the driver is initialised and same driver (Chrome Driver will be given to Webdriver driver and used to quit as well)
		driver=df.initDriver(prop); //loading property file to the driver so tat we can decide which property can be used accordingly 
		
		loginPage=new LoginPage(driver);//object of Loginpage refer LoginpageTest
		
		softAssert=new SoftAssert();
		
	}
	
	
	
	@AfterTest
	
	public void tearDown() {
		driver.quit();
	}
	
}
