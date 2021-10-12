package com.qa.demoopencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverFactory {

	public WebDriver driver;
	public Properties prop; // defined in class level so that it can be used everyehere inside the class

	public static String highlight; // defined the property file for JS Alert

	public OptionsManager optionsmanager;

	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();
	// thread local has two methods 1.set and 2.get

	/**
	 * This method is used to initialize the driver based on the browser we give
	 * 
	 * @param browser
	 *            and this will return the WebDriver
	 */

	// now we need to read the property file to remove hardcoded url and browser
	// username and etc ...

	public WebDriver initDriver(Properties prop) {

		optionsmanager = new OptionsManager(prop); // creating object for optionsmanager

		highlight = prop.getProperty("highlight");

		String browser = prop.getProperty("browser").trim();// we are using .getproperty default available already in
															// java to get the property key present in the Config file

		// note:whenever using property file always use trim to avoid spaces in first
		// and last
		System.out.println("Browser used is " + browser);

		if (browser.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
			// driver = new ChromeDriver(optionsmanager.getChromeOptions()); // adding
			// reference from Optionsmanager class
			tlDriver.set(new ChromeDriver(optionsmanager.getChromeOptions())); // using thread set method
		} else if (browser.equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			// driver = new FirefoxDriver(optionsmanager.getFirefoxOptions());
			tlDriver.set(new FirefoxDriver(optionsmanager.getFirefoxOptions())); // using thread set method
		} else if (browser.equalsIgnoreCase("edge")) {
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
		} else {
			System.out.println("Please Pass the Correct browser name..." + browser);
		}

		getDriver().manage().window().maximize();
		getDriver().manage().deleteAllCookies();
		// driver.get("https://demo.opencart.com/index.php?route=account/login");
		getDriver().get(prop.getProperty("url"));// getting the key from the config file removing hardcoded

		return getDriver();
	}

	// using get threadlocal
	// used synchronized since n number of test use this driver so we are
	// synchronized
	// and use the same getdriver method in manage ,window,delete cookies ets
	public synchronized WebDriver getDriver() {
		return tlDriver.get();
	}

	/**
	 * This is used to Initialize property based on given environment
	 * 
	 * @return this method will return Properties
	 */

	public Properties initProperty() {
		prop = new Properties(); // creating a object for property class which is default available in java
		FileInputStream ip = null;

		String env = System.getProperty("env");// to run on multiple environment System .setproperty is a default
												// available in selenium

		if (env == null) {
			try {
				ip = new FileInputStream("./src/test/resources/config/config.properties");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} // Use FileInputStream which is default available in java and copy the path of
				// config and replace with starting with . adn then /

		}

		try {
			switch (env.toLowerCase()) {
			case "qa":
				ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
				break;
			default:
				System.out.println("Please pass correct environment " + env);
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			prop.load(ip);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;// return the prop
	}

	
	/**
	 * this method is used to take screenshots 
	 */
	public String getScreenshot() {
		File srcFile=((TakesScreenshot)getDriver()).getScreenshotAs(OutputType.FILE);
		String path=System.getProperty("user.dir")+"/Screenshot/" +System.currentTimeMillis()+".png";
		File destination=new File(path);
		try {
			FileUtils.copyFile(srcFile, destination);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path;
	}
	
	
	// we have not passed the environment anywer to check on specific env for that
	// we need maven command to do ...install the maven and set environmental
	// variable and also in cmd
	// move to the root directory we need(i.e E:)
	// after that pass the cd and the workspace path
	// now it will be in root directory
	// then pass mvn clean install -Denv="qa"(to run the test in QA environment from
	// the CMD)

	// Threadlocal concepts= if there is n number of testcase and one wants to run
	// all the testmethods there is a chances of
	// reports of first test can be replaced by second Tests hebce wen we create a
	// thread local we can create a thread for
	// all tests as local threads that can call the driver and run and give report
	// higher chances of false report will happen when we dont use Thread local
	// these threads are generated by TESTNG
	// But Managing it is done by Java --->Thread local concepts are done by Java
	
	
	//Listerners 
	//Listerners are used to listen the execution and give results of execution in the form of reports (Extend Reports)
	//We have to activate this listerner by passing it with testrunner 
	//listerners are some libraries provided by extend reports 
	//no need to memorize it 
	//we need to add dependency in pom.xml and property in pom.xml 
	//for taking screenshots its selenium responsibility so we have to write method for taking screenshots
	//to take a screenshots we have created a method in driver factory
	
	
	
	
	//Allure reports 
	//First we need to add some dependencies to work with allure reports 
	//it has many features and supports many languages java with testNG cucumber etc 
	//plugins are used to build the project 
	//two plugins are there in allure reports
	//1.compiler plugin --used to compile the code 
	//2.surefire plugin ---used to execute the testcases 
	//in surefire plugin under suite xml define the path of regression testrunner file to run the testcases 
	//add all the dependencies for allure and Update the project using Right click on the project and select Maven-->Update Project
	//then add Allure reporting listeners in listeners path
	//pass the same listener in runner file(testNGRegression.xml)
	//run the regression.xml 
	//and then refresh the project to show the extend reports as well as Allure reports 
	//Allure reports are generated in Allure results folder as a Json file 
	//Allure report is a webservice report to know the allure report we need to download allure server 
	//install the scoop in powersheel and then install allure in powershell
	// in the cmd go to workspace directory 
	// then type allure serve allure-reports 
	//this will give the allure report 
	// we can customize the report as well 
	//lot of features are there in allure reports 
	//always delete the allure results and build and screenshot for next results 
	// we can customize the reports by adding @description 
}
