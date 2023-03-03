package com.example.produktapi;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.locators.RelativeLocator;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import java.util.regex.*;

@SpringBootTest
class ProduktapiApplicationTests {

/*
public String baseUrl = "https://java22.netlify.app/";


	@Test
	public void testTotalNumberOfProducts() {

		WebDriver driver = new ChromeDriver();

		// This is a MUST for the test to work!
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		driver.get(baseUrl);

		// Hämta det totala antalet produkter och jämför med det förväntade antalet
		List<WebElement> products = driver.findElements(By.className("productItem"));

		Assertions.assertEquals(20, products.size(),"Wrong number of products");

		driver.quit();
	}

	@Test
	public void testPriceOfProductFjallraven() {

		WebDriver driver = new ChromeDriver();

		driver.get(baseUrl);

		// ta ut priset från texten
		WebElement priceElement = new WebDriverWait(driver, Duration.ofSeconds(20))
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[contains(text(), 'Fin väska me plats för dator')]")));

		// ta ut priset från texten
		String priceText = priceElement.getText();
		String price = priceText.replaceAll("[^\\d.]", ""); //Vissa elements behöver man ta bort punkten efter d i regexen

		// kör själva testet
		Assertions.assertEquals("109.95", price, "The product price does not match");

		driver.quit();

	}

	@Test
	public void testPriceOfProductMensCasualPremiumFitTShirts() {

		WebDriver driver = new ChromeDriver();

		driver.get(baseUrl);

		WebElement priceElement = new WebDriverWait(driver, Duration.ofSeconds(20))
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[contains(text(), 'Vilken härlig t-shirt, slim fit o casual i ett!')]")));

		String priceText = priceElement.getText();
		String price = priceText.replaceAll("[^\\d.]", "");

		Assertions.assertEquals("22.3", price, "The product price does not match");

		driver.quit();
	}

	@Test
	public void testPriceOfProductMensCasualSlimFit() {

		WebDriver driver = new ChromeDriver();

		driver.get("https://java22.netlify.app/");

		WebElement priceElement = new WebDriverWait(driver, Duration.ofSeconds(20))
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[contains(text(), 'Mer casual än såhär blir det inte!')]")));

		String priceText = priceElement.getText();
		String price = priceText.replaceAll("[^\\d.]", "");

		Assertions.assertEquals("15.99", price, "The product price does not match");

		driver.quit();
	}

	*/
}


