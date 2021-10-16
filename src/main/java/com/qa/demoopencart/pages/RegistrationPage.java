package com.qa.demoopencart.pages;

import org.openqa.selenium.By;

import org.openqa.selenium.WebDriver;

import com.qa.demoopencart.utils.Constants;
import com.qa.demoopencart.utils.ElementUtil;

public class RegistrationPage {

	// 1.private element Util
	
	private ElementUtil elementUtil;
	


	// 2.private By Locators
	private By firstName = By.id("input-firstname");
	private By lastName = By.id("input-lastname");
	private By emailid = By.id("input-email");
	private By telePhone = By.id("input-telephone");
	private By password = By.id("input-password");
	private By confirmPwd = By.id("input-confirm");
	private By subscribeYes = By.xpath("(//label[@class='radio-inline']/input)[position()=1]");
	private By subscribeNo = By.xpath("(//label[@class='radio-inline']/input)[position()=2]");

	private By agreeChkbox = By.name("agree");
	private By continueBtn = By.xpath("//input[@type='submit']");
	private By accountSuccess = By.cssSelector("div#content h1");
	private By logOut = By.linkText("Logout");
	private By registerlink = By.xpath("//*[@id=\"column-right\"]/div/a[2]");
	
	// 3.Constructors
		public RegistrationPage(WebDriver driver) {
			elementUtil = new ElementUtil(driver);
			
		}

	// 4.Page actions

	public boolean doRegistration(String firstname, String lastname, String email, String teleph, String pwd,
			String subscribe) {
//		elementUtil.doSendKeys(this.firstName, firstname);//to differentiate the same name here this means access to private locator
//		elementUtil.doSendKeys(this.lastName, lastname);
//		elementUtil.doSendKeys(this.emailid, email);
//		elementUtil.doSendKeys(this.telePhone, teleph);
//		elementUtil.doSendKeys(this.password, pwd);
//		elementUtil.doSendKeys(this.confirmPwd, pwd);
//		
//		//we need to give in such a way that if user give yes it should click yes or else no so usig if else for subscribe
//		
//		if(subscribe.equalsIgnoreCase("yes")) {
//			elementUtil.doClick(subscribeYes);
//		}else {
//			elementUtil.doClick(subscribeNo);
//		}
//		
//		elementUtil.doClick(agreeChkbox);
//		elementUtil.doClick(continueBtn);
//		if(elementUtil.getText(accountSuccess).contains("Your Account Has Been Created!")) {
//			elementUtil.doClick(logOut);
//			elementUtil.doClick(registerlink);
//			return true;
//		}
//		return false;
//	
//	

		Registrationdata(firstname, lastname, email, teleph, pwd);
		doSubscribe(subscribe);
		agreeandContinue();
		return registrationStatus();

	}

	// instead of writing this we can write separe methods as private and do
	// encapsulate
	private boolean registrationStatus() {
		String mesg = elementUtil.getText(accountSuccess);
		if (mesg.contains(Constants.REGISTRATION_SUCCESS_MSG)) {
			elementUtil.doClick(logOut);
			elementUtil.doClick(registerlink);
			return true;
		}
		return false;

	}

	private void agreeandContinue() {

		elementUtil.doClick(agreeChkbox);
		elementUtil.doClick(continueBtn);
	}

	private void Registrationdata(String firstname, String lastname, String email, String teleph, String pwd) {
		elementUtil.doSendKeys(this.firstName, firstname);
		elementUtil.doSendKeys(this.lastName, lastname);
		elementUtil.doSendKeys(this.emailid, email);
		elementUtil.doSendKeys(this.telePhone, teleph);
		elementUtil.doSendKeys(this.password, pwd);
		elementUtil.doSendKeys(this.confirmPwd, pwd);
	}

	private void doSubscribe(String subscribe) {
		if (subscribe.equalsIgnoreCase("yes")) {
			elementUtil.doClick(subscribeYes);
		} else {
			elementUtil.doClick(subscribeNo);
		}

	}

}
