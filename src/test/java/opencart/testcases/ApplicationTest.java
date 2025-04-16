package opencart.testcases;

import java.io.IOException;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import opencart.launch.LoadDriver;
import opencart.page.CheckoutPage;
import opencart.page.HomePage;
import opencart.page.LoginPage;
import opencart.utilities.ExcelReader;

public class ApplicationTest {

	static WebDriver driver;
	static LoginPage loginPage;
	static HomePage homePage;
	static CheckoutPage checkout;
	static ExtentHtmlReporter exreport = new ExtentHtmlReporter("./report/Report.html");
	static ExtentReports extent = new ExtentReports();
	static ExtentTest logger;
	private ExcelReader excelReader;
	private ExcelReader excelQtyReader;
	int qtycount=1;
	int addresscount=1;

	@BeforeClass
	public static void launchBrowser() {
		driver = LoadDriver.loadDriver("chrome");
		driver.get("https://naveenautomationlabs.com/opencart");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(6));
		homePage = new HomePage(driver);
		loginPage = new LoginPage(driver);
		checkout = new CheckoutPage(driver);
		extent.attachReporter(exreport);
	}

	@BeforeMethod
	public void setUp(Method method) {
		logger = extent.createTest(method.getName());
	}

	@Test(priority = 0)
	public void launchBrowserTest() {
		if (driver == null)
			logger.log(Status.FAIL, "Browser Launch Failed");
		else
			logger.log(Status.PASS, "Browser Launch Success");
		extent.flush();
	}

	@Test(priority = 1)
	public void login() {

		homePage.clickMyAccount();
		homePage.clickLogin();

		loginPage.setUsername("amal2000@gmail.com");
		loginPage.setPassword("12345");
		loginPage.clickLogin();

		String pageTitle = driver.getTitle();
        System.out.println(pageTitle);
        Assert.assertEquals(pageTitle, "My Account");
        
		extent.flush();
	}
	
	@Test(priority = 2)
	public void browseProduct() throws InterruptedException {
		homePage.clickDesktops();
		logger.log(Status.INFO, "Selects Desktops");
		
		homePage.showAllDesktops();
		logger.log(Status.INFO, "Selects All Desktops");
		
		
		homePage.selectProduct("HP LP3065");
		logger.log(Status.INFO, "Product HP LP3065 Selected");
		
		extent.flush();
			
	}
	
	@Test(priority = 3, dataProvider = "getQty") 
	public void addProductToCart(String qty) throws InterruptedException, IOException {


		homePage.enterQty(qty);
		homePage.addProductToCart();		
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(8));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='product-product']/div[1]")));
		
		String actualMessage = homePage.getCartAddMessageAlert();
		String expectedMessage = excelQtyReader.getCellData(qtycount, 2);
		if(actualMessage.equals(expectedMessage))
			excelQtyReader.setCellData(qtycount, 1, "Passed");
		else
			excelQtyReader.setCellData(qtycount, 1, "Failed");
		
		qtycount++;
		Assert.assertEquals(actualMessage,expectedMessage);	
		logger.log(Status.PASS, "Qty value : "+qty+" Accepted");
		extent.flush();
		
	}
	
	@Test(priority = 4)
	public void proceedToCheckout() throws InterruptedException {
		homePage.viewCart();
		logger.log(Status.INFO, "Cart Displayed");
		
		homePage.proceedToCheckout();
		Thread.sleep(3000);
		logger.log(Status.INFO, "Proceed to checkout");
	}
	
	@Test(priority=5, dataProvider="getAddressData")
	public void enterAddress(String first,String last,String addr1,String city,String pincode,String country,String state) throws InterruptedException{		
		//checkout.selectNewAddress();
		Thread.sleep(2000);
		logger.log(Status.INFO, "New Address selected");
		
		if(checkout.skipExistingAddress())
			checkout.selectOptNewAddress();
		
		checkout.enterBillingDetails(first, last, addr1, city, pincode, country, state);
		
		logger.log(Status.INFO, "Address Data : " + first + " , " + last + " , " + addr1 + " , " + city + " , "+ pincode + " , " + country + " , " + state);
		if (first.equals("") && last.equals("") && addr1.equals("") && city.equals("")) {
			
			Assert.assertEquals(checkout.getFirstFieldError(), "First Name must be between 1 and 32 characters!","Error Message Not Displayed!!");
			logger.log(Status.PASS, "Error message of first name field is verified");
			
			Assert.assertEquals(checkout.getLastFieldError(), "Last Name must be between 1 and 32 characters!","Error Message Not Displayed!!");
			logger.log(Status.PASS, "Error message of first name field is verified");
			
			Assert.assertEquals(checkout.getAddr1FieldError(), "Address 1 must be between 3 and 128 characters!","Error Message Not Displayed!!");
			logger.log(Status.PASS, "Error message of first name field is verified");
			
			Assert.assertEquals(checkout.getCityFieldError(), "City must be between 2 and 128 characters!","Error Message Not Displayed!!");
			logger.log(Status.PASS, "Error message of first name field is verified");
			
			excelReader.setCellData(addresscount, excelReader.getColCount(), "Passed"); // Making Status Passed if add error message is verified.
		} else {
			excelReader.setCellData(addresscount, excelReader.getColCount(), "Failed");
		}
		Thread.sleep(3000);
		addresscount++;
	}
	
	@Test(priority = 6)
	public void enterDeliveryDetails() throws InterruptedException {
		checkout.enterDeliveryDetails();
		logger.log(Status.INFO, "Delivery detailes entered");
		Thread.sleep(3000);
	}
	
	@Test(priority = 7)
	public void enterDeliveryMethod() throws InterruptedException {
		checkout.enterDeliveryMethod();
		logger.log(Status.INFO, "Delivery method details entered");
		Thread.sleep(3000);
	}
	
	@Test(priority = 8)
	public void acceptTerms() throws InterruptedException {
		checkout.clickTermsAndCondition();
		logger.log(Status.INFO, "Terms and Condition clicked");
	}

	
	
	@DataProvider
	public Object[][] getAddressData(){
		String projectpath = System.getProperty("user.dir");
		String filepath = projectpath + "/testdata/";
		String filename = "TestData.xlsx";
		String sheetname = "Address";		
		excelReader = new ExcelReader(filepath+filename, sheetname,1);
		Object data[][]  = excelReader.getAllData();
		return data;
	}

	@DataProvider
	public Object[][] getQty(){
		String projectpath = System.getProperty("user.dir");
		String filepath = projectpath + "/testdata/";
		String filename = "TestData.xlsx";
		String sheetname = "Qty";		
		excelQtyReader = new ExcelReader(filepath+filename, sheetname,2);
		Object data[][]  = excelQtyReader.getAllData();
		return data;
	}

	@AfterClass
	public void closerBrowser() {
		driver.quit();
	}
}

