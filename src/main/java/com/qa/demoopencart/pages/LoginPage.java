package com.qa.demoopencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.demoopencart.utils.ElementUtil;

import io.qameta.allure.Step;

public class LoginPage {

	private WebDriver driver;// defining it in the class level as private cox this driver is should not be
								// used anywhere as per POM Structure

	private ElementUtil elementUtil;

	// in Page package we need to define mainly two things
	// 1.By locators (or)Page Objects and should be in private in nature to achive
	// Encapsulaton

	// 2.Page Constructor

	// 3.Page actions/methods/in the form of features

	// 1.By locators (or)Page Objects and should be in private in nature to achive
	// Encapsulaton
	private By emaild = By.id("input-email");
	private By pwd = By.id("input-password");
	private By loginbtn = By.xpath("//input[@type='submit']");
	private By forgotpwdlink = By.linkText("Forgotten Password");
	private By registerlink = By.xpath("//*[@id=\"column-right\"]/div/a[2]");

	// 2.Page Constructor

	// creating a Pageb Constructor
	// constructor should be same as class name

	public LoginPage(WebDriver driver) {
		this.driver = driver;
		elementUtil = new ElementUtil(driver); // created a object inside the constructor since the same driver can be
												// given to ElementUtl as well
	}

	// 3.Page actions/methods/in the form of features

	@Step("Getting Loginpage Title")//this @ Step is allure report annotations for report view purpose
	public String getLoginPageTitle() {
		return driver.getTitle();
	}

	@Step("Getting Loginpage URL")//this @ Step is  allure report annotations for report view purpose
	public String getLoginPageUrl() {
		return driver.getCurrentUrl();
	}

	@Step("checking forgotpwd link exists")//this @ Step is  allure report annotations for report view purpose
	public boolean isForgotpwdlinkExist() {
		// return driver.findElement(forgotpwdlink).isDisplayed();
		return elementUtil.doIsDisplayed(forgotpwdlink);
	}

	@Step("checking Registration link exists")//these are allure report annotations for report view purpose
	public boolean isRegisterlinkExist() {
		// return driver.findElement(registerlink).isDisplayed();
		return elementUtil.doISEnabled(registerlink);
	}

	@Step("Login with username : {0} and password: {1}") //this @ Step is   allure report annotations for report view purpose
	public AccountsPage doLogin(String username, String password) {
//		driver.findElement(emaild).sendKeys("jack.yuvan@gmail.com");
//		driver.findElement(pwd).sendKeys("Jaggu123");
//		driver.findElement(loginbtn).click();
		elementUtil.doSendKeys(emaild, username);
		elementUtil.doSendKeys(pwd, password);
		elementUtil.doClick(loginbtn);
		return new AccountsPage(driver);
	}

	// instead of using driver.findelements everytime we can use elementUtil created
	// Note: in this we have used Encapsulation where private By locator is
	// encapsulated by Public methods

	// According to page chaining concept its responsibility of loginpage to handle
	// the object of landingpage(AccountsPage)
	// so created a object and return the same 
	//but the reference should be created in base test and use the same reference in AccPageTest

	
	@Step("Navigating to RegistrationPage")
	public RegistrationPage navigatetoRegistrationPage() {
		elementUtil.doClick(registerlink);
		return new RegistrationPage(driver);
	}
   //@ Step is a Allure annotation and can be used to customize the report 
	//Epic is a something overall feature which is divided into small small user stories 
	
}
