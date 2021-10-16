package com.qa.demoopencart.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.demoopencart.base.BaseTest;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

//epic and Story are for allure report customisation 
@Epic("EPIC 12:Open Democart -Design ProductInfoPage")
@Story("US3: Product Info page features with basic functionalities ")
public class ProductInfoPageTest extends BaseTest{

	@BeforeClass
	public void ProductInfoPageSetUp() {
		accPage=loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}
	
	
	
	@DataProvider
	public Object[][] productHeaderData() {
		return new Object[][] {
			{"macbook","MacBook Pro","MacBook Pro"},
			{"imac","iMac","iMac"},
			{"apple","Apple Cinema 30\"","Apple Cinema 30\""}
		};
	} 
	
	@Description("ProductInfo Page Headerdata Test....")//Allure Annotations for customisation of allure reports 
	@Severity(SeverityLevel.CRITICAL)
	@Test(dataProvider="productHeaderData")
	public void productHeaderTest(String productName,String mainProductName,String headerName) {
		resultsPage=accPage.doSearch(productName);
		productInfoPage=resultsPage.doProductClick(mainProductName);
		String actualHeaderText=productInfoPage.getproductHeaderText();
		Assert.assertEquals(actualHeaderText, headerName);
	}
	
	
	
	@DataProvider
	public Object[][] getproductImageCount() {
		return new Object[][] {
			{"macbook","MacBook Pro",4},
			{"imac","iMac",3},
			{"apple","Apple Cinema 30\"",6}
		};
	} 
	
	@Description("ProductInfo Page ImageCount Test....")//Allure Annotations for customisation of allure reports 
	@Severity(SeverityLevel.NORMAL)
	@Test(dataProvider="getproductImageCount")
	public void productImageCount(String productName,String mainProductName,int ImageCount) {
		resultsPage=accPage.doSearch(productName);
		productInfoPage=resultsPage.doProductClick(mainProductName);
		Assert.assertEquals(productInfoPage.getproductImageCount(),ImageCount );
	}
	
	
	@Description("ProductInfo Page MetaData Test....")//Allure Annotations for customisation of allure reports 
	@Severity(SeverityLevel.CRITICAL)
	@Test
	public void productMetaDataTest() {
		resultsPage = accPage.doSearch("macbook");
		productInfoPage = resultsPage.doProductClick("MacBook Pro");
		Map<String, String> actProdMap = productInfoPage.getProductMetaData();
		actProdMap.forEach((k,v) -> System.out.println(k + ":" + v));
		softAssert.assertEquals(actProdMap.get("productname"), "MacBook Pro");
		softAssert.assertEquals(actProdMap.get("Brand"), "Apple");
		softAssert.assertEquals(actProdMap.get("Product Code"), "Product 18");
		softAssert.assertEquals(actProdMap.get("price"), "$2,000.00");
		softAssert.assertAll();
		
		//diff betwwen soft assert and hard assert is that 
		//Soft Assert is used to assert all assert even if one fails not like hard asssert 
		//and for soft assert we need to use asssertAll at the end to assert all the methods
		//in this case instead of macbook pro we have written macbook pro11 just to know trhe case 
		//when to use soft assert?
		//when there is a n no of assert in a method and to know all the status we can use Soft Assert
		
		
	}
}
