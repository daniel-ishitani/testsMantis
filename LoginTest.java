package Tests;

import org.easetech.easytest.annotation.DataLoader;
import org.easetech.easytest.annotation.Param;
import org.easetech.easytest.loader.LoaderType;
import org.easetech.easytest.runner.DataDrivenTestRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import core.BaseTest;
import pages.LoginPage;

@RunWith(DataDrivenTestRunner.class)
public class LoginTest extends BaseTest{
	
	LoginPage loginPage = new LoginPage();
	
	@Test	@DataLoader(filePaths = "LoginTestData.csv", loaderType = LoaderType.CSV)
	public void LoginDeveFalharTest(@Param(name = "username") String username,
            						@Param(name = "password") String password){
		String error = "Your account may be disabled or blocked or the username/password you entered is incorrect.";
		loginPage.Login(username, password);
		Assert.assertEquals(error, loginPage.ObtemMensagemDeErro());
	}
	
	@Test
	public void LoginComSucessoTest(){
		String username = "daniel.ishitani";
		String password = "asd";
		loginPage.Login(username, password);
		Assert.assertEquals(username, loginPage.ObterUsuarioLogado());
	}
}
