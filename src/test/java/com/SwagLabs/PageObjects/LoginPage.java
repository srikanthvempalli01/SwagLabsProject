package com.SwagLabs.PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage 
{
	WebDriver ldriver;
	public LoginPage(WebDriver rdriver)
	{
		ldriver=rdriver;
		PageFactory.initElements(rdriver,this);
	}
	
	@FindBy(xpath="//input[@id='user-name']")  
	WebElement username;
	@FindBy(xpath="//input[@id='password']") 
	WebElement password;
	@FindBy(xpath="//input[@id='login-button']") 
	WebElement login;
	@FindBy(xpath="//button[@id='react-burger-menu-btn']") 
	WebElement openMenu;
	@FindBy(xpath="//a[@id='logout_sidebar_link']") 
	WebElement logout;
	
	public void enterUsername(String user)
	{
		username.sendKeys(user);
	}
	public void enterPassword(String passwd)
	{
		password.sendKeys(passwd);
	}
	public void clickLogin()
	{
		login.click();
	}
	public void clickOpenMenu()
	{
		openMenu.click();
	}
	public void clickLogout()
	{
		logout.click();
	}
}

