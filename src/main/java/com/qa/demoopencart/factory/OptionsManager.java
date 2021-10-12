package com.qa.demoopencart.factory;

import java.util.Properties;

import org.openqa.selenium.chrome.ChromeOptions;

import org.openqa.selenium.firefox.FirefoxOptions;

public class OptionsManager {

	// Incase if one needs to run the driver in headless mode which is defined in
	// property file
	// certainly he needs to define it in the Driver factory since driver
	// initialization is done there
	// but since it is tedious to maintain all there and according to SRP(Single
	// responsive principle) we have created Options manager class

	// created a private properties to create a costructor
	private Properties prop;
	private ChromeOptions co;// to create a objectof chromeoptions
	private FirefoxOptions fo;

	// constructor for property

	public OptionsManager(Properties prop) {
		this.prop = prop;
	}

	public ChromeOptions getChromeOptions() {
		co = new ChromeOptions();

		if (Boolean.parseBoolean(prop.getProperty("headless"))) {
			co.addArguments("--headless");
		}
		if (Boolean.parseBoolean(prop.getProperty("incognito"))) {
			co.addArguments("--incognito");
		}
		return co;
	}

	public FirefoxOptions getFirefoxOptions() {
		fo = new FirefoxOptions();

		if (Boolean.parseBoolean(prop.getProperty("headless"))) {
			fo.addArguments("--headless");
		}
		if (Boolean.parseBoolean(prop.getProperty("incognito"))) {
			fo.addArguments("--incognito");
		}
		return fo;
	}

	
	//to invoke all these in the driver factory add co/fo to the driver 
}
