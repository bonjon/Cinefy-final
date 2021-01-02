package test.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.*;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestLogin {

	private static final String DRIVER = "webdriver.gecko.driver";
	private static final String LOGIN = "//*[@id=\"login\"]";

	private WebDriver setProps() {
		System.setProperty(DRIVER, "Drivers/geckodriver");
		WebDriver driver = new FirefoxDriver();
		driver.get("http://localhost:8080/Cinefy/index.jsp");
		return driver;
	}

	/*
	 * Author: Giovanni Pica
	 */

	@Test
	public void testLoginFails() {
		WebDriver driver = this.setProps();
		driver.findElement(By.xpath("//*[@id=\"username\"]")).sendKeys("test");
		driver.findElement(By.xpath("//*[@id=\"password\"]")).sendKeys("test");
		driver.findElement(By.xpath(LOGIN)).click();
		assertEquals("http://localhost:8080/Cinefy/LoginServlet", driver.getCurrentUrl());
		driver.close();
	}

	/*
	 * Author: Jacopo Onorati
	 */

	@Test
	public void testEmptyField() {
		WebDriver driver = this.setProps();
		driver.findElement(By.xpath(LOGIN)).click();
		WebElement label = driver.findElement(By.xpath("/html/body/div/div[2]/h6"));
		String output = label.getText();
		String attended = "This field cannot be empty";
		assertEquals(attended, output);
		driver.close();
	}
}
