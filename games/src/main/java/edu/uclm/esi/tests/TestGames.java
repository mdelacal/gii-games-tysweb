package edu.uclm.esi.tests;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class TestGames {
  private WebDriver driverPepe, driverAna;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  public TestGames() {
	  System.setProperty("webdriver.chrome.driver", "D:/OneDrive - Universidad de Castilla-La Mancha/Universidad/4º/1º CUATRIMESTRE/Tecnologías y Sistemas Web/Prácticas/chromedriver.exe");
	  System.setProperty("webdriver.gecko.driver", "D:/OneDrive - Universidad de Castilla-La Mancha/Universidad/4º/1º CUATRIMESTRE/Tecnologías y Sistemas Web/Prácticas/geckodriver.exe");
  }
  
  @Before
  public void setUp() throws Exception {
	driverPepe = new ChromeDriver();
	driverAna = new ChromeDriver();
    baseUrl = "https://www.katalon.com/";
    driverPepe.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    driverAna.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testGames() throws Exception {
	login();
	
	WebElement pepe=driverPepe.findElement(By.xpath("/html/body/table/tbody/tr[2]/td[1]/a[3]"));
	pepe.click();
	
	WebElement ana=driverAna.findElement(By.xpath("/html/body/table/tbody/tr[2]/td[2]/a[3]"));
	ana.click();
	
	assertTrue(driverPepe.getPageSource().contains("winner"));
	assertTrue(driverAna.getPageSource().contains("winner"));
  
	pepe=driverPepe.findElement(By.xpath("/html/body/table/tbody/tr[2]/td[1]/a[4]"));
	pepe.click();
	
	ana=driverAna.findElement(By.xpath("/html/body/table/tbody/tr[2]/td[2]/a[4]"));
	ana.click();
	
	pepe=driverPepe.findElement(By.xpath("/html/body/table/tbody/tr[2]/td[1]/a[5]"));
	pepe.click();
	
	ana=driverAna.findElement(By.xpath("/html/body/table/tbody/tr[2]/td[2]/a[5]"));
	ana.click();
	
	pepe=driverPepe.findElement(By.xpath("/html/body/table/tbody/tr[2]/td[1]/a[6]"));
	pepe.click();
	
	ana=driverAna.findElement(By.xpath("/html/body/table/tbody/tr[2]/td[2]/a[6]"));
	ana.click();
	Thread.sleep(500);
	String textoQueNoTieneQueEstar="\"winner\":{\"userName\":\"ana\"";
	assertTrue(!driverPepe.getPageSource().contains(textoQueNoTieneQueEstar));
	assertTrue(driverAna.getPageSource().contains(textoQueNoTieneQueEstar));
  }
  
  private void login() throws Exception{
    driverPepe.get("http://localhost:8080/index.html");
    driverPepe.findElement(By.linkText("Piedra, papel, tijera")).click();
    
    driverAna.get("http://localhost:8080/index.html");
    driverAna.findElement(By.linkText("Piedra, papel, tijera")).click();
    
    driverPepe.findElement(By.xpath("/html/body/table/tbody/tr[2]/td[1]/a[2]")).click();
    driverAna.findElement(By.xpath("/html/body/table/tbody/tr[2]/td[2]/a[2]")).click();
    
    assertTrue(driverPepe.getPageSource().contains("userName"));
    assertTrue(driverAna.getPageSource().contains("userName"));
    
    /*
    driver.findElement(By.id("userNameLogin")).click();
    driver.findElement(By.id("userNameLogin")).clear();
    driver.findElement(By.id("userNameLogin")).sendKeys(userName);
    driver.findElement(By.id("pwdLogin")).clear();
    driver.findElement(By.id("pwdLogin")).sendKeys(pwd);
    driver.findElement(By.id("btnLogin")).click();
    driver.findElement(By.id("gamesList")).click();
    driver.findElement(By.id("gamesList")).click();
    */
  }

  @After
  public void tearDown() throws Exception {
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  private boolean isElementPresent(By by) {
    try {
      driverPepe.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driverPepe.switchTo().alert();
      //driverAna.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driverPepe.switchTo().alert();
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
