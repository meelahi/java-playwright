package com.mnzr.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.mnzr.pages.SwagLabs;
import com.mnzr.support.ConfigReader;
import com.mnzr.support.Log;
import com.mnzr.tests.base.BaseTest;

public class SwagLabsTest extends BaseTest {
	@Test
    public void validateSwagLabs() {
		try {
			String baseUrl = ConfigReader.get("base.url");
			Log.info("URL:: " + baseUrl);
	        page.navigate(baseUrl);
	        takeScreenshot("Page loaded");
	        
	        SwagLabs labsPage = new SwagLabs(page);
	        
	        int countUserNames = labsPage.getAcceptedUsernames().size();
	        Assert.assertTrue(countUserNames > 0, "Acceptable user names are found on page");
	        
	        String userName = labsPage.getAcceptedUsernames().get(0);
	        labsPage.Login(userName, labsPage.getPasswordForAll());
		} catch  (Exception e) {
			e.printStackTrace();
			takeScreenshot("validateSwagLabs failed");
		}
	}
}
