package com.automation.utils;


import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.stream.Collectors;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;


public class CommonMethods {

	public static void sendText(By locator, String textToSend, WebDriver driver) {
		WebElement element = driver.findElement(locator);
		if (element.isDisplayed()) {
			element.clear();
			element.sendKeys(textToSend);
		} else {

			System.out.println("Element is not displayed");
		}

	}

	public String randomSendText(By locator, WebDriver driver) {

		WebElement element = driver.findElement(locator);
		String alphabet = "abcdefghijklmnopqrstuvwxyz1234567890-_";
		String randomDRN = RandomStringUtils.random(10, alphabet);
		element.sendKeys(randomDRN);

		return randomDRN.toUpperCase();

	}

	public static void scrollToElement(By locator, WebDriver driver) {

	    final int maxAttempts = 3;

	    WebElement element = driver.findElement(locator);

	    JavascriptExecutor executor = (JavascriptExecutor) driver;

	    for (int i = 0; i < maxAttempts; i++) {

	        executor.executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'nearest', behavior: 'smooth'});", element);

	        try {

	            Thread.sleep(200); // Wait for the scroll animation to complete

	            if (element.isDisplayed() && element.isEnabled()) {

	                break; // Break if the element is fully visible and interactable

	            }

	        } catch (InterruptedException e) {

	            Thread.currentThread().interrupt();

	        } catch (Exception e) {

	            System.out.println("Scroll attempt " + (i + 1) + " failed.");

	        }

	    }

	}

	public String elementGetText(By locator, WebDriver driver) {

		WebElement element = driver.findElement(locator);
		return element.getText();

	}

	public static void reactSelectSendText(By locator, String textToSend, WebDriver driver)
			throws InterruptedException {

		WebElement element = driver.findElement(locator);
		element.sendKeys(textToSend);
		Thread.sleep(1000);
		element.sendKeys(Keys.ENTER);
	}

	public static WebDriverWait getWait(WebDriver driver) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Constants.EXPLICIT_WAIT));
		return wait;
	}

	public static void click(By locator, WebDriver driver) throws InterruptedException {

		int maxRetries = 5;
		int retryCount = 0;

		while (retryCount < maxRetries) {

			try {


				explicitlyWaitForVisibility(By.tagName("body"), Constants.EXPLICIT_WAIT, driver);
				explicitlyWaitForVisibility(locator, Constants.EXPLICIT_WAIT, driver);
				explicitlyWaitForClickibility(locator, Constants.EXPLICIT_WAIT, driver);

				scrollToElement(locator, driver);

				WebElement element = driver.findElement(locator);
				element.click();

				break;

			} catch (StaleElementReferenceException e) {

				retryCount++;
				System.out.println("StaleElementReferenceException occurred. Retrying... Attempt: " + retryCount);
				Thread.sleep(500);

			} catch (ElementClickInterceptedException e) {

				retryCount++;
				System.out.println("ElementClickInterceptedException occurred. Retrying... Attempt: " + retryCount);
				Thread.sleep(500);

			}

			// If reached max retries, throw an exception

			if (retryCount == maxRetries) {
				throw new RuntimeException("Failed to click on element after " + maxRetries + " attempts: " + locator);

			}
		}
	}

	public static void refreshPage(WebDriver driver) {

		driver.navigate().refresh();
	}

	public static void clearText(By locator, WebDriver driver) {

		WebElement element = driver.findElement(locator);
		element.click();
		element.clear();
	}

	public static boolean fluentWaitForText(WebDriver driver, By locator, String expectedText, int maxWaitSeconds,
			int pollingIntervalSeconds) {
		Wait<WebDriver> wait = new FluentWait<>(driver).withTimeout(Duration.ofSeconds(maxWaitSeconds))
				.pollingEvery(Duration.ofSeconds(pollingIntervalSeconds)).ignoring(TimeoutException.class);
		try {
			return wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, expectedText));
		} catch (TimeoutException e) {
			System.out.println("Text '" + expectedText + "' not found within " + maxWaitSeconds + " seconds.");
			return false;
		}
	}

	public static boolean fluentWaitForEitherText(WebDriver driver, By locator, String expectedText1,
			String expectedText2, int maxWaitSeconds, int pollingIntervalSeconds) {
		Wait<WebDriver> wait = new FluentWait<>(driver).withTimeout(Duration.ofSeconds(maxWaitSeconds))
				.pollingEvery(Duration.ofSeconds(pollingIntervalSeconds)).ignoring(NoSuchElementException.class)
				.ignoring(TimeoutException.class);
		try {
			return wait.until(ignored -> {
				String elementText = driver.findElement(locator).getText();
				return elementText.contains(expectedText1) || elementText.contains(expectedText2);
			});

		} catch (TimeoutException e) {
			System.out.println("Text '" + expectedText1 + "' or '" + expectedText2 + "' not found within "
					+ maxWaitSeconds + " seconds.");
			return false;
		}
	}

	public static WebElement fluentWaitForElement(WebDriver driver, By locator, int maxWaitSeconds,
			int pollingIntervalSeconds) {
		Wait<WebDriver> wait = new FluentWait<>(driver).withTimeout(Duration.ofSeconds(maxWaitSeconds))
				.pollingEvery(Duration.ofSeconds(pollingIntervalSeconds)).ignoring(NoSuchElementException.class)
				.ignoring(TimeoutException.class);
		try {
			return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		} catch (TimeoutException e) {
			System.out.println("Element not visible after " + maxWaitSeconds + " seconds.");
			return null;
		}
	}

	public static boolean fluentWaitForAnyText(WebDriver driver, By locator, List<String> possibleTexts,
			int maxWaitSeconds, int pollingIntervalSeconds) {
		Wait<WebDriver> wait = new FluentWait<>(driver).withTimeout(Duration.ofSeconds(maxWaitSeconds))
				.pollingEvery(Duration.ofSeconds(pollingIntervalSeconds)).ignoring(TimeoutException.class);
		try {
			List<String> foundTexts = wait.until(new ExpectedCondition<List<String>>() {
				public List<String> apply(WebDriver driver) {
					String elementText = driver.findElement(locator).getText();
					return possibleTexts.stream().filter(elementText::contains).collect(Collectors.toList());
				}
			});

			if (!foundTexts.isEmpty()) {
				System.out.println("Found text(s) '" + foundTexts + "' in element located by" + locator);
				return true;
			} else {
				return false;
			}
		} catch (TimeoutException e) {
			System.out.println("None of the expected texts found within " + maxWaitSeconds + " seconds.");
			return false;
		}
	}

	public static void jsclick(By locator, WebDriver driver) {

		WebElement element = driver.findElement(locator);
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click", element);
	}

	public static void pressEnterUsingJavaScript(WebDriver driver) {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].dispatchEvent(new KeyboardEvent('keypress', { 'key': 'Enter' }));");
		handleAlert(Constants.EXPLICIT_WAIT, driver);
	}

	public static void handleAlert(int timeoutInSeconds, WebDriver driver) {
		try {
			explicitlyWaitForAlertVisibility(timeoutInSeconds, driver);
			Alert alert = driver.switchTo().alert();
			alert.accept();
		} catch (NoAlertPresentException e) {

		}
	}

	public static void explicitlyWaitForAlertVisibility(int timeoutInSeconds, WebDriver driver) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));

		wait.until(ExpectedConditions.alertIsPresent());

	}

	public static void verifyText(By actualLocator, String expected, WebDriver driver) throws InterruptedException {

		explicitlyWaitForVisibility(actualLocator, Constants.EXPLICIT_WAIT, driver);
		scrollToElement(actualLocator, driver);
		WebElement actualElement = driver.findElement(actualLocator);
		String expectedText = expected;
		String actualText = actualElement.getText();
		System.out.println("What is displayed is: " + actualText);

		try {

			Assert.assertEquals(actualText, expectedText);
			greenHighlightElement(actualLocator, driver);

		} catch (AssertionError e) {

			throw e;
		}
	}

	public static void verifyElementDisplayed(By locator, WebDriver driver, String elementDescription) {

		//waitForSpinnerToDisappear(20, driver);
		
		try {

			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Constants.EXPLICIT_WAIT));
			WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

			if (element.isDisplayed()) {
				System.out.println(elementDescription + " is displayed as expected.");

			} else {
				System.out.println(elementDescription + " is NOT displayed.");

			}

			// Assert to fail the test if the element is not displayed
			Assert.assertTrue(elementDescription + " is not displayed", element.isDisplayed());

		} catch (TimeoutException e) {

			System.out.println(elementDescription + " is NOT displayed within the wait time.");
			Assert.fail(elementDescription + " is not displayed within the wait time.");

		}

	}

	@FunctionalInterface
	public interface RunnableWithException {
		void run() throws Exception;
	}

	public static void addHighlightCommentAndScreenshot(By locator, String commentText, String color, WebDriver driver,
			RunnableWithException screenshotMethod) throws Exception {

		// Highlight element
		greenHighlightElement(locator, driver);

		// Add comment
		addCommentToMiddleOfPage(commentText, color, driver);

		// Take screenshot (using the passed screenshot method)
		screenshotMethod.run();

		// Remove highlight and comments
		removeHighlight(locator, driver);
		removeComments(driver);

	}

	public static void redHighlightElement(By locator, WebDriver driver) {

		WebElement element = driver.findElement(locator);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].setAttribute('style', 'border: 3px solid tomato;');", element);

	}

	public static void greenHighlightElement(By locator, WebDriver driver) throws InterruptedException {

		WebElement element = driver.findElement(locator);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].setAttribute('style', 'border: 3px solid limegreen;');", element);

	}

	public static void addCommentToMiddleOfPage(String commentText, String backgroundColor, WebDriver driver)
			throws InterruptedException {

		String script = "var comment = document.createElement('div');" + "comment.classList.add('comment');"
				+ "comment.style.position = 'fixed';" + // Use fixed to position relative to the viewport
				"comment.style.left = '50%';" + "comment.style.top = '50%';" + "comment.style.background = '"
				+ backgroundColor + "';" + "comment.style.padding = '10px';" + "comment.style.color = 'black';"
				+ "comment.style.fontWeight = 'bold';" + "comment.style.fontSize = '14px';"
				+ "comment.style.transform = 'translate(-50%, -50%)';" + // Center the element
				"comment.style.zIndex = '9999';" + // Ensure it's on top of other elements
				"comment.innerHTML = '" + commentText + "';" + "document.body.appendChild(comment);";
		((JavascriptExecutor) driver).executeScript(script);
		Thread.sleep(2000);

	}

	public static void removeComments(WebDriver driver) {

		((JavascriptExecutor) driver).executeScript("var comments = document.querySelectorAll('.comment');"
				+ "comments.forEach(comment => comment.parentNode.removeChild(comment));");

	}

	public static void removeHighlight(By locator, WebDriver driver) throws InterruptedException {

		WebElement element = driver.findElement(locator);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].style.removeProperty('border')", element);

	}

	public static void blueHighlightElement(By locator, WebDriver driver) {

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].setAttribute('style', 'border: 3px solid blue;');", locator);

	}

	public static void addCommentUnderElement(By locator, String commentText, String backgroundColor,
			WebDriver driver) {

		int elementX = ((WebElement) locator).getLocation().getX();
		int elementY = ((WebElement) locator).getLocation().getY();

		int commentX = elementX;
		int commentY = elementY + ((WebElement) locator).getSize().getHeight() + 10;

		addComment(commentText, commentX, commentY, backgroundColor, driver);

	}

	protected static void addComment(String commentText, int x, int y, String backgroundColor, WebDriver driver) {

		String script = "var comment = document.createElement('div');" +

				"comment.classList.add('comment');" + "comment.style.position = 'absolute';" + "comment.style.left = '"
				+ x + "px';" + "comment.style.top = '" + y + "px';" + "comment.style.background = '" + backgroundColor
				+ "';" + "comment.style.padding = '10px';" + "comment.style.color = 'black';"
				+ "comment.style.fontWeight = 'bold';" + "comment.style.fontSize = '14px';"
				+ "comment.style.opacity = '1.0';" + "comment.innerHTML = '" + commentText + "';"
				+ "document.body.appendChild(comment);";

		((JavascriptExecutor) driver).executeScript(script);

	}

	public static void explicitlyWaitForVisibility(By locator, int timeoutInSeconds, WebDriver driver) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

	}

	public static void explicitlyWaitForVisibilityLocator(By locator, int timeoutInSeconds, WebDriver driver) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

	}

	public static void explicitlyWaitForInvisibility(By locator, int timeoutInSeconds, WebDriver driver) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));

	}

	public static void explicitlyWaitForClickibility(By locator, int timeoutInSeconds, WebDriver driver) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
		wait.until(ExpectedConditions.elementToBeClickable(locator));

	}

	public static void explicitlyWaitForStaleness(By locator, int timeoutInSeconds, WebDriver driver) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
		WebElement element = driver.findElement(locator);
		wait.until(ExpectedConditions.stalenessOf(element));

	}

//	public static void waitForSpinnerToDisappear(int timeoutSeconds, WebDriver driver) {

//		WebDriverWait wait = new WebDriverWait(driver, timeoutSeconds);
//
//		By spinnerLocator = By.cssSelector("i.fa-spinner");
//
//		// Poll more frequently - every 100 milliseconds
//
//		wait.pollingEvery(Duration.ofMillis(80));
//
//		try {
//
//			// Directly wait for the spinner to disappear but allow a short delay to check
//			// its initial presence
//
//			wait.withTimeout(Duration.ofMillis(80)) // Short duration just to check presence
//
//					.until(ExpectedConditions.visibilityOfElementLocated(spinnerLocator));
//
//			// If spinner is visible, then wait for it to disappear with the full timeout
//
//			wait.withTimeout(Duration.ofSeconds(timeoutSeconds))
//
//					.until(ExpectedConditions.invisibilityOfElementLocated(spinnerLocator));
//
//			System.out.println("Spinner disappeared, proceeding with the test.");
//
//		} catch (TimeoutException e) {
//
//			// If the spinner never appears or disappears within the short initial timeout,
//			// catch the exception
//
//			System.out.println("Spinner did not appear or disappeared quickly.");
//
//		}

		public static void waitForSpinnerToDisappear(int timeoutSeconds, WebDriver driver) {

		    By spinnerLocator = By.cssSelector("i.fa-spinner");
		    long startTime = System.currentTimeMillis();
		    long endTime = startTime + timeoutSeconds * 500;

		    while (System.currentTimeMillis() < endTime) {

		        // Check if spinner is present
		        if (driver.findElements(spinnerLocator).isEmpty()) {
		            System.out.println("Spinner not present. Proceeding with the test.");
		            return; // Exit immediately if no spinner is found
		        }
		        // Short sleep to avoid hammering the UI
		        try {
		            Thread.sleep(50); // Poll every 50ms
		        } catch (InterruptedException e) {
		            Thread.currentThread().interrupt();
		            throw new RuntimeException("Thread was interrupted", e);
		        }
		    }
		    // If spinner is still visible after timeout
		    throw new RuntimeException("Spinner did not disappear within the timeout.");
		}
		
	public void handlePagination(WebDriver driver) throws InterruptedException {

		if (checkNoRecords(driver)) {

			System.out.println("No records found.");

			return;

		}

		explicitlyWaitForClickibility(By.xpath(
				"//span[contains(@class, 'input-group-text') and (contains(., 'Total record') or contains(., 'Record'))]"),
				Constants.EXPLICIT_WAIT, driver);
		explicitlyWaitForVisibilityLocator(By.xpath(
				"//span[contains(@class, 'input-group-text') and (contains(., 'Total record') or contains(., 'Record'))]"),
				Constants.EXPLICIT_WAIT, driver);
		String pageInfoText = driver.findElement(By.xpath(
				"//span[contains(@class, 'input-group-text') and (contains(., 'Total record') or contains(., 'Record'))]"))
				.getText();

		if (!pageInfoText.contains("Page")) {

			System.out.println("Only one page available. No pagination needed.");

			return; // Exit if only one page is present

		}

		int totalPages = extractTotalPages(pageInfoText);

		if (totalPages <= 7) {

			// No ellipses, just verify the last page

			navigateAndVerifyPage(driver, totalPages, totalPages);

		} else {

			// Handle ellipsis scenario

			int randomPage = new Random().nextInt(6) + 1;

			navigateAndVerifyPage(driver, randomPage, totalPages);

			navigateAndVerifyPage(driver, totalPages, totalPages); // Verify the last page

		}

	}

	private boolean checkNoRecords(WebDriver driver) {

		return !driver.findElements(By.xpath("//div[contains(text(), 'No record') or contains(text(), 'No report')]")).isEmpty();

	}

	private void navigateAndVerifyPage(WebDriver driver, int page, int totalPages) throws InterruptedException {

		if (page > 1) {

			By pageLinkSelector = By.xpath("//a[@class='page-link rounded-circle'][text()='" + page + "']");

			explicitlyWaitForClickibility(pageLinkSelector, Constants.EXPLICIT_WAIT, driver);

			click(pageLinkSelector, driver);
			Thread.sleep(500);

		}

		// Wait for the correct page number to appear in the pageInfo

		waitForCorrectPageInfo(driver, page, totalPages);

	}

	// Method to wait until the page info text updates to the expected page number

	private void waitForCorrectPageInfo(WebDriver driver, int expectedPage, int totalPages)
			throws InterruptedException {

		By pageInfoLocator = By.xpath(
				"//span[contains(@class, 'input-group-text') and (contains(., 'Total record') or contains(., 'Record'))]");

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Constants.EXPLICIT_WAIT));

		boolean isCorrectPageInfo = wait.until((WebDriver d) -> {

			WebElement pageInfoElement = d.findElement(pageInfoLocator);

			String pageInfoText = pageInfoElement.getText();

			String expectedPageInfo = "Page " + expectedPage + " of " + totalPages + ". Total records found: "
					+ getTotalRecords(driver) + ".";

			System.out.println("Checking page info: " + pageInfoText);

			return pageInfoText.equals(expectedPageInfo);

		});

		if (!isCorrectPageInfo) {

			throw new AssertionError("Page info text does not match expected after waiting: expected <" + "Page "
					+ expectedPage + " of " + totalPages + "> but was different.");

		}

	}

	private int getTotalRecords(WebDriver driver) {

		By totalRecordsLocator = By.xpath(
				"//span[contains(@class, 'input-group-text') and (contains(., 'Total record') or contains(., 'Record'))]");

		explicitlyWaitForVisibility(totalRecordsLocator, Constants.EXPLICIT_WAIT, driver);

		String text = driver.findElement(totalRecordsLocator).getText();

		String[] parts = text.split("found: ");

		if (parts.length > 1) {

			// Remove any non-digit characters before parsing to ensure the string is a
			// valid integer

			String numberString = parts[1].replaceAll("[^0-9]", "").trim();

			return Integer.parseInt(numberString);

		} else {

			throw new IllegalStateException("Total records text is not formatted as expected: " + text);

		}

	}

	private int extractTotalPages(String pageInfo) {

		return Integer.parseInt(pageInfo.split("of ")[1].split("\\.")[0].trim());

	}
	
	

	
	
}