import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.Arrays;

public class SampleTest_with_PageObjectSample {

	String base_url = "http://localhost:8081/";
	StringBuffer verificationErrors = new StringBuffer();
	WebDriver driver = null;
	PageObjectSample page;


	@BeforeClass
	public void beforeClass() throws Exception {
		System.setProperty("webdriver.chrome.driver", "D:\\Idea\\IntelliJ IDEA Community Edition 2017.3\\chromedriver_win32\\chromedriver.exe");
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		capabilities.setCapability("chrome.switches", Arrays.asList("--homepage=about:blank"));
		driver = new ChromeDriver(capabilities);
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			Assert.fail(verificationErrorString);
		}
	}

	@BeforeMethod
	public void beforeTest() {
		driver.get(base_url);
		System.out.println(driver.getTitle());
		page = PageFactory.initElements(driver, PageObjectSample.class);
		Assert.assertTrue(page.getFormForLogin(), "Form  is not founded");
		page.setName("LeskovskayaAnastasia");
		Assert.assertEquals(page.getName(), "LeskovskayaAnastasia", "Unable to fill 'User' field");
		page.setPassword("130588jk");
		Assert.assertEquals(page.getPassword(), "130588jk", "Unable to fill 'Password' field");
		page.submitForm();
	}

	@Test
	public void sampleTest() {
		// test1

		// клик по ссылке «Manage Jenkins»
		page.goManageJenkinsLink();

		// отображение элементов на странице
		//verificationErrors.append(page.getErrorOnTextAbsence("Manage Users"));
		Assert.assertEquals(page.getManageUserTextDT(), "Manage Users", "Manage Users dt not founded");
		Assert.assertEquals(page.getManageUserTextDD(), "Create/delete/modify users that can log in to this Jenkins", "Manage Users dd not founded");

		// test2
		page.goManageUsersLink();
		Assert.assertTrue(page.isCreateUserLinkDisplayed(), "Link \"Create User\" were not found");

		//test3
		page.goCreateUserLink();
		Assert.assertTrue(page.isFormReal(), "No suitable forms found!");

		//test4
		page.setNameUserCreateForm("someuser");
		Assert.assertEquals(page.getNameUserCreateForm(), "someuser", "Unable to fill 'Username' field");

		page.setPasswordUserCreateForm("somepassword");
		Assert.assertEquals(page.getPasswordUserCreateForm(), "somepassword", "Unable to fill 'Password' field");

		page.setConfirmPasswordUserCreateForm("somepassword");
		Assert.assertEquals(page.getConfirmPasswordUserCreateForm(), "somepassword", "Unable to fill 'Confirm password' field");

		page.setFullNameUserCreateForm("Some Full Name");
		Assert.assertEquals(page.getFullNameUserCreateForm(), "Some Full Name", "Unable to fill 'Full name' field");

		page.setEmailAddressUserCreateForm("some@addr.dom");
		Assert.assertEquals(page.getEmailAddressUserCreateForm(), "some@addr.dom", "Unable to fill 'E-mail address' field");

		page.createUserButtonClick();
		//verificationErrors.append(page.getErrorOnTextAbsence("Имя"));
		Assert.assertTrue(page.tableContainsText("someuser"));

		//test5
		Assert.assertTrue(page.isLink1Real());
		page.deleteUserClick();
		Assert.assertTrue(page.pageTextContains("Are you sure about deleting the user from Jenkins?"), "Message 'Are you sure about deleting the user from Jenkins?' either is absent or is not in a proper place");

		//test6
		page.yesButtonClick();
		Assert.assertFalse(page.tableContainsText("someuser"));
		Assert.assertFalse(page.isLink1Real());

		//test7
		Assert.assertFalse(page.isLink2Real());
	}
}
