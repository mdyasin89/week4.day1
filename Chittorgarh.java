package week4.day1.assignment;

import java.time.Duration;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Chittorgarh {

	public static void main(String[] args) {
		WebDriverManager.chromedriver().setup();
		ChromeDriver driver = new ChromeDriver();
		driver.get("https://www.Chittorgarh.com/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		// click the Stock Market button
		driver.findElement(By.id("navbtn_stockmarket")).click();
		// Click the NSE Bulk Deals button
		driver.findElement(By.linkText("NSE Bulk Deals")).click();
		// find the table
		WebElement tableElement = driver.findElement(By.xpath("//div[@class='table-responsive']/table"));
		//find the no of header values
		List<WebElement> tableHeaders = tableElement.findElements(By.tagName("th"));
		int securityNameColumnIndex = 0;
		for (int i = 0; i < tableHeaders.size(); i++) {
			if ((tableHeaders.get(i).getText()).equalsIgnoreCase("Security Name")) {
				//store the  header index of 'Security Name'
				securityNameColumnIndex = i;
				break;
			}
		}
		Set<String> setObj= new LinkedHashSet<String>();
		List<WebElement> tableRowsElements = tableElement.findElements(By.tagName("tr"));
		//in this table row '0' of tr having header value('th') and used for only style ,not having any value so start with 1
		for(int j=1; j<tableRowsElements.size();j++)
		{
			WebElement eachRow = tableRowsElements.get(j);
			List<WebElement> tableColumnsElements = eachRow.findElements(By.tagName("td"));
			System.out.println(tableColumnsElements.get(securityNameColumnIndex).getText());
		    //adding values to the set for avoid duplicates
			setObj.add(tableColumnsElements.get(securityNameColumnIndex).getText());
		}
		System.out.println("Security Names are "+setObj);
		driver.quit();
	}

}
