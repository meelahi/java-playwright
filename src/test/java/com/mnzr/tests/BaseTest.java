package com.mnzr.tests;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;

import com.microsoft.playwright.Page;
import com.mnzr.factory.PlaywrightFactory;
import com.mnzr.support.ConfigReader;
import com.mnzr.support.Log;

public abstract class BaseTest {

	protected Page page;

	@BeforeMethod(alwaysRun = true)
	public void setUp() {
		PlaywrightFactory.initBrowser();
		page = PlaywrightFactory.getPage();
		Log.info("Test setup complete");
	}

	@AfterMethod(alwaysRun = true)
	public void tearDown() {
		PlaywrightFactory.cleanup();
	}

	@AfterSuite(alwaysRun = true)
	public void afterSuite() {
		// Reserved for report flushing, Allure lifecycle cleanup, etc.
		Log.info("Test suite completed");
	}

	protected void takeScreenshot(String name) {
		try {
			byte[] bytes = page.screenshot(
					new Page.ScreenshotOptions()
							.setFullPage(true)
							.setPath(getScreenshotPath(name)));
			Log.info("Screenshot saved: " + getScreenshotPath(name));
		} catch (Exception e) {
			Log.error("Failed to take screenshot: " + e.getMessage());
		}
	}

	private Path getScreenshotPath(String name) throws Exception {
		String dir = ConfigReader.get("screenshots.dir");
		if (dir == null || dir.isEmpty())
			dir = "target/screenshots";
		Path folder = Paths.get(dir);
		Files.createDirectories(folder);
		return folder.resolve(name + "_" + System.currentTimeMillis() + ".png");
	}
}
