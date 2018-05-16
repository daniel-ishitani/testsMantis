package Tests;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

import core.Properties;

public class HomepageTest {
	WebDriver driver = null;
	
	@Before
	public void init(){
		switch(Properties.browser){
		case FIREFOX: 
			System.setProperty("webdriver.gecko.driver", "C:/Users/Base2/Downloads/drivers/geckodriver.exe");
			driver = new FirefoxDriver(); break;
		case CHROME:
			System.setProperty("webdriver.chrome.driver", "C:/Users/Base2/Downloads/drivers/chromedriver.exe");
			driver = new ChromeDriver(); break;
		}
		driver.get("http://mantis-prova.base2.com.br/login_page.php");
	}
	
	
	@Rule
	public TestName testName = new TestName();
	
	@After
	public void close() throws IOException{
		TakesScreenshot ss = (TakesScreenshot) driver;
		File arquivo = ss.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(arquivo, new File("target" + File.separator + 
				"screenshot" + File.separator + testName.getMethodName() + ".jpg"));
		
		driver.close();
	}
	
	
	public void login(String user, String password){
		driver.findElement(By.xpath("//input[@name='username']")).clear();
		driver.findElement(By.xpath("//input[@name='username']")).sendKeys(user);
		driver.findElement(By.xpath("//input[@type='password']")).clear();
		driver.findElement(By.xpath("//input[@name='password']")).sendKeys(password);
		driver.findElement(By.xpath("//input[@value='Login']")).click();
	}
	
	@Test
	public void loggedIn(){
		login("daniel.ishitani", "asd");
		
		Assert.assertEquals("daniel.ishitani", driver
				.findElement(By.xpath("//span[@class='italic'][contains(text(),'daniel.ishitani')]")).getText());
	}
	
	@Test
	public void wrongPassword(){
		String error = "Your account may be disabled or blocked or the username/password you entered is incorrect.";
		login("daniel.ishitani", "123");
		Assert.assertEquals(error, driver.findElement(By.xpath("//font[@color='red']")).getText());
	}
	
	@Test
	public void wrongUsername(){
		String error = "Your account may be disabled or blocked or the username/password you entered is incorrect.";
		login("danielishitani", "asd");
		Assert.assertEquals(error, driver.findElement(By.xpath("//font[@color='red']")).getText());
	}
	
	@Test
	public void accessViewIssues(){
		login("daniel.ishitani", "asd");
		driver.findElement(By.xpath("//a[@href='/view_all_bug_page.php']")).click();
		Assert.assertTrue(driver.findElement(By.xpath("//table[@id='buglist']//td[@class='form-title']"))
				.getText().contains("Viewing Issues"));
	}
	
	@Test
	public void accessReportIssue(){
		login("daniel.ishitani", "asd");
		WebElement element = driver.findElement(By.xpath("//select[@name='project_id']"));
		Select combo = new Select(element);
		combo.selectByVisibleText("All Projects");
		driver.findElement(By.xpath("//a[@href='/bug_report_page.php']")).click();
		Assert.assertTrue(driver.findElement(By.xpath("//td[@class='form-title']"))
				.getText().contains("Select Project"));
	}
}
