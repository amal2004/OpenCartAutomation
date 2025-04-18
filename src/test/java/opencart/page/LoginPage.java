package opencart.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
	
	private WebDriver driver;
	
	@FindBy(id="input-email")
	WebElement username;
	
	@FindBy(id="input-password")
	WebElement password;
	
	@FindBy(css=" #content > div > div:nth-child(2) > div > form>input")
	WebElement loginbtn;

	public LoginPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
		this.driver = driver;
	}

	public void setUsername(String userName) {
		username.sendKeys(userName);
	}

	public void setPassword(String pword) {
		password.sendKeys(pword);
	}

	public void clickLogin() {
		loginbtn.click();
	}
	
}
