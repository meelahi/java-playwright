package com.mnzr.tests;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.mnzr.pages.SwagLabs;
import com.mnzr.tests.base.BaseTest;

public class SwagLabsTest extends BaseTest {
	@Test()
	public void validateSwagLabs() {
		try {
			SwagLabs labsPage = new SwagLabs(page);
			takeScreenshot("SwagLabs test page loaded");

			int countUserNames = labsPage.getAcceptedUsernames().size();
			Assert.assertTrue(countUserNames > 0, "Acceptable user names are found on page");

			var result = Reporter.getCurrentTestResult();
			int current = result.getMethod().getCurrentInvocationCount();

			String userName = labsPage.getAcceptedUsernames().get(current);
			labsPage.Login(userName, labsPage.getPasswordForAll());
		} catch (Exception e) {
			e.printStackTrace();
			takeScreenshot("validateSwagLabs failed");
		}
	}
}
