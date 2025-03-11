package com.example.tnr;

import Constants.PathConstants;
import Utils.DevToolsUtils;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.openqa.selenium.*;
import java.util.*;
import io.cucumber.java.Before;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
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
	public void setupSelenium() {
		System.setProperty("webdriver.chrome.driver", "C:\\TNR\\chromedriver.exe");
		ChromeOptions chromeOptions = getDefaultChromeOptions();
		driver = new ChromeDriver(chromeOptions);
		DevToolsUtils.HttpAuth(driver);
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
		options.addArguments("--lang=fr");
		options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36");
		options.addArguments("--disable-infobars");
		options.addArguments("--disable-extensions");
		options.addArguments("--disable-gpu");
		options.addArguments("--no-sandbox");
		options.addArguments("--disable-dev-shm-usage");
		options.addArguments("--window-size=1920,1080");
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
}
