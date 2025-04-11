package opencart.launch;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class LoadDriver {
	
	static WebDriver driver = null;
	
	
	public static WebDriver loadDriver(String browserName) {
		
		if(browserName.equals("chrome")) {
			driver = new ChromeDriver();
		}
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		return driver;
		
	}
	
	
	public static WebDriver getDriver() {
		return driver;
	}
	
	public static void main(String args[]){
		loadDriver("chrome");
	}

}
