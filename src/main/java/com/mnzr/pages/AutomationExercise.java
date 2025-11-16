package com.mnzr.pages;

import com.microsoft.playwright.Page;
import com.mnzr.support.ConfigReader;
import com.mnzr.support.Log;

public class AutomationExercise extends BasePage {

	protected AutomationExercise(Page page) {
		super(page);
		String baseUrl = ConfigReader.get("base.url");
		Log.info("AutomationExercise URL:: " + baseUrl);
		page.navigate(baseUrl);
		Log.info("AutomationExercise page loaded");
	}

}
