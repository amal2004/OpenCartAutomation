package opencart.utilities;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class Capture {
	
	public static String captureScreenShot(WebDriver driver) {
		TakesScreenshot scrnShot = (TakesScreenshot)driver;
		
		File src = scrnShot.getScreenshotAs(OutputType.FILE);
		String path = System.getProperty("user.dir") + "/screenshots/"+ System.currentTimeMillis()+".png";
		File destination = new File(path);
		
		try {
			FileUtils.copyFile(src, destination);
			return path;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
	}

}
