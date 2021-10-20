package com.qa.demoopencart.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.demoopencart.utils.Constants;
import com.qa.demoopencart.utils.ElementUtil;

import io.qameta.allure.Step;

public class AccountsPage {

	//1.creating private webdriver and ELementUtil
	private WebDriver driver;
	private ElementUtil elementUtil;
	//2.By locators in private
	
	private By search=By.name("search");
	private By searchIcon=By.cssSelector("div span.input-group-btn");
	private By logoutLink=By.linkText("Logout");
	private By editAccLink=By.linkText("Edit Account");
	private By headerText=By.cssSelector("div#content h2");
	
	
	//3.Creating a Constructor
	
	public AccountsPage(WebDriver driver) {
		this.driver=driver;
		elementUtil=new ElementUtil(driver);
	}
	
	
	//4.Page action methods
	
	
	@Step("Getting AccountPage Title")//this @ Step is allure report annotations for report view purpose
	public String getAccountPageTitle() {
		return elementUtil.waitforTitleIs(Constants.DEFAULT_TIME_OUT, Constants.ACCOUNT_PAGE_TITLE);
		//we are using wait instead of getTitle cox once login in done it takes some time to load the account page thats why
		//default time and account page time are defined in Constants 
	}
	
	@Step("Checking logoutlink exist")//this @ Step is allure report annotations for report view purpose
	public boolean islogoutLinkExist() {
		return elementUtil.doIsDisplayed(logoutLink);
	}
	
	@Step("Checking search exists")//this @ Step is allure report annotations for report view purpose
	public boolean issearchExist() {
		System.out.println("This is issearch exist");
		return elementUtil.doIsDisplayed(search);
	}

	@Step("Checking EditLink exists")//this @ Step is allure report annotations for report view purpose
	public boolean iseditAccLinkExist() {
		return elementUtil.doIsDisplayed(editAccLink);
	}
	
	@Step("Getting Header Text")//this @ Step is allure report annotations for report view purpose
	public List<String> headerSecText() {
		List<WebElement> headerseclist=elementUtil.getElements(headerText);
		List<String> headertexts=new ArrayList <String>();
		for(WebElement e:headerseclist) {
			headertexts.add(e.getText());
		}
		return headertexts;
	}
	
	@Step("Navigating to Result Page")//this @ Step is allure report annotations for report view purpose
	public ResultPage doSearch(String productName) {
		
		elementUtil.doSendKeys(search, productName);
		elementUtil.doClick(searchIcon);
		return new ResultPage(driver);
	}
	

	
	
	
}
