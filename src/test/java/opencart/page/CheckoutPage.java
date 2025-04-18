package opencart.page;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CheckoutPage {

	private WebDriver driver;
	static WebDriverWait wait;

	@FindBy(xpath="//*[@id=\"collapse-payment-address\"]/div/form/div[3]/label/input")
	WebElement newaddress;

	@FindBy(id="input-payment-firstname")
	WebElement firstname;

	@FindBy(id="input-payment-lastname")
	WebElement lastname;

	@FindBy(id="input-payment-address-1")
	WebElement address1;

	@FindBy(id="input-payment-city")
	WebElement city;

	@FindBy(id="input-payment-postcode")
	WebElement postcode;

	@FindBy(id="input-payment-country")
	WebElement country;

	@FindBy(id="input-payment-zone")
	WebElement state;

	@FindBy(id="button-payment-address")
	WebElement step2continue;

	@FindBy(id="button-shipping-address")
	WebElement step3continue;
	
	@FindBy(id="button-shipping-method")
	WebElement step4continue;
	
	@FindBy(id="button-payment-method")
	WebElement step5continue;
	
	@FindBy(id="button-confirm")
	WebElement confirm;

	@FindBy(xpath="//*[@id=\"collapse-payment-address\"]/div/form/div[1]/label/input")
	WebElement existingAddress;
	
	@FindBy(xpath="//*[@id=\"collapse-payment-address\"]/div/form/div[3]/label/input")
	WebElement selectOptNewAddress;
	
	@FindBy(xpath="//*[@id=\"collapse-payment-method\"]/div/div[3]/div/input[1]")
	WebElement termscheckbox;
	
	
	
	/*************Error Message*******************/
	
	@FindBy(xpath="//*[@id=\"payment-new\"]/div[1]/div/div")
	WebElement firsterror;

	@FindBy(xpath="//*[@id=\"payment-new\"]/div[2]/div/div")
	WebElement lasterror;

	@FindBy(xpath="//*[@id=\"payment-new\"]/div[4]/div/div")
	WebElement addr1error;
	
	@FindBy(xpath="//*[@id=\"payment-new\"]/div[6]/div/div")
	WebElement cityerror;
	
	@FindBy(xpath="//*[@id=\"payment-new\"]/div[7]/div/div")
	WebElement pincodeerror;
	
	/***********************************************/


	public CheckoutPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
		this.driver = driver;
		wait = new WebDriverWait(driver, Duration.ofSeconds(5));
	}
	
	public void enterBillingDetails(String first,String last,String address1,String city,String postcode, String country,String state) {
		try {
			int countryidx, stateidx;
			try{
				 countryidx = Integer.parseInt(country);
				 stateidx = Integer.parseInt(state);
			}catch(NumberFormatException e){
				countryidx = 0;
				stateidx = 0;
			}
			this.firstname.sendKeys(first);
			this.lastname.sendKeys(last);
			this.address1.sendKeys(address1);
			this.city.sendKeys(city);
			this.postcode.sendKeys(postcode);
			
			Thread.sleep(2000);
			Select countrysec = new Select(this.country);
			countrysec.selectByIndex(countryidx);
			
			Thread.sleep(2000);
			Select stateselect = new Select(this.state);
			stateselect.selectByIndex(stateidx);
			
			step2continue.click();			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public void selectNewAddress() {
		newaddress.click();
	}
	public void clickStep2Continue() {
		step2continue.click();
	}
	public void clickStep3Continue() {
		step3continue.click();
	}
	public void clickStep4Continue() {
		step4continue.click();
	}
	public void clickStep5Continue() {
		step5continue.click();
	}
	public void confirmOrder() {
		confirm.click();
	}
	
	public void enterDeliveryDetails() {
		driver.findElement(By.xpath("//*[@id=\"button-shipping-address\"]")).click();
		step3continue.click();	
		
	}
	public void enterDeliveryMethod() {
		step4continue.click();		
	}
	public void clickTermsAndCondition() throws InterruptedException{		
		driver.findElement(By.xpath("//div[@id='collapse-payment-method']/div/div[2]/div/input[1]")).click();
		clickStep5Continue();
		Thread.sleep(2000);
		confirmOrder();
		Thread.sleep(3000);
	}
	
	public void enterPaymentMethod() {
		step5continue.click();		
	}
	
	public boolean skipExistingAddress() {
		return existingAddress.isSelected();
	}
	
	public void selectOptNewAddress() {
		selectOptNewAddress.click();
	}
	
	/*********************************************************/

	public String getFirstFieldError(){
		return firsterror.getText();
	}
	public String getLastFieldError(){
		return lasterror.getText();
	}
	public String getAddr1FieldError(){
		return addr1error.getText();
	}
	public String getCityFieldError(){
		return cityerror.getText();
	}
	public String getPincodeFieldError(){
		return pincodeerror.getText();
	}
	/*********************************************************/
	
	
	
}
