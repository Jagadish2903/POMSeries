package com.qa.demoopencart.utils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.demoopencart.factory.DriverFactory;

public class ElementUtil {

	private WebDriver driver;
	private JavaScriptUtil jsUtil;

	public ElementUtil(WebDriver driver) {
		this.driver = driver;
		jsUtil=new JavaScriptUtil (driver);
	}

	public WebElement getElement(By locator) {
		
		WebElement ele=driver.findElement(locator);
		if(Boolean.parseBoolean(DriverFactory.highlight)){
			jsUtil.flash(ele);
		}
		return ele;
	}

	public List<WebElement> getElements(By locator) {
		return driver.findElements(locator);
	}

	public void doActionSendKeys(By locator, String value) {
		Actions act = new Actions(driver);
		act.sendKeys(getElement(locator), value).build().perform();
	}

	public void doActionClick(By locator) {
		Actions act = new Actions(driver);
		act.click(getElement(locator)).build().perform();
	}

	public void doSendKeys(By locator, String value) {
		WebElement element=getElement(locator);
		element.clear();
		element.sendKeys(value);
	}

	public void doClick(By locator) {
		getElement(locator).click();
	}

	public String getText(By locator) {
		return getElement(locator).getText();
	}

	public boolean doIsDisplayed(By locator) {
		return getElement(locator).isDisplayed();
	}

	public boolean doISEnabled(By locator) {
		return getElement(locator).isEnabled();
	}

	public boolean doIsSelected(By locator) {
		return getElement(locator).isSelected();
	}

	public String dogetAttributeValue(By locator, String attributename) {
		return getElement(locator).getAttribute(attributename);
	}

	public void doLinkClick(By locator, String linkvalue) {
		List<WebElement> linkslist = driver.findElements(locator);

		for (WebElement e : linkslist) {
			String langtext = e.getText();
			System.out.println(linkslist);

			if (langtext.trim().equals(linkvalue)) {
				e.click();
				break;
			}
		}
	}

	public boolean checkElementDisplayed(By locator, int eleOccurance) {
		if (getElements(locator).size() == eleOccurance)
			return true;
		return false;
	}

	/*********************************
	 * dropdown Utility
	 *****************************/

	public void doSelectByIndex(By locator, int index) {
		Select select = new Select(getElement(locator));
		select.selectByIndex(index);

	}

	public boolean doSelectByValue(By locator, String value) {
		Select select = new Select(getElement(locator));
		select.selectByValue(value);
		return isDropDownValueSelected(select, value);
	}

	public boolean doSelectByVisibleText(By locator, String Text) {
		Select select = new Select(getElement(locator));
		select.selectByVisibleText(Text);
		return isDropDownValueSelected(select, Text);
	}

	public boolean isDropDownValueSelected(Select select, String expValue) {
		if (select.getFirstSelectedOption().getText().contains(expValue)) {
			return true;
		}
		return false;
	}

	public void doSelectDropDown(By locator, String value) {
		Select select = new Select(getElement(locator));
		List<WebElement> optionslist = select.getOptions();
		iterateDropdownSelect(optionslist, value);
	}

	public void selectDropdownWithoutSelect(By locator, String value) {
		List<WebElement> optionslist = getElements(locator);
		iterateDropdownSelect(optionslist, value);
	}

	private void iterateDropdownSelect(List<WebElement> optionslist, String value) {
		for (WebElement e : optionslist) {
			String text = e.getText();
			System.out.println(text);
			if (text.equals(value)) {
				e.click();
				break;
			}
		}
	}

	public void googleSearchSelect(By search, By suggestion, String searchKey, String value)
			throws InterruptedException {
		getElement(search).sendKeys(searchKey);
		Thread.sleep(2000);
		List<WebElement> suggestionlist = getElements(suggestion);

		for (WebElement e : suggestionlist) {
			String text = e.getText().trim();
			System.out.println(text);
			if (text.equals(value)) {
				e.click();
				break;
			}
		}
	}

	/***************************************************
	 * Actions Util
	 *************************************/

	public void parentChildMenuHandle(By parent, By child) {
		Actions act = new Actions(driver);
		act.moveToElement(getElement(parent)).build().perform();
		// getElement(child).click();(Instead of using it since we have already a method
		// for click we can use tat)
		doClick(child);
	}

	public int getRightClickItemsCount(By rightclick, By items) {
		return getrightClickChecklist(rightclick, items).size();
	}

	public void rightclickItem(By rightclick, By items, String value) {
		Actions act = new Actions(driver);
		act.contextClick(getElement(rightclick)).build().perform();
		List<WebElement> menuList = getElements(items);
		for (WebElement e : menuList) {
			String text = e.getText();
			if (text.equalsIgnoreCase(value)) {
				e.click();
				break;
			}
		}
	}

	public List<String> getrightClickChecklist(By rightclick, By items) {
		List<String> itemsValuelist = new ArrayList<String>();
		Actions act = new Actions(driver);
		act.contextClick(getElement(rightclick)).build().perform();
		List<WebElement> menuList = getElements(items);
		for (WebElement e : menuList) {
			String text = e.getText();
			System.out.println(text);
			itemsValuelist.add(text);
		}
		return itemsValuelist;
	}

	/***************************************************
	 * Wait Utils
	 *************************************/

	public Alert waitforJSAlert(int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		return wait.until(ExpectedConditions.alertIsPresent());
	}

	public void acceptAlert(int timeout) {
		waitforJSAlert(timeout).accept();
	}

	public void dismissAlert(int timeout) {
		waitforJSAlert(timeout).dismiss();
	}

	public String alertGetText(int timeout) {
		Alert alert = waitforJSAlert(timeout);
		String text = alert.getText();
		alert.accept();
		return text;
	}

	public void doSendKeysAlert(int timeout, String value) {
		waitforJSAlert(timeout).sendKeys(value);
	}

	public boolean waitforURLContains(int timeout, String Containsvalue) {
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		return wait.until(ExpectedConditions.urlContains(Containsvalue));
	}

	public boolean waitUrlToBe(int timeout, String expectedURL) {
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		return wait.until(ExpectedConditions.urlToBe(expectedURL));
	}

	public String waitforTitleContains(int timeout, String titleFraction) {
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		if (wait.until(ExpectedConditions.titleContains(titleFraction))) {
			return driver.getTitle();
		}
		return null;
	}

	public String waitforTitleIs(int timeout, String ExpectedTitle) {
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		if (wait.until(ExpectedConditions.titleIs(ExpectedTitle))) {
			return driver.getTitle();
		}
		return null;
	}

	public void waitForFramesusingNameorID(String FrameIDorName, int Timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Timeout);
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(FrameIDorName));
	}

	public void waitForFramesusingframeByLocator(By frameLocator, int Timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Timeout);
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
	}

	public void waitForFramesusingframeIndex(int frameIndex, int Timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Timeout);
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIndex));
	}

	public void waitForFramesusingElementLocator(String frameElement, int Timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Timeout);
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameElement));
	}

	/**
	 * An expectation for checking that an element is present on the DOM of a page.
	 * This does notnecessarily mean that the element is visible.
	 * 
	 * @param locator
	 * @param timeout
	 * @return
	 */

	public WebElement waitForElementPresent(By locator, long timeout) {
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}

	public WebElement waitForElementPresent(By locator, long timeout, long IntervalTime) {
		WebDriverWait wait = new WebDriverWait(driver, timeout, IntervalTime);
		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}

	public List<WebElement> waitforPresenceofElements(By locator, int timouts) {
		WebDriverWait wait = new WebDriverWait(driver, timouts);
		return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
	}

	public List<String> getListofElementsusingWaits(By locator, int timeouts) {
		List<WebElement> elelists = waitforPresenceofElements(locator, timeouts);
		List<String> eleVallist = new ArrayList<String>();
		for (WebElement e : elelists) {
			eleVallist.add(e.getText());
		}
		return eleVallist;
	}

	public void printListofElements(By locator, int timeouts) {
		List<WebElement> elelist = waitforPresenceofElements(locator, timeouts);
		for (WebElement e : elelist) {
			System.out.println(e.getText());
		}
	}
	
	public List<WebElement> waitforVisibilityofElements(By locator, int timouts) {
		WebDriverWait wait = new WebDriverWait(driver, timouts);
		return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
	}

	public List<String> getVisibleListofElementsusingWaits(By locator, int timeouts) {
		List<WebElement> elelists = waitforVisibilityofElements(locator, timeouts);
		List<String> eleVallist = new ArrayList<String>();
		for (WebElement e : elelists) {
			eleVallist.add(e.getText());
		}
		return eleVallist;
	}

	/**
	 * An expectation for checking that an element is present on the DOM of a page
	 * and visible. Visibility means that the element is not only displayed but also
	 * has a height and width that isgreater than 0.
	 * 
	 * @param locator
	 * @param timeout
	 * @return
	 */

	public WebElement waitForElementVisible(By locator, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	public WebElement waitforElementtobeClickable(By locator, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		return wait.until(ExpectedConditions.elementToBeClickable(locator));
	}

	public void clickWhenReady(By locator, int timeout) {
		waitforElementtobeClickable(locator, timeout).click();
	}
	
	
	public WebElement waitforElementPresentwithFluentwait(By locator, int timeout, int pollingtime) {

		Wait<WebDriver> waits = new FluentWait<WebDriver>(driver)
				.withTimeout(Duration.ofSeconds(timeout))
					.withMessage(Error.Timeout_WebElement_msg)// error is a class and its imported from error class using builder pattern
						.pollingEvery(Duration.ofMillis(pollingtime))
							.ignoring(NoSuchElementException.class, StaleElementReferenceException.class);
		return waits.until(ExpectedConditions.presenceOfElementLocated(locator));
	}
	
	public Alert waitforAlertPresentwithFluentwait(int timeout, int pollingtime) {

		Wait<WebDriver> waits = new FluentWait<WebDriver>(driver)
				.withTimeout(Duration.ofSeconds(timeout))
					.withMessage(Error.Timeout_Alrt_msg)
						.pollingEvery(Duration.ofMillis(pollingtime))
							.ignoring(NoAlertPresentException.class);
		return waits.until(ExpectedConditions.alertIsPresent());
	}
	
	public WebDriver waitforFramePresentwithFluentwait(By framelocator, int timeout, int pollingtime) {

		Wait<WebDriver> waits = new FluentWait<WebDriver>(driver)
				.withTimeout(Duration.ofSeconds(timeout))
					.withMessage(Error.Timeout_Frame_msg)
						.pollingEvery(Duration.ofMillis(pollingtime))
							.ignoring(NoSuchFrameException.class);
		return waits.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(framelocator));
	}
	

}
