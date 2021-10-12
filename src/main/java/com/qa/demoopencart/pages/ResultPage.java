package com.qa.demoopencart.pages;



import java.util.List;

import org.openqa.selenium.By;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.demoopencart.utils.Constants;
import com.qa.demoopencart.utils.ElementUtil;

public class ResultPage {

	
	
	private WebDriver driver;
	
	private ElementUtil elementUtil;
	
	private By searchHeader=By.cssSelector("div#content h1");
	private By productResultList=By.cssSelector("div.caption a");

	
	
	public ResultPage(WebDriver driver) {
	this.driver=driver;
	elementUtil=new ElementUtil(driver);
	}
	
	public String getSearchHeaderText() {
		return elementUtil.getText(searchHeader);
	}
	
	public int getSearchProductListCount() {
		return elementUtil.waitforVisibilityofElements(productResultList, Constants.DEFAULT_TIME_OUT).size();
	}
	
	public ProductInfoPage doProductClick(String mainProduct) {
		
		List<WebElement>searchList=  elementUtil.waitforVisibilityofElements(productResultList, Constants.DEFAULT_TIME_OUT);
		
		for(WebElement e:searchList) {
			String text=e.getText().trim();
			if(text.equals(mainProduct)) {
				e.click();
				break;
			}
		}
		return new ProductInfoPage(driver);
	}
	
}
