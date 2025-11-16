package com.mnzr.pages;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
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
}
