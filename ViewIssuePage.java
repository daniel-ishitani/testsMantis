package pages;

import org.openqa.selenium.By;

import core.BasePage;

public class ViewIssuePage extends BasePage {
	public void ClicarIssue(){
		Click(By.xpath("//tr[@bgcolor]//td[4]//a[@href]"));
	}
	
	public void ClicarEdit(){
		Click(By.xpath("//input[@value='Edit']"));
	}
	
	public void ClicarSubmitInformation(){
		Click(By.xpath("//input[@value='Update Information']"));
	}
	
	public void SelecionarPrioridade(String prioridade){
		SelectByVisibleText(By.name("priority"), prioridade);
	}
	
	public String ObterPrioridade(){
		return GetText(By.xpath("//html/body/table[@class='width100']//tr[7]//td[2]"));
	}
	
	public void AcessarViewIssues(){
		Click(By.xpath("//a[@href='/view_all_bug_page.php']"));
	}
	
	public void DeletarIssue(){
		AcessarViewIssues();
		ClicarIssue();
		Click(By.xpath("//input[@value='Delete']"));
		Click(By.xpath("//input[@value='Delete Issues']"));
	}
}
