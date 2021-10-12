package com.qa.demoopencart.tests;



import java.util.Random;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.demoopencart.base.BaseTest;
import com.qa.demoopencart.utils.Constants;
import com.qa.demoopencart.utils.ExcelUtil;

public class ResgistrationPageTest extends BaseTest {
		
	
	@BeforeClass
	public void registrationPageSetup() {
		registrationPage=loginPage.navigatetoRegistrationPage();
	}
	
	//to create a random number for the email for generating email we have used this 
	public String getRandomNumber() {
		Random random =new Random();
		String email="Jaggu"+random.nextInt(2000)+"@gmail.com";
		return email;
	}
	
	
	@DataProvider
	public Object[][] getRegisterData() {
		//ExcelUtil.getExcelData("register") instead of passing sheet name directly we can use constants 
		Object data[][]=ExcelUtil.getTestData(Constants.REGISTER_SHEET_NAME);
		return data;
	}

	
	
	
	@Test(dataProvider="getRegisterData")
	public void registrationPageTest(String firstname,String lastname,String email,String telphone,String password,String subscribe) {
		Assert.assertTrue(registrationPage.doRegistration(firstname,lastname,getRandomNumber(),telphone,password,subscribe));
	
	}

	
//	@Test
//	public void registrationPageTest( ) {
//		Assert.assertTrue(registrationPage.doRegistration("Ushas", "uthups", "Ushauthasp@gmail.com", "987562231", "Ush6a123", "Yes"));
//	
//	}
	//in the above case we have one case to be registered what if one wants to register multiple users 
	//instead of data provider we can use the excel to import the data 
	//The Flow is ...First Excel then Excel Utility ---testdata ----Testannotation
	//create a excel with sheet name as register and file name as opencartTestData and copy and save it in the Testdata in Resources folder
	
	//to achieve this we need apache POI dependencies so copy that and paste in POM.xml under dependencies 
	//then in excel Util create a utility for excel 
	
}
