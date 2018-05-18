package core;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverFactory {
	
	private static WebDriver driver = null;
	
	private DriverFactory(){}
	
	public static WebDriver getDriver(){
		if(driver != null)
			return driver;
		else
			return initDriver();
	}
	
	public static WebDriver initDriver(){
		switch(Properties.browser){
			case FIREFOX: 
				System.setProperty("webdriver.gecko.driver", "C:/Users/Base2/Downloads/drivers/geckodriver.exe");
				driver = new FirefoxDriver(); break;
			case CHROME:
				System.setProperty("webdriver.chrome.driver", "C:/Users/Base2/Downloads/drivers/chromedriver.exe");
				driver = new ChromeDriver(); break;
		}
		driver.manage().window().setSize(new Dimension(1200, 700));	
	
		return driver;
	}
	
	public static void killDriver(){
		WebDriver driver = getDriver();
		if(driver != null){
			driver.quit();
			driver = null;
		}
	}
}
