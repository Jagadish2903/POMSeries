package com.qa.demoopencart.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.demoopencart.base.BaseTest;
import com.qa.demoopencart.utils.Constants;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
//epic and Story are for allure report customisation 
@Epic("EPIC 10:Open Democart -Design LoginPage")
@Story("US1: Login page features with basic functionalities ")

public class LoginPageTest extends BaseTest {
	// Note: Never ever use driver.findelement in Testclass

	@Description("Login Page Title Test....")
	@Severity(SeverityLevel.NORMAL)
	@Test
	public void loginPageTitleTest() {
		// to get the tite we will be using driver.get title but that is already used in
		// page class
		// so we can create a object and use direclt call
		// instead of creating a object here and to use for every test we need to cretae
		// a object so we created in Baseclass
		// since loginpageTest extends BaseTest
		String title = loginPage.getLoginPageTitle();
		Assert.assertEquals(title, Constants.LOGIN_PAGE_TITLE);// here Account login is a Constants class so we can
																// define that in constant class

	}

	@Description("Login Page url Test....")
	@Severity(SeverityLevel.NORMAL)
	@Test
	public void loginPageUrlTest() {
		String url = loginPage.getLoginPageUrl();
		Assert.assertTrue(url.contains(Constants.LOGIN_PAGE_URL_VALUE));

	}

	@Description("Login Page fpwd Test....")
	@Severity(SeverityLevel.CRITICAL)
	@Test
	public void forgotpwdlinkTest() {
		Assert.assertTrue(loginPage.isForgotpwdlinkExist());
	}

	@Description("Login Page registrationlink Test....")
	@Severity(SeverityLevel.CRITICAL)
	@Test
	public void registerLinkTest() {
		Assert.assertTrue(loginPage.isRegisterlinkExist());
	}

	@Description("Login Page Title Test....")
	@Severity(SeverityLevel.BLOCKER)
	@Test
	public void loginTest() {
		// loginPage.doLogin("Jack.yuvan@gmail.com","Jaggu123");
		loginPage.doLogin(prop.getProperty("username").trim(), prop.getProperty("password").trim());
	}
	// note:whenever using property file always use trim to avoid spaces in first
	// and last
	
	//here @description and @severity annotations is a allure report Annotation that can be used for customise the allure report 
	}
