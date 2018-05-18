package core;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class BasePage {
	
	public void SendKeys(By by, String text){
		DriverFactory.getDriver().findElement(by).clear();
		DriverFactory.getDriver().findElement(by).sendKeys(text);
	}
	
	public void Click(By by){
		DriverFactory.getDriver().findElement(by).click();
	}
	
	public String GetText(By by){
		return DriverFactory.getDriver().findElement(by).getText();
	}
	
	public void SendKeys(String path, String text){
		DriverFactory.getDriver().findElement(By.xpath(path)).clear();
		DriverFactory.getDriver().findElement(By.xpath(path)).sendKeys(text);
	}
	
	public void SelectByVisibleText(By by, String valor){
		WebElement element = DriverFactory.getDriver().findElement(by);
		Select combo = new Select(element);
		combo.selectByVisibleText(valor);
	}
}
