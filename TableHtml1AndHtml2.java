package week4.day1.assignment;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TableHtml1AndHtml2 {
	public static void main(String args[]) {
		WebDriverManager.chromedriver().setup();
		ChromeDriver driver = new ChromeDriver();
		driver.get("https://html.com/tags/table");
		driver.manage().window().maximize();
		System.out.println("Page is Launched");
		// find the no of rows
		List<WebElement> tableRows = driver.findElements(By.xpath("//div[@class='render']/table/tbody/tr"));
		System.out.println("Total No of Rows" + tableRows.size());
		// find the no of columns , taking first row value alone
		List<WebElement> tableColumns = tableRows.get(0).findElements(By.tagName("td"));
		System.out.println("Total No of Columns" + tableColumns.size());
		List<WebElement> tableHeaders = driver.findElements(By.xpath("//div[@class='render']/table/thead/tr/th"));
		
		//Below is the code for HTML2 assignment
		// find the index of "Library"- need column index
		int indexLibrary = 0;
		for (int i = 0; i < tableHeaders.size(); i++) {
			if ((tableHeaders.get(i).getText()).equals("Library")) {
				indexLibrary = i;
				System.out.println("Library Index " + indexLibrary);
				break;
			}
		}
		List<String> listValues= new ArrayList<String>();
		for (int j = 0; j < tableRows.size(); j++) {
			WebElement eachRow = tableRows.get(j);
			List<WebElement> columnValues = eachRow.findElements(By.tagName("td"));
			String strCellValue = columnValues.get(indexLibrary).getText(); // using the library index ,due to get the library related values
			if (strCellValue.equals("Absolute Usage")) {//checking each cell value under 0 index with the text 'Absolute Usage'
				for (int k = 1; k < columnValues.size(); k++) {
					String strCellValueEach = columnValues.get(k).getText();//getting corresponding values matching  in the row of 'Absolute Usage'
					System.out.println(strCellValueEach);
					listValues.add(strCellValueEach);//adding into list all values
				}
			}
		}
		System.out.println("Absolute Usage Values are " + listValues);
		driver.close();
	}

}
