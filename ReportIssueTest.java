package Tests;

import org.easetech.easytest.annotation.DataLoader;
import org.easetech.easytest.annotation.Param;
import org.easetech.easytest.loader.LoaderType;
import org.easetech.easytest.runner.DataDrivenTestRunner;
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
public class ReportIssueTest extends BaseTest {
	
	ReportIssuePage reportIssuePage = new ReportIssuePage();
	static LoginPage loginPage = new LoginPage();
	static ViewIssuePage viewIssuePage = new ViewIssuePage();
	
	@BeforeClass
	public static void FazLogin(){
		String username = "daniel.ishitani";
		String password = "asd";
		loginPage.Login(username, password);
	}
	
	@AfterClass
	public static void DeletaIssue(){
		viewIssuePage.DeletarIssue();
	}
	

	@Test	@DataLoader(filePaths = "ReportIssueTestData.csv", loaderType = LoaderType.CSV)
	public void ReportIssueDeveFalhar(@Param(name = "category") String category,
									  @Param(name = "summary") String summary,
									  @Param(name = "description") String description){
		String error = "APPLICATION ERROR #11";
		reportIssuePage.CriaReport(category, summary, description);
		Assert.assertEquals(error, reportIssuePage.ObterMensagemDeErro());
	}
	
	@Test
	public void reportIssue(){
		reportIssuePage.CriaReport("[All Projects] Teste", "asd", "asd");
		Assert.assertTrue(reportIssuePage.ObterTituloDaTabela().contains("Viewing Issues"));	
	}

}
