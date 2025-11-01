package com.mnzr.reports;

import java.io.ByteArrayInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.microsoft.playwright.Page;
import com.mnzr.factory.PlaywrightFactory;
import com.mnzr.support.Log;

import io.qameta.allure.Allure;

/**
 * Handles automatic screenshot capture and Allure attachment for Playwright-based TestNG tests.
 */
public class PlaywrightTestListener implements ITestListener {

	@Override
	public void onTestStart(ITestResult result) {
		Log.info("Starting test: " + result.getMethod().getMethodName());
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		captureAndAttachScreenshot(result, "success");
		Log.info("Test passed: " + result.getMethod().getMethodName());
	}

	@Override
	public void onTestFailure(ITestResult result) {
		captureAndAttachScreenshot(result, "failure");
		Log.error("Test failed: " + result.getMethod().getMethodName());
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		captureAndAttachScreenshot(result, "skipped");
		Log.warn("Test skipped: " + result.getMethod().getMethodName());
	}

	@SuppressWarnings("resource")
	private void captureAndAttachScreenshot(ITestResult result, String status) {
		try {
			Page page = PlaywrightFactory.getPage();
			if (page == null) {
				Log.warn("No active page found — skipping screenshot");
				return;
			}
			if (page.isClosed()) {
				Log.warn("Page already closed — skipping screenshot");
				return;
			}

			// Take screenshot
			byte[] bytes = page.screenshot(new Page.ScreenshotOptions().setFullPage(true));

			// Save to file system
			Path dir = Paths.get("target", "screenshots");
			Files.createDirectories(dir);
			String filename = String.format(
					"%s_%s_%d.png",
					result.getMethod().getMethodName(),
					status,
					System.currentTimeMillis());
			Path filePath = dir.resolve(filename);
			Files.write(filePath, bytes);

			// Attach to Allure report
			Allure.addAttachment(result.getMethod().getMethodName(), new ByteArrayInputStream(bytes));

			Log.info("Screenshot captured: " + filePath);

		} catch (Exception e) {
			Log.error("Failed to capture screenshot: " + e.getMessage());
		}
	}

	@Override
	public void onStart(ITestContext context) {
		Log.info("Test suite started: " + context.getName());
	}

	@Override
	public void onFinish(ITestContext context) {
		Log.info("Test suite finished: " + context.getName());
	}
}
