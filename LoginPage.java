package pages;

import org.openqa.selenium.By;

import core.BasePage;

public class LoginPage extends BasePage{
	
	public void PreencheUsername(String username){
		SendKeys(By.name("username"), username);
	}
	
	public void PreenchePassword(String password){
		SendKeys(By.name("password"), password);
	}
	
	public void ClicarLogin(){
		Click(By.xpath("//input[@value='Login']"));
	}
	
	public String ObtemMensagemDeErro(){
		return GetText(By.xpath("//font[@color='red']"));
	}
	
	public String ObterUsuarioLogado(){
		return GetText(By.xpath("//span[@class='italic'][contains(text(),'daniel.ishitani')]"));
	}
	
	public void Login(String username, String password){
		PreencheUsername(username);
		PreenchePassword(password);
		ClicarLogin();
	}
}
