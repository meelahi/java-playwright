package com.mnzr.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.mnzr.support.Log;

public class GoogleSearch extends BasePage {
	public GoogleSearch(Page page) {
		super(page);
		String baseUrl = "https://www.google.com/";
		Log.info("Google URL:: " + baseUrl);
		page.navigate(baseUrl);
		Log.info("Google home page loaded");
	}
	
	public GoogleSearch(Page page, String searchTerm) {
		super(page);
		String baseUrl = "https://www.google.com/search?q=" + searchTerm;
		Log.info("Google URL:: " + baseUrl);
		page.navigate(baseUrl);
		Log.info("Google home page loaded");
	}
	
	public Locator searchTextBox() {
		return title("Search");
	}
	
	public void search(String searchText) {
		searchTextBox().fill(searchText);
		takeScreenshot("Typed: " + searchText);
		searchTextBox().press("Enter");
        page.waitForSelector("h3");
	}

}
