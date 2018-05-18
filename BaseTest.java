package core;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;


import java.io.File;
import java.io.IOException;
public class BaseTest {
	
	@Rule
	public TestName testName = new TestName();
	
	@BeforeClass
	public static void inicializa(){
		DriverFactory.getDriver().get("http://mantis-prova.base2.com.br/login_page.php");
	}
	
	@After
	public void finaliza() throws IOException{
		TakesScreenshot ss = (TakesScreenshot) DriverFactory.getDriver();
		File arquivo = ss.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(arquivo, new File("target" + File.separator + 
				"screenshot" + File.separator + testName.getMethodName() + ".jpg"));
	}
	
	@AfterClass
	public static void killDriver(){
		DriverFactory.killDriver();
	}
	
}
