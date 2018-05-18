package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import core.BasePage;
import core.DriverFactory;

public class ReportIssuePage extends BasePage{
	
	public void ClicarSubmitReport(){
		Click(By.xpath("//input[@value='Submit Report']"));
	}
	
	public void ClicarSelectProject(){
		Click(By.xpath("//input[@value='Select Project']"));
	}
	
	public void SelecionarProjeto(String valor){
		SelectByVisibleText(By.name("project_id"), valor);
	}
	
	public void SelecionarCategoria(String categoria){
		SelectByVisibleText(By.name("category_id"), categoria);
	}
	
	public void AcessarReportIssue(){
		Click(By.xpath("//a[@href='/bug_report_page.php']"));
	}
	
	public void EscreverDescricao(String descricao){
		SendKeys(By.name("description"), descricao);
	}
	
	public void EscreverSumario(String sumario){
		SendKeys(By.name("summary"), sumario);
	}
	
	public String ObterMensagemDeErro(){
		return GetText(By.xpath("//td[@class='form-title']"));
	}
	
	public String ObterTituloDaTabela(){
		WebDriverWait wait = new WebDriverWait(DriverFactory.getDriver(), 30);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//table[@id='buglist']//td[@class='form-title']")));
		return GetText(By.xpath("//table[@id='buglist']//td[@class='form-title']"));
	}
	
	public void FazerLogout(){
		Click(By.xpath("//a[@href='/logout_page.php']"));
	}
	
	public void CriaReport(String category, String summary, String description){
		AcessarReportIssue();
		SelecionarProjeto("All Projects");
		ClicarSelectProject();
		SelecionarCategoria(category);
		EscreverSumario(summary);
		EscreverDescricao(description);
		ClicarSubmitReport();
	}
}
