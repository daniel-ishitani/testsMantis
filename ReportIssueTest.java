package Tests;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import core.Properties;

public class ReportIssueTest {
	
	static WebDriver driver = null;
	
	@BeforeClass
	public static void initialize(){
		switch(Properties.browser){
		case FIREFOX: 
			System.setProperty("webdriver.firefox.bin", "C:/Users/Base2/Downloads/drivers/geckodriver.exe");
			driver = new FirefoxDriver(); break;
		case CHROME:
			System.setProperty("webdriver.chrome.driver", "C:/Users/Base2/Downloads/drivers/chromedriver.exe");
			driver = new ChromeDriver(); break;
		}
		
		driver.get("http://mantis-prova.base2.com.br/login_page.php");
		driver.findElement(By.xpath("//input[@name='username']")).clear();
		driver.findElement(By.xpath("//input[@name='username']")).sendKeys("daniel.ishitani");
		driver.findElement(By.xpath("//input[@type='password']")).clear();
		driver.findElement(By.xpath("//input[@name='password']")).sendKeys("asd");
		driver.findElement(By.xpath("//input[@value='Login']")).click();
	}
	
	@AfterClass
	public static void close(){
		driver.close();
	}
	
	@Rule
	public TestName testName = new TestName();
	
	@After
	public void testScreenshot() throws IOException{
		TakesScreenshot ss = (TakesScreenshot) driver;
		File arquivo = ss.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(arquivo, new File("target" + File.separator + 
				"screenshot" + File.separator + testName.getMethodName() + ".jpg"));
		
	}
	
	@Test
	public void test1_reportIssue(){
		WebElement element = driver.findElement(By.xpath("//select[@name='project_id']"));
		Select combo = new Select(element);
		combo.selectByVisibleText("All Projects");
		driver.findElement(By.xpath("//a[@href='/bug_report_page.php']")).click();
		driver.findElement(By.xpath("//input[@value='Select Project']")).click();
		element = driver.findElement(By.xpath("//select[@name='category_id']"));
		combo = new Select(element);
		combo.selectByVisibleText("[All Projects] Teste");
		driver.findElement(By.xpath("//input[@name='summary']")).sendKeys("Create report");
		driver.findElement(By.xpath("//textarea[@name='description']")).sendKeys("Testing Report Issue");
		driver.findElement(By.xpath("//input[@value='Submit Report']")).click();
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//table[@id='buglist']//td[@class='form-title']")));
		Assert.assertTrue(driver.findElement(By.xpath("//table[@id='buglist']//td[@class='form-title']"))
				.getText().contains("Viewing Issues"));
	}
	
	@Test
	public void reportMissingCategory(){
		String error = "APPLICATION ERROR #11";
		driver.findElement(By.xpath("//a[@href='/bug_report_page.php']")).click();
		driver.findElement(By.xpath("//input[@name='summary']")).sendKeys("Create report");
		driver.findElement(By.xpath("//textarea[@name='description']")).sendKeys("Testing Report Issue");
		driver.findElement(By.xpath("//input[@value='Submit Report']")).click();
		Assert.assertEquals(error , driver.findElement(By.xpath("//td[@class='form-title']")).getText());
	}
	
	@Test
	public void reportMissingSummary(){
		String error = "APPLICATION ERROR #11";
		driver.findElement(By.xpath("//a[@href='/bug_report_page.php']")).click();
		WebElement element = driver.findElement(By.xpath("//select[@name='category_id']"));
		Select combo = new Select(element);
		combo.selectByVisibleText("[All Projects] Teste");
		driver.findElement(By.xpath("//textarea[@name='description']")).sendKeys("Testing Report Issue");
		driver.findElement(By.xpath("//input[@value='Submit Report']")).click();
		Assert.assertEquals(error, driver.findElement(By.xpath("//td[@class='form-title']")).getText());
	}

	
	@Test
	public void reportMissingDescription(){
		String error = "APPLICATION ERROR #11";
		driver.findElement(By.xpath("//a[@href='/bug_report_page.php']")).click();
		WebElement element = driver.findElement(By.xpath("//select[@name='category_id']"));
		Select combo = new Select(element);
		combo.selectByVisibleText("[All Projects] Teste");
		driver.findElement(By.xpath("//input[@name='summary']")).sendKeys("Create report");
		driver.findElement(By.xpath("//input[@value='Submit Report']")).click();
		Assert.assertEquals(error, driver.findElement(By.xpath("//td[@class='form-title']")).getText());
	}
	
	
	@Test
	public void test2_deleteIssue(){
		driver.findElement(By.xpath("//a[@href='/view_all_bug_page.php']")).click();
		driver.findElement(By.xpath("//tr[@bgcolor]//td[4]")).click();
		driver.findElement(By.xpath("//input[@type='submit'][@value='Delete']")).click();
		driver.findElement(By.xpath("//input[@type='submit'][@value='Delete Issues']")).click();
		Assert.assertTrue(driver.findElement(By.xpath("//table[@id='buglist']//td[@class='form-title']"))
				.getText().contains("Viewing Issues (0 - 0 / 0)"));
	}
}
