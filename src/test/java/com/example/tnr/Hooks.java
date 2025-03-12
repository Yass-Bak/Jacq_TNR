package com.example.tnr;

import Constants.PathConstants;
import Utils.DevToolsUtils;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import java.util.*;
import io.cucumber.java.Before;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;

public class Hooks {
	public static WebDriver driver;
	private static WireMockServer wireMockServer;
	private static final Logger logger = LoggerFactory.getLogger(Hooks.class);
	@Before("@Backend")
	public void setupWireMock() {
		// Start MockServer
		wireMockServer = new WireMockServer(WireMockConfiguration.wireMockConfig().dynamicPort());
		wireMockServer.start();
		configureFor("localhost", wireMockServer.port());
		WireMock.reset();

	}
	@Before("@Checkout")
	public void setupSelenium() throws InterruptedException {
		//System.setProperty("webdriver.chrome.driver", "C:\\TNR\\chromedriver.exe");
		//System.setProperty("webdriver.edge.driver", "C:\\TNR\\msedgedriver.exe");
		ChromeOptions chromeOptions = getDefaultChromeOptions();
		EdgeOptions EdgeOptions = getDefaultEdgeOptions();
		//WebDriverManager.chromedriver().setup();
		WebDriverManager.edgedriver().setup();
		//driver = new ChromeDriver(chromeOptions);
		driver = new EdgeDriver(EdgeOptions);
		DevToolsUtils.HttpAuthEdge(driver);
        //Most detection scripts interpret this as "not automated" since it's not explicitly true
		((JavascriptExecutor) driver).executeScript(
				"Object.defineProperty(navigator, 'webdriver', {get: () => undefined});"
		);
		driver.get(PathConstants.BASE_URL);

	}

	@After
	public void tearDown(Scenario scenario) {
		if (scenario.isFailed()) {
			byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
			scenario.attach(screenshot, "image/png", "Screenshot");
				driver.quit();
		}
		if (wireMockServer != null && wireMockServer.isRunning()) {
			wireMockServer.stop();
		}

	}
	private ChromeOptions getDefaultChromeOptions() {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--lang=en");
		options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36");
		options.addArguments("--disable-infobars");
		options.addArguments("--disable-extensions");
		options.addArguments("--disable-gpu");
		options.addArguments("--no-sandbox");
		options.addArguments("--disable-dev-shm-usage");
		//options.addArguments("--window-size=1920,1080");
		options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
		options.setExperimentalOption("useAutomationExtension", false);
		options.setPageLoadStrategy(PageLoadStrategy.EAGER);
		options.addArguments("--disable-blink-features=AutomationControlled");
		options.addArguments("--disable-web-security");
		Map<String, Object> prefs = new HashMap<>();
		prefs.put("credentials_enable_service", false);
		prefs.put("profile.password_manager_enabled", false);
		options.setExperimentalOption("prefs", prefs);

		// For headless testing:
		//options.addArguments("--headless=new", "--window-size=1920,1080");

		return options;
	}
	private EdgeOptions getDefaultEdgeOptions() {
		EdgeOptions options = new EdgeOptions();
		options.addArguments("--lang=en");
		options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36");
		options.addArguments("--disable-extensions");
		//options.addArguments("--disable-gpu");
		options.addArguments("--no-sandbox");
		options.addArguments("--disable-dev-shm-usage");
		// options.addArguments("--window-size=1920,1080");
		options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
		options.setExperimentalOption("useAutomationExtension", false);
		options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
		options.addArguments("--disable-blink-features=AutomationControlled");
		options.addArguments("--disable-web-security");
		options.addArguments("--disable-save-password-bubble");
		// Disable other potential popups
		options.addArguments("--disable-popup-blocking");
		options.addArguments("--disable-infobars");
		// Set other preferences to disable password manager
		Map<String, Object> prefs = new HashMap<>();
		prefs.put("credentials_enable_service", false);
		prefs.put("profile.password_manager_enabled", false);
		prefs.put("autofill.credit_card_enabled", false);
		prefs.put("payment_method.autofill_enabled", false);
		options.setExperimentalOption("prefs", prefs);

		// For headless testing:
		// options.addArguments("--headless=new", "--window-size=1920,1080");

		return options;
	}

}
