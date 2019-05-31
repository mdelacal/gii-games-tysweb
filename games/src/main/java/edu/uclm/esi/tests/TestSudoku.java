package edu.uclm.esi.tests;

import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class TestSudoku {
	private WebDriver driverMiguel, driverWerselio;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();

	public TestSudoku() {
		System.setProperty("webdriver.gecko.driver", "C:/geckodriver.exe");
	}

	@Before
	public void setUp() throws Exception {
		driverMiguel = new FirefoxDriver();
		driverWerselio = new FirefoxDriver();
		driverMiguel.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driverWerselio.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void testSudoku() throws Exception {
		// MIGUEL
		driverMiguel.get("http://localhost:8080/");
		driverMiguel.findElement(By.id("userNameLogin")).click();
		driverMiguel.findElement(By.id("userNameLogin")).clear();
		driverMiguel.findElement(By.id("userNameLogin")).sendKeys("miguel");
		driverMiguel.findElement(By.id("pwdLogin")).clear();
		driverMiguel.findElement(By.id("pwdLogin")).sendKeys("miguel123");
		driverMiguel.findElement(By.id("btnLogin")).click();

		// WERSELIO
		driverWerselio.get("http://localhost:8080/");
		driverWerselio.findElement(By.id("userNameLogin")).click();
		driverWerselio.findElement(By.id("userNameLogin")).clear();
		driverWerselio.findElement(By.id("userNameLogin")).sendKeys("werselio");
		driverWerselio.findElement(By.id("pwdLogin")).clear();
		driverWerselio.findElement(By.id("pwdLogin")).sendKeys("werselio123");
		driverWerselio.findElement(By.id("btnLogin")).click();

		driverMiguel.findElement(By.id("btnPlaySudoku")).click();
		driverWerselio.findElement(By.id("btnPlaySudoku")).click();
		Thread.sleep(3000);
		
		// MIGUEL: click en una celda y cambiar el valor
		new Select(driverMiguel.findElement(By.id("cellPropio-7"))).selectByVisibleText("5");
		new Select(driverMiguel.findElement(By.id("cellPropio-64"))).selectByVisibleText("1");
		
		Thread.sleep(5000);

		driverMiguel.close();
		driverWerselio.close();
	}

	@After
	public void tearDown() throws Exception {
		driverMiguel.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}

	private boolean isElementPresent(By by) {
		try {
			driverMiguel.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	private boolean isAlertPresent() {
		try {
			driverMiguel.switchTo().alert();
			return true;
		} catch (NoAlertPresentException e) {
			return false;
		}
	}

	private String closeAlertAndGetItsText() {
		try {
			Alert alert = driverMiguel.switchTo().alert();
			String alertText = alert.getText();
			if (acceptNextAlert) {
				alert.accept();
			} else {
				alert.dismiss();
			}
			return alertText;
		} finally {
			acceptNextAlert = true;
		}
	}
}
