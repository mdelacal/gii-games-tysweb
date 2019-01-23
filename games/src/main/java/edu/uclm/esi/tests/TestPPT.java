package edu.uclm.esi.tests;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;

import org.bson.BsonDocument;
import org.bson.BsonString;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

import edu.uclm.esi.mongolabels.dao.MongoBroker;

public class TestPPT {
  private WebDriver driverPepe, driverAna;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  public TestPPT() {
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
    
    BsonDocument criterion=new BsonDocument();
    criterion.put("email", new BsonString("pepe@pepe.com"));
    MongoBroker.get().delete("Player", criterion);
  }

  public void testRegistrar() throws Exception {
	  driverPepe.get("http://localhost:8080/index.html");
	  driverPepe.findElement(By.linkText("Ir a ejemplos con JSON, POST y Angular")).click();
	  
	  WebElement wePepe=driverPepe.findElement(By.id("email"));
	  wePepe.clear();
	  wePepe.sendKeys("pepe@pepe.com");
	  
	  wePepe=driverPepe.findElement(By.id("userName"));
	  wePepe.clear();
	  wePepe.sendKeys("pepe");
	  
	  wePepe=driverPepe.findElement(By.id("pwd1"));
	  wePepe.clear();
	  wePepe.sendKeys("pepe123");
	  
	  wePepe=driverPepe.findElement(By.id("pwd2"));
	  wePepe.clear();
	  wePepe.sendKeys("pepe1234");

	  Thread.sleep(2000);
	  wePepe=driverPepe.findElement(By.xpath("//*[@id=\"divRegister\"]/div/button"));
	  wePepe.click();
	  
	  
	  Thread.sleep(1000);
	  Thread.sleep(1000);//new
	  wePepe=driverPepe.findElement(By.id("mensajeRegistro"));
	  Thread.sleep(1000);//new
	  String mensaje=wePepe.getText();
	  Thread.sleep(1000);//new
	  assertTrue(mensaje.toLowerCase().contains("error"));
	  
	  wePepe=driverPepe.findElement(By.id("pwd2"));
	  wePepe.clear();
	  wePepe.sendKeys("pepe123");
	  
	  wePepe=driverPepe.findElement(By.xpath("//*[@id=\"divRegister\"]/div/button"));
	  wePepe.click();
	  Thread.sleep(1000);
	  wePepe=driverPepe.findElement(By.id("mensajeRegistro"));
	  mensaje=wePepe.getText();
	  assertTrue(mensaje.contains("OK")); 
  }
  @Test
  public void testLogin() throws Exception{
	  testRegistrar();
	  WebElement wePepe=driverPepe.findElement(By.id("userNameLogin"));
	  wePepe.clear();
	  wePepe.sendKeys("pepe");
	  
	  wePepe=driverPepe.findElement(By.id("pwdLogin"));
	  wePepe.clear();
	  wePepe.sendKeys("pepe1234");//pass incorrecta
	  
	  wePepe=driverPepe.findElement(By.id("btnLogin"));
	  wePepe.click();
	  Thread.sleep(2000);
	  String mensaje=closeAlertAndGetItsText();
	  assertTrue(mensaje.equals("Error"));
	  
	  wePepe=driverPepe.findElement(By.id("pwdLogin"));
	  wePepe.clear();
	  wePepe.sendKeys("pepe123"); //pass correcta, login correcto => se cargan los juegos
	  
	  wePepe=driverPepe.findElement(By.id("btnLogin"));
	  wePepe.click();
	  Thread.sleep(2000);
  
	  wePepe=driverPepe.findElement(By.id("gamesList"));
	  assertTrue(wePepe.getText().contains("tictactoe"));
	  
	  //Logueamos a Ana
	  driverAna.get("http://localhost:8080/index.html");
	  driverAna.findElement(By.linkText("Ir a ejemplos con JSON, POST y Angular")).click();
	  WebElement weAna=driverAna.findElement(By.id("userNameLogin"));
	  weAna.clear(); weAna.sendKeys("ana");
	  
	  weAna=driverAna.findElement(By.id("pwdLogin"));
	  weAna.clear(); weAna.sendKeys("ana123");
	  
	  weAna=driverAna.findElement(By.id("btnLogin"));
	  weAna.click();
	  Thread.sleep(2000);
	  
	  weAna=driverAna.findElement(By.id("gamesList"));
	  assertTrue(weAna.getText().contains("tictactoe"));
  }
  
  public void testJoinGame() throws Exception{
	  testLogin();
	  WebElement wePepe=driverPepe.findElement(By.id("gamesList"));
	  wePepe.click();
	  wePepe.findElement(By.id("tictactoe"));
	  wePepe.click();
	  wePepe.findElement(By.id("btnJoinGame"));
	 
	  
  }

  @After
  public void tearDown() throws Exception {
	driverPepe.quit();
	driverAna.quit();
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
