package week4.day1.assignment;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class SnapDeal {

	public static void main(String[] args) throws InterruptedException, IOException {
		WebDriverManager.chromedriver().setup();
		ChromeDriver driver = new ChromeDriver();
		driver.get("https://www.snapdeal.com/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		driver.findElement(By.id("inputValEnter")).sendKeys("Training Shoes");
		driver.findElement(By.className("searchTextSpan")).click();
		Thread.sleep(390000);
		// finding no of shoes available
		List<WebElement> listOfShoes = driver.findElements(By.xpath("//picture[@class='picture-elem']"));
		int noOfShoes = listOfShoes.size();
		System.out.println("Number of Shoes available is " + noOfShoes);
		// Select SortBy dropdown "Price Low To High"
		driver.findElement(By.className("sort-selected")).click();
		WebElement textPriceLoetoHigh = driver.findElement(By.xpath("//ul/li[contains(.,'Price Low To High')]"));
		if (textPriceLoetoHigh.isDisplayed()) {
			textPriceLoetoHigh.click();
		}

		// items displayed and sorted correctly
		List<Integer> rateList = new ArrayList<Integer>();
		List<Integer> rateListSorted = new ArrayList<Integer>();
		Thread.sleep(20000);
		List<WebElement> listofShoesrate = driver.findElements(By.xpath("//span[@class='lfloat product-price']"));
		int noOfShoesrate = listofShoesrate.size();
		System.out.println("Total Number of item displayed " + noOfShoesrate);
		if (noOfShoes == noOfShoesrate) {
			System.out.println("Items Are Matching before and after sorting and displayed ");
			for (WebElement eachItem : listofShoesrate) {
				String strRate = eachItem.getText();
				String strNew = strRate.replace("Rs.", "").replaceAll(" ", "").replaceAll(",", "");
				System.out.println(strNew + " is Displaying");
				int convertRate = Integer.valueOf(strNew);
				// adding the rate which displays in the page
				rateList.add(convertRate);
				strNew = "";
			}
		}
		rateListSorted.addAll(rateList);
		Collections.sort(rateListSorted); // sorting the ascending order
		boolean rateCheck = false;
		for (int i = 0; i < rateList.size(); i++) {
			if (rateListSorted.get(i).equals(rateList.get(i))) { // compare both are same
				rateCheck = true;
			} else {
				rateCheck = false;
				break;
			}
		}
		if (rateCheck) {
			System.out.println("Rates are Sorted Correctly");
		} else {
			System.out.println("Rates are not Sorted Properly");
		}

		// enter the price range
		driver.findElement(By.name("fromVal")).clear();
		driver.findElement(By.name("fromVal")).sendKeys("900");
		driver.findElement(By.name("toVal")).clear();
		driver.findElement(By.name("toVal")).sendKeys("1500");
		driver.findElement(By.xpath("//div[@class='price-input']/following-sibling::div[contains(text(),'GO')]")).click();
		System.out.println("Price Range Entered");
		// select the blue color
		WebElement checkboxBlue = driver.findElement(By.xpath("//label[@for='Color_s-Blue']"));
		JavascriptExecutor execute= (JavascriptExecutor)driver;
		execute.executeScript("arguments[0].click()", checkboxBlue);
		WebElement checkboxBlue2 = driver.findElement(By.id("Color_s-Blue"));
		boolean checkboxStatus = checkboxBlue2.isSelected();
		if (checkboxStatus) {
			System.out.println("Blue color checkbox is selected");
		} else {
			System.out.println("Blue color checkbox is not selected");
		}

		// select the first result
		driver.findElement(By.xpath("(//picture[@class='picture-elem'])[1]")).click();
		System.out.println("First item is selected");
		Thread.sleep(30000);
		Set<String> allWindows = driver.getWindowHandles();
		List<String> listAllWindows = new ArrayList<String>(allWindows);
		driver.switchTo().window(listAllWindows.get(1));
		// print the price and percentage
		String strPrice = driver.findElement(By.xpath("//span[@itemprop='price']")).getText();
		String strdiscount = driver.findElement(By.xpath("//span[contains(@class,'pdpDiscount')]")).getText();
		System.out.println("The shoe price is Rs " + strPrice + " and the percentage is " + strdiscount);
		// Take the screen shot
		WebElement pictureShoe = driver.findElement(By.id("bx-slider-left-image-panel"));
		// File source=driver.getScreenshotAs(OutputType.FILE);
		File source = pictureShoe.getScreenshotAs(OutputType.FILE);
		File dest = new File("./snaps/IMG001");
		FileUtils.copyFile(source, dest);
		driver.quit();
	}

}
