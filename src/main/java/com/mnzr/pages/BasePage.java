package com.mnzr.pages;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.mnzr.support.ConfigReader;
import com.mnzr.support.Log;

public abstract class BasePage {

	protected final Page page;

	protected BasePage(Page page) {
		this.page = page;
	}

	protected Locator $(String selector) {
		return page.locator(selector);
	}

	protected Locator text(String text) {
		return page.getByText(text);
	}
	
	protected Locator title(String text) {
		return page.getByTitle(text);
	}

	protected void waitForVisible(String selector) {
		page.waitForSelector(selector);
		Log.info("Waited for visibility of: " + selector);
	}

	protected void shouldBeVisible(Locator locator) {
		assertThat(locator).isVisible();
	}

	protected void scrollTo(Locator locator) {
		locator.scrollIntoViewIfNeeded();
	}

	protected void info(String message) {
		Log.info(message);
	}
	
	protected void takeScreenshot(String name) {
		try {
			page.screenshot(
					new Page.ScreenshotOptions()
							.setFullPage(true)
							.setPath(getScreenshotPath(name)));
			Log.info("Screenshot saved: " + getScreenshotPath(name));
		} catch (Exception e) {
			e.printStackTrace();
			Log.error("Failed to take screenshot: " + e.getMessage());
		}
	}
	
	private Path getScreenshotPath(String name) throws Exception {
		String dir = ConfigReader.get("screenshots.dir");
		if (dir == null || dir.isEmpty())
			dir = "target/screenshots";
		Path folder = Paths.get(dir);
		Files.createDirectories(folder);
		name = name.replaceAll("[\\\\/:*?\"<>|]", "_");
		return folder.resolve(name + "_" + System.currentTimeMillis() + ".png");
	}
}
