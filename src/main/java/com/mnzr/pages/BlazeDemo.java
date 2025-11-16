package com.mnzr.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.mnzr.support.Log;

public class BlazeDemo extends BasePage {
	public BlazeDemo(Page page) {
		super(page);
		String baseUrl = "https://blazedemo.com/";
		Log.info("BlazeDemo URL:: " + baseUrl);
		page.navigate(baseUrl);
		Log.info("BlazeDemo page loaded");
	}
	
	public Locator pageHeaderTitle() {
		return $(".container h1");
	}
}
