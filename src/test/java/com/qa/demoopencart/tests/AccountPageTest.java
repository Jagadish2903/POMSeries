package com.qa.demoopencart.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.demoopencart.base.BaseTest;
import com.qa.demoopencart.utils.Constants;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

//epic and Story are for allure report customisation 
@Epic("EPIC 11:Open Democart -Design AccountPage")
@Story("US2: Account page features with basic functionalities ")
public class AccountPageTest extends BaseTest{

	
	@BeforeClass
	public void accountPageSetUp() {
		accPage=loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}
	
	//before class is something like a precondition for accountspage so written n before class so that it will be executed before test
	
	@Description("Account Page Title Test....")//Allure Annotations for customisation of allure reports 
	@Severity(SeverityLevel.NORMAL)
	@Test
	public void accPageTitleTest() {
		String title=accPage.getAccountPageTitle();
		System.out.println("The Title of the acc Page is "+title);
		Assert.assertEquals(title,Constants.ACCOUNT_PAGE_TITLE);
	}
	
	@Description("Account Page searchLink Test....")//Allure Annotations for customisation of allure reports 
	@Severity(SeverityLevel.CRITICAL)
	@Test
	public void accPageSearchlinkTest() {
		Assert.assertTrue(accPage.issearchExist());
	}
	
	@Description("Account Page LogOut Test....")//Allure Annotations for customisation of allure reports 
	@Severity(SeverityLevel.BLOCKER)
	@Test
	public void accPageLogoutlinkTest() {
		Assert.assertTrue(accPage.islogoutLinkExist());
	}
	
	@Description("Account Page EditLink Test....")//Allure Annotations for customisation of allure reports 
	@Severity(SeverityLevel.NORMAL)
	@Test
	public void accPageEditLinkTest() {
		Assert.assertTrue(accPage.iseditAccLinkExist());
	}
	
	@Description("Account Page headerList Test....")//Allure Annotations for customisation of allure reports 
	@Severity(SeverityLevel.NORMAL)
	@Test
	public void accPageheaderslistTest() {
		List<String> actualheaderText=accPage.headerSecText();
		System.out.println("The actual list of header available is "+actualheaderText);
		Assert.assertEquals(actualheaderText, Constants.EXP_HEADER_TEXT);
		
	}
	
	
	//it is not necessary to have a testclass for page class so for a page cahining we have created searchtest in Accountpage itself 
	
	
	@DataProvider
	public Object[][] productName() {
		return new Object[][] {
			{"macbook"},
			{"imac"},
			{"apple"}
		};
	}
	
	@Description("Account Page searchProduct Test....")//Allure Annotations for customisation of allure reports 
	@Severity(SeverityLevel.CRITICAL)
	@Test(dataProvider="productName")
	public void doSearchTest(String productName) {
		resultsPage=accPage.doSearch(productName);//return type of dosearchis reselt pasge so storing it in it and create a reference in baseclass
		Assert.assertTrue(resultsPage.getSearchProductListCount()>0);
	}
	
	
	
	
	@DataProvider
	public Object[][] productSelect() {
		return new Object[][] {
			{"macbook","MacBook Pro"},
			{"imac","iMac"},
			{"apple","Apple Cinema 30\""}
		};
	}
	
	@Description("Account Page ProductSelect Test....")//Allure Annotations for customisation of allure reports 
	@Severity(SeverityLevel.CRITICAL)
	@Test(dataProvider="productSelect")
	public void searchProductClick(String producName,String mainProductName) {
		resultsPage=accPage.doSearch(producName);
		resultsPage.doProductClick(mainProductName);
		
	}
}
