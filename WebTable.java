package week4.day1.assignment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class WebTable {

	public static void main(String[] args) {
		WebDriverManager.chromedriver().setup();
		ChromeDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("http://www.leafground.com/pages/table.html");
		// Get the Number of rows and columns
		WebElement tableElement = driver.findElement(By.xpath("//tbody"));
		List<WebElement> tableRows = tableElement.findElements(By.tagName("tr"));
		Map<Integer, Integer> MapVitalValue = new LinkedHashMap<Integer, Integer>();
		System.out.println("Number of Rows " + tableRows.size());
		int column = 0;
		int smallNumberIndex = 0;
		for (int i = 1; i < tableRows.size(); i++)// here starting from 1 due to 0 contains th tag not td
		{
			WebElement eachElementRow = tableRows.get(i);
			List<WebElement> tableColumns = eachElementRow.findElements(By.tagName("td"));
			column = tableColumns.size();
			for (int j = 0; j < tableColumns.size(); j++) {
				WebElement eachColumnElement = tableColumns.get(j);
				// line 38 to 42used for storing all progress value in to map
				String eachColumnElementValue = eachColumnElement.getText();
				String progressTextWithPercentage = tableColumns.get(1).getText();
				String strProgressText = progressTextWithPercentage.replaceAll("%", "");
				int progressText = Integer.valueOf(strProgressText);
				MapVitalValue.put(i, progressText);
				if (eachColumnElementValue.equalsIgnoreCase("Learn to interact with Elements")) {
					// We need Progress Column we using 1 as index
					String seleniumLearningDetails = tableColumns.get(1).getText();
					// printing the values corresponding to "Learn to interact with Elements"
					System.out.println("Row No " + i + " " + eachColumnElementValue + " and Progress is "
							+ seleniumLearningDetails);
				}
			}
		}
		// printing the number of columns
		System.out.println("Number of Columns " + column);
		System.out.println(MapVitalValue);
		// finding the least number using collection--declare arraylist
		List<Integer> listKeyvalues = new ArrayList<Integer>();
		// finding the least number using collection--getting all the values
		Collection<Integer> keyvalues = MapVitalValue.values();
		// convert collection in to list array
		listKeyvalues.addAll(keyvalues);
		// sorting the array , so that first one will be the least number
		Collections.sort(listKeyvalues);
		// least progress number stored in the pairValue
		int indexPairValue = listKeyvalues.get(0);
		Set<Entry<Integer, Integer>> entrySet = MapVitalValue.entrySet();
		for (Entry<Integer, Integer> eachEntry : entrySet) {
			Integer valueOfPair = eachEntry.getValue();
			if (valueOfPair == indexPairValue) {
				smallNumberIndex = eachEntry.getKey();
				break;
			}
		}
		String strsmallNumberIndex = Integer.toString(smallNumberIndex);
		String tempXpathCheckbox = "(//table//tr//td//input[@name='vital'])[temp]";
		String dynamicXpathCheckbox = tempXpathCheckbox.replace("temp", strsmallNumberIndex);
		driver.findElement(By.xpath(dynamicXpathCheckbox)).click();
		System.out.println("Vital checkbox selected corresponding to least Progress Value ");
		driver.close();
	}

}
