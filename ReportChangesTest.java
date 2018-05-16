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

public class ReportChangesTest {
	static WebDriver driver = null;
	
	@BeforeClass
	public static void initialize(){
		switch(Properties.browser){
		case FIREFOX: 
			System.setProperty("webdriver.gecko.driver", "C:/Users/Base2/Downloads/drivers/geckodriver.exe");
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
	}
		
	@AfterClass
	public static void deleteIssue(){
		driver.findElement(By.xpath("//a[@href='/view_all_bug_page.php']")).click();
		driver.findElement(By.xpath("//tr[@bgcolor]/td[4]")).click();
		driver.findElement(By.xpath("//input[@type='submit'][@value='Delete']")).click();
		driver.findElement(By.xpath("//input[@type='submit'][@value='Delete Issues']")).click();
		
		driver.close();
	}
	
	@Rule
	public TestName testName = new TestName();
	
	@After
	public void close() throws IOException{
		
		TakesScreenshot ss = (TakesScreenshot) driver;
		File arquivo = ss.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(arquivo, new File("target" + File.separator + 
				"screenshot" + File.separator + testName.getMethodName() + ".jpg"));
	}
	
	@Test
	public void assignTo(){
		driver.findElement(By.xpath("//a[@href='/view_all_bug_page.php']")).click();
		driver.findElement(By.xpath("//tr[@bgcolor]/td[4]")).click();
		driver.findElement(By.xpath("//input[@type='submit'][@value='Assign To:']")).click();;
		Assert.assertEquals("daniel.ishitani", driver
				.findElement(By.xpath("//html/body/table[@class='width100']//tr[6]/td[2]")).getText());
	}
	
	@Test
	public void editPriority(){
		driver.findElement(By.xpath("//a[@href='/view_all_bug_page.php']")).click();
		driver.findElement(By.xpath("//tr[@bgcolor]/td[4]")).click();
		driver.findElement(By.xpath("//input[@type='submit'][@value='Edit']")).click();
		WebElement element = driver.findElement(By.xpath("//select[@name='priority']"));
		Select combo = new Select(element);
		combo.selectByVisibleText("none");
		driver.findElement(By.xpath("//input[@value='Update Information']")).click();
		Assert.assertEquals("none", 
				driver.findElement(By.xpath("//html/body/table[@class='width100']//tr[7]//td[2]")).getText());
	}
	
	
	@Test
	public void editStatus(){
		driver.findElement(By.xpath("//a[@href='/view_all_bug_page.php']")).click();
		driver.findElement(By.xpath("//html//tr[@bgcolor]/td[4]")).click();
		WebElement element = driver.findElement(By.xpath("//select[@name='new_status']"));
		Select combo = new Select(element);
		combo.selectByVisibleText("acknowledged");
		driver.findElement(By.xpath("//input[@type='submit'][@value='Change Status To:']")).click();
		driver.findElement(By.xpath("//input[@type='submit'][@value='Acknowledge Issue']")).click();
		Assert.assertEquals("acknowledged", driver.findElement(By.xpath("//td[@bgcolor='#ffcd85']")).getText());
	}
	
	@Test
	public void editViewStatus(){
		driver.findElement(By.xpath("//a[@href='/view_all_bug_page.php']")).click();
		driver.findElement(By.xpath("//html//tr[@bgcolor]/td[4]")).click();
		driver.findElement(By.xpath("//input[@type='submit'][@value='Edit']")).click();
		WebElement element = driver.findElement(By.xpath("//select[@name='view_state']"));
		Select combo = new Select(element);
		combo.selectByVisibleText("private");
		driver.findElement(By.xpath("//input[@value='Update Information']")).click();
		Assert.assertEquals("private", driver
				.findElement(By.xpath("//tr[@class='row-1']//td[contains(text(),'private')]")).getText());
	}
	
	
	@Test
	public void uploadFile(){
		driver.findElement(By.xpath("//a[@href='/view_all_bug_page.php']")).click();
		driver.findElement(By.xpath("//html//tr[@bgcolor]/td[4]")).click();
		driver.findElement(By.xpath("//input[@id='ufile[]']"))
			.sendKeys("C:/Users/Base2/Desktop/Teste.jpg");
		driver.findElement(By.xpath("//input[@type='submit'][@value='Upload File']")).click();
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='relationships_open']")));
		WebElement image = driver.findElement(By.xpath("//img[@alt=''][@style]"));
		Assert.assertNotNull(image);
	}
	
}
