package com.example.tnr.Utils;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;

import java.util.HashMap;
import java.util.Map;

public class DriversOptions {
    public static ChromeOptions getDefaultChromeOptions() {
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
    public static EdgeOptions getDefaultEdgeOptions() {
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
         //options.addArguments("--headless=new", "--window-size=1920,1080");

        return options;
    }
}
