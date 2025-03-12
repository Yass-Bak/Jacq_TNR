package com.example.tnr;

import com.example.tnr.Constants.PathConstants;
import com.example.tnr.Utils.DevToolsUtils;
import com.example.tnr.Utils.DriversOptions;
import com.example.tnr.Utils.VideoRecorderUtils;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import io.cucumber.java.Before;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;
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
		ChromeOptions chromeOptions = DriversOptions.getDefaultChromeOptions();
		EdgeOptions EdgeOptions = DriversOptions.getDefaultEdgeOptions();
		//WebDriverManager.chromedriver().setup();
		WebDriverManager.edgedriver().setup();
		//driver = new ChromeDriver(chromeOptions);
		driver = new EdgeDriver(EdgeOptions);
		DevToolsUtils.HttpAuthEdge(driver);
		((JavascriptExecutor) driver).executeScript(
				"Object.defineProperty(navigator, 'webdriver', {get: () => undefined});"
		);
		driver.get(PathConstants.BASE_URL);
		VideoRecorderUtils.initializeScreenRecording();
	}
	@After
	public void tearDown(Scenario scenario) {
		if (scenario.isFailed()) {
			byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
			scenario.attach(screenshot, "image/png", "Screenshot");
			VideoRecorderUtils.turnOffRecord(scenario);
				driver.quit();
		}
		if (wireMockServer != null && wireMockServer.isRunning()) {
			wireMockServer.stop();
		}
	}
}
