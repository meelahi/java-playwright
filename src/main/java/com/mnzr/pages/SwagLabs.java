package com.mnzr.pages;

import java.util.Arrays;
import java.util.List;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.mnzr.support.Log;

public class SwagLabs extends BasePage {

	public SwagLabs(Page page) {
		super(page);
		String baseUrl = "https://www.saucedemo.com/";
		Log.info("SwagLabs URL:: " + baseUrl);
		page.navigate(baseUrl);
		Log.info("Swag Labs page loaded");
	}
	
	public Locator pageHeaderTitle() {
		return $(".login_logo");
	}
	
	public Locator txtUserName() {
		return $("#user-name");
	}
	
	public Locator txtPassword() {
		return $("#password");
	}
	
	public Locator LoginButton() {
		return $("#login-button");
	}
	
	public Locator AcceptedUsernames() {
		return $("#login_credentials");
	}
	
	public Locator PasswordForAll() {
		return $(".login_password");
	}
	
	public void waitForPageLoad() {
		waitForVisible(".login_logo");
		shouldBeVisible(pageHeaderTitle());
		info("SwagLabs page loaded successfully");
	}
	
	public void enterUserName(String username) {
		txtUserName().fill(username);
	}
	
	public void enterPassword(String password) {
		txtPassword().fill(password);
	}
	
	public void clickLogin() {
		LoginButton().click();
	}
	
	public void Login(String username, String password) {
		info("Logging in with: " + username);
		enterUserName(username);
		enterPassword(password);
		clickLogin();
		info("SwagLabs logged in.");
	}
	
	public List<String> getAcceptedUsernames() {
		String userNames = AcceptedUsernames().innerText();
		userNames = userNames.split(":")[1].trim();
		return Arrays.asList(userNames.split("\n"));
	}
	
	public String getPasswordForAll() {
		String password = PasswordForAll().innerText().split(":")[1].trim();
		return password;
	}

}
