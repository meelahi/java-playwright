package com.mnzr.factory;

import java.nio.file.Paths;
import java.util.Arrays;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.mnzr.support.ConfigReader;
import com.mnzr.support.Log;

public class PlaywrightFactory {

    private static final ThreadLocal<Playwright> playwrightThread = new ThreadLocal<>();
    private static final ThreadLocal<Browser> browserThread = new ThreadLocal<>();
    private static final ThreadLocal<BrowserContext> contextThread = new ThreadLocal<>();
    private static final ThreadLocal<Page> pageThread = new ThreadLocal<>();

    @SuppressWarnings("resource")
	public static void initBrowser() {
        try {
            if (playwrightThread.get() != null) {
                Log.warn("Playwright already initialized on this thread. Skipping re-initialization.");
                return;
            }

            Playwright playwright = Playwright.create();
            playwrightThread.set(playwright);

            String browserName = ConfigReader.get("browser", "chrome");
            boolean headless = ConfigReader.getBoolean("headless");
            int slowMo = ConfigReader.getInt("slowMo", 0);

            BrowserType.LaunchOptions options = new BrowserType.LaunchOptions()
                    .setHeadless(headless)
                    .setSlowMo(slowMo)
                    .setArgs(Arrays.asList("--start-maximized"));

            Browser browser;
            switch (browserName.toLowerCase()) {
                case "firefox":
                    browser = playwright.firefox().launch(options);
                    break;
                case "webkit":
                    browser = playwright.webkit().launch(options);
                    break;
                default:
                    browser = playwright.chromium().launch(options);
            }
            browserThread.set(browser);

            Browser.NewContextOptions contextOptions = new Browser.NewContextOptions()
                    .setViewportSize(2560, 1440);

			String videoDir = ConfigReader.get("videos.dir");
			if (videoDir != null && !videoDir.isEmpty()) {
				contextOptions.setRecordVideoDir(Paths.get(videoDir));
			}
            
            BrowserContext context = browser.newContext(contextOptions);
            contextThread.set(context);

            Page page = context.newPage();
            pageThread.set(page);

			Log.info("Initialized browser: " + browserName + ", headless=" + headless);
        } catch (Exception e) {
            Log.error("Error initializing Playwright: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static Page getPage() {
        return pageThread.get();
    }

    public static BrowserContext getContext() {
        return contextThread.get();
    }

    public static Browser getBrowser() {
        return browserThread.get();
    }

    public static Playwright getPlaywright() {
        return playwrightThread.get();
    }

    @SuppressWarnings("resource")
	public static void cleanup() {
        try {
            if (getContext() != null) getContext().close();
            if (getBrowser() != null) getBrowser().close();
            if (getPlaywright() != null) getPlaywright().close();

            Log.info("Playwright resources cleaned up successfully");
        } catch (Exception e) {
            Log.error("Error during cleanup: " + e.getMessage());
        } finally {
            contextThread.remove();
            browserThread.remove();
            playwrightThread.remove();
            pageThread.remove();
        }
    }
}
