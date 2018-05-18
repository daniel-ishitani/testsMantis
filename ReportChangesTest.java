package Tests;

import org.easetech.easytest.annotation.DataLoader;
import org.easetech.easytest.loader.LoaderType;
import org.easetech.easytest.runner.DataDrivenTestRunner;
import org.easetech.easytest.annotation.Param;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import core.BaseTest;
import pages.LoginPage;
import pages.ReportIssuePage;
import pages.ViewIssuePage;

@RunWith(DataDrivenTestRunner.class)
public class ReportChangesTest extends BaseTest{
	static ViewIssuePage viewIssuePage = new ViewIssuePage();
	static ReportIssuePage reportIssuePage = new ReportIssuePage();
	static LoginPage loginPage = new LoginPage();
	
	@BeforeClass
	public static void FazLogin(){
		String username = "daniel.ishitani";
		String password = "asd";
		loginPage.Login(username, password);
		reportIssuePage.CriaReport("[All Projects] Teste", "asd", "asd");
	}
	
	@AfterClass
	public static void DeletaIssue(){
		viewIssuePage.DeletarIssue();
	}

	@Test	@DataLoader(filePaths = "ReportChangesTestData.csv", loaderType = LoaderType.CSV)
	public void EditarPrioridadeTest(@Param(name = "priority") String priority ){
		viewIssuePage.AcessarViewIssues();
		viewIssuePage.ClicarIssue();
		viewIssuePage.ClicarEdit();
		viewIssuePage.SelecionarPrioridade(priority);
		viewIssuePage.ClicarSubmitInformation();
		Assert.assertEquals(priority, viewIssuePage.ObterPrioridade());
	}
		
}
