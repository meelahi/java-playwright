package com.mnzr.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.mnzr.pages.GoogleSearch;

public class GoogleSearchTest extends BaseTest {
	@Test
	public void validateGoogleSearch() {
		String searchText = "Amex";
		try {
			GoogleSearch google = new GoogleSearch(page);
			takeScreenshot("Google home page loaded");
			
			google.search(searchText);
			takeScreenshot("Searched for: " + searchText);
			
		} catch (Exception e) {
			e.printStackTrace();
			takeScreenshot("validateGoogleSearch failed");
			Assert.fail();
		}
	}

}
