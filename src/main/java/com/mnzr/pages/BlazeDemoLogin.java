package com.mnzr.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.mnzr.support.Log;

public class BlazeDemoLogin extends BasePage{
	public BlazeDemoLogin(Page page) {
		super(page);
		String baseUrl = "https://blazedemo.com/login";
		Log.info("BlazeDemo Login URL:: " + baseUrl);
		page.navigate(baseUrl);
		Log.info("BlazeDemo page loaded");
	}
	
	public Locator LoginBox() {
		return $(".form-horizontal");
	}
}
