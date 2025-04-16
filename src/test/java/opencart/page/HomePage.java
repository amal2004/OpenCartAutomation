package opencart.page;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {
	
    WebDriver driver;
	
	@FindBy(xpath="//div[@id='top-links']/ul/li[2]/a")
	WebElement myaccount;
	
	@FindBy(xpath="//div[@id='top-links']/ul/li[2]/ul/li[2]/a")
	WebElement login;
	
	@FindBy(xpath = "//nav[@id='menu']/div[2]/ul/li[1]/a")
	WebElement desktops;
	
	@FindBy(xpath="//nav[@id='menu']/div[2]/ul/li[1]/div/a")
	WebElement showAllDesktops;
	
	@FindBy(xpath="//div[@id='content']/div[4]/div[3]/div/div[2]/div[1]/h4/a")
	WebElement selectProductHP_LP3065;
	
	@FindBy(xpath="//button[@id='button-cart']")
	WebElement addProductToCart;
	
	
	@FindBy(xpath="//div[@id='top-links']/ul/li[4]")
	WebElement viewCart;
	
	@FindBy(xpath="//div[@id='content']/div[3]/div[2]/a")
	WebElement checkOut;
	
	
	public HomePage(WebDriver driver) {
		PageFactory.initElements(driver, this);
		this.driver=driver;
	}
	
	public void clickMyAccount() {
		myaccount.click();
	}
	
	public void clickLogin() {
		login.click();
	}
	
	public void clickDesktops() {
		desktops.click();
	}
	
	public void showAllDesktops() {
		showAllDesktops.click();
	}
	
	public void selectProduct(String productName){
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		driver.findElement(By.partialLinkText(productName)).click();
	}
	
	public void enterQty(String qty) {
		driver.findElement(By.id("input-quantity")).clear();
		driver.findElement(By.id("input-quantity")).sendKeys(qty);
	}
	
	public void addProductToCart() throws InterruptedException {
		addProductToCart.click();
		Thread.sleep(3000);
	}
	
	public String getCartAddMessageAlert() throws InterruptedException {
		
		String message = driver.findElement(By.xpath("//*[@id='product-product']/div[1]")).getText();
		message = message.substring(0, message.length()-2);
		System.out.println(message);
		return message;
	}
	
	public void viewCart() {
		viewCart.click();
	}
	
	public void proceedToCheckout() {
		checkOut.click();
	}
	
	
	
}
