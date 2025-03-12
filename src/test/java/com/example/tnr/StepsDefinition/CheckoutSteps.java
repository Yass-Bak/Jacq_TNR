package com.example.tnr.StepsDefinition;

import com.example.tnr.Constants.PathConstants;
import com.example.tnr.Utils.ScreenshotUtils;
import com.example.tnr.Hooks;
import io.cucumber.java.en.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertTrue;

public class CheckoutSteps {
    private static final Logger logger = LoggerFactory.getLogger(CheckoutSteps.class);
    private WebDriver driver = Hooks.driver;
    private WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

    @Given("the user accesses the e-commerce website on Google Chrome")
    public void the_user_accesses_the_e_commerce_website_on_google_chrome() {
        try {
            logger.info("Successfully navigated to the e-commerce website.");
        } catch (Exception e) {
            logger.error("Error at: @Given(\"the user accesses the e-commerce website on Google Chrome\")", e);
            ScreenshotUtils.takeScreenshot(driver);
            throw new RuntimeException("Step failed: " + e.getMessage());
        }
    }

    @Given("chose france as a location")
    public void chose_france_as_a_location() {
        try {
            WebElement regionPopup = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("popin-locale")));
            assertTrue("regionPopup is not visible", regionPopup.isDisplayed());
            WebElement regionButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("popin-locale")));
            regionButton.click();
            Thread.sleep(1000);
            logger.info("Successfully chose France as location.");
        } catch (Exception e) {
            logger.error("Error at: @Given(\"chose france as a location\")", e);
            ScreenshotUtils.takeScreenshot(driver);
            throw new RuntimeException("Step failed: " + e.getMessage());
        }
    }

    @Given("click on continue without accepting cookiees")
    public void click_on_continue_without_accepting_cookiees() {
        try {
            WebElement cookiesPopup = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("onetrust-accept-btn-handler")));
            assertTrue("cookiesPopup is not visible", cookiesPopup.isDisplayed());
            WebElement cookiesButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("onetrust-accept-btn-handler")));
            cookiesButton.click();
            Thread.sleep(1000);
            logger.info("Successfully decline cookies");
        } catch (Exception e) {
            logger.error("Error at: @Given(\"I am on the checkout page France As Region And Accepting all Cookies\")",
                    e);
            ScreenshotUtils.takeScreenshot(driver);
            throw new RuntimeException("Step failed: " + e.getMessage());
        }
    }

    @When("the user selects a product from the store")
    public void the_user_selects_a_product_from_the_store() {
        try {
            WebElement navproductButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("Bags")));
            navproductButton.click();

            WebElement navproductButton2 = wait.until(ExpectedConditions.elementToBeClickable(By.id("Shoulder_bags")));
            navproductButton2.click();

            WebElement productButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"searchWrapper\"]/infinite-scroll/tile-container/product-tile[2]/div/div[2]/div[3]/div[1]/a/img")));
            productButton.click();
            logger.info("Successfully selected a product from the store.");
        } catch (Exception e) {
            logger.error("Error at: @When(\"the user selects a product from the store\")", e);
            ScreenshotUtils.takeScreenshot(driver);
            throw new RuntimeException("Step failed: " + e.getMessage());
        }
    }

    @When("the user add a product to the cart")
    public void the_user_add_a_product_to_the_cart() {
        try {
            WebElement addToCartButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='addToCartBtn']")));
            addToCartButton.click();
            logger.info("Successfully added a product to the cart.");
        } catch (Exception e) {
            logger.error("Error at: @When(\"the user add a product to the cart\")", e);
            ScreenshotUtils.takeScreenshot(driver);
            throw new RuntimeException("Step failed: " + e.getMessage());
        }
    }

    @When("the user proceds to checkout")
    public void the_user_proceds_to_checkout() {
        try {
            WebElement checkoutButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='miniCart__buttons__checkout']")));
            checkoutButton.click();
            logger.info("Successfully proceeded to checkout.");
        } catch (Exception e) {
            logger.error("Error at: @When(\"the user proceds to checkout\")", e);
            ScreenshotUtils.takeScreenshot(driver);
            throw new RuntimeException("Step failed: " + e.getMessage());
        }
    }

    @Then("the login page should be displayed")
    public void the_login_page_should_be_displayed() {
        try {
            WebElement loginForm = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("guest-customer")));
            assertTrue("Login form not visible", loginForm.isDisplayed());
            logger.info("Login page is displayed.");
        } catch (Exception e) {
            logger.error("Error at: @Then(\"the login page should be displayed\")", e);
            ScreenshotUtils.takeScreenshot(driver);
            throw new RuntimeException("Step failed: " + e.getMessage());
        }
    }

    @When("the user enters valid login")
    public void the_user_enters_valid_login() {
        try {
            WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='email-guest']")));
            emailInput.sendKeys("khitem.guerbouj.ext@gmail.com");

            WebElement guestCheckoutButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='guest-customer']/fieldset/div[2]/button[1]")));
            guestCheckoutButton.click();
            logger.info("Successfully entered valid login.");
        } catch (Exception e) {
            logger.error("Error at: @When(\"the user enters valid login\")", e);
            ScreenshotUtils.takeScreenshot(driver);
            throw new RuntimeException("Step failed: " + e.getMessage());
        }
    }

    @When("the user enters valid password")
    public void the_user_enters_valid_password() {
        try {
            WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='password']")));
            passwordInput.sendKeys("Yassine_1144!");
            logger.info("Successfully entered valid password.");
        } catch (Exception e) {
            logger.error("Error at: @When(\"the user enters valid password\")", e);
            ScreenshotUtils.takeScreenshot(driver);
            throw new RuntimeException("Step failed: " + e.getMessage());
        }
    }

    @When("the user clicks on the login button")
    public void the_user_clicks_on_the_login_button() {
        try {
            WebElement guestCheckoutButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='registered-customer']/fieldset/div[3]/button[1]")));
            guestCheckoutButton.click();
            logger.info("Successfully clicked on the login button.");
        } catch (Exception e) {
            logger.error("Error at: @When(\"the user clicks on the login button\")", e);
            ScreenshotUtils.takeScreenshot(driver);
            throw new RuntimeException("Step failed: " + e.getMessage());
        }
    }

    @Then("the shipping information form should be displayed")
    public void the_shipping_information_form_should_be_displayed() {
        try {
            WebElement shippingForm = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='dwfrm_shipping']")));
            assertTrue("Shipping form not visible", shippingForm.isDisplayed());
            logger.info("Shipping information form is displayed.");
        } catch (Exception e) {
            logger.error("Error at: @Then(\"the shipping information form should be displayed\")", e);
            ScreenshotUtils.takeScreenshot(driver);
            throw new RuntimeException("Step failed: " + e.getMessage());
        }
    }

    @When("the user fills in the shipping details with")
    public void the_user_fills_in_the_shipping_details_with(io.cucumber.datatable.DataTable dataTable) {
        try {
            Map<String, String> data = dataTable.asMap(String.class, String.class);

            WebElement firstNameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='shippingFirstNamedefault']")));
            firstNameField.sendKeys(data.get("First Name"));

            WebElement lastNameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='shippingLastNamedefault']")));
            lastNameField.sendKeys(data.get("Last Name"));

            WebElement companyField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='shippingAddressTwodefault']")));
            companyField.sendKeys(data.get("Company"));

            WebElement phoneNumberField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='shippingPhoneNumberdefault']")));
            phoneNumberField.sendKeys(data.get("Phone Number"));
            logger.info("Successfully filled in the shipping details.");
        } catch (Exception e) {
            logger.error("Error at: @When(\"the user fills in the shipping details with\")", e);
            ScreenshotUtils.takeScreenshot(driver);
            throw new RuntimeException("Step failed: " + e.getMessage());
        }
    }

    @When("the user fills his Adresse or his zip code the system should send checkout details \"country, postal code, orderType to OMS")
    public void the_user_fills_his_adresse_or_his_zip_code_the_system_should_send_checkout_details_country_postal_code_order_type_to_oms() {
        try {
            WebElement addressField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='shippingAddressOnedefault']")));
            addressField.sendKeys("Av. d'Aquitaine");

            WebElement zipCodeField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='shippingZipCodedefault']")));
            zipCodeField.sendKeys("85100");

            WebElement cityField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='shippingAddressCitydefault']")));
            cityField.sendKeys("Les Sables-d'Olonne");
            Thread.sleep(2000);
            logger.info("Successfully filled address and zip code.");
        } catch (Exception e) {
            logger.error("Error at: @When(\"the user fills his Adresse or his zip code the system should send checkout details\")", e);
            ScreenshotUtils.takeScreenshot(driver);
            throw new RuntimeException("Step failed: " + e.getMessage());
        }
    }

    @When("the user selects {string} as the shipment method")
    public void the_user_selects_as_the_shipment_method(String shipmentMethod) {
        try {
            // Wait for the fieldset to be present
            WebElement fieldset = new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.presenceOfElementLocated(By.className("shipping-method-block")));
            assertTrue("not visible",fieldset.isDisplayed());
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView(true);", fieldset);

            // Wait for the radio button to be present
            WebElement chronopostRadioButton = new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"dwfrm_shipping\"]/div/fieldset[3]/div[1]/div[1]")));

            chronopostRadioButton.click();
            Thread.sleep(1000);
            logger.info("Successfully selected " + shipmentMethod + " as the shipment method.");
        } catch (Exception e) {
            logger.error("Error at: @When(\"the user selects {string} as the shipment method\")", e);
            ScreenshotUtils.takeScreenshot(driver);
            throw new RuntimeException("Step failed: " + e.getMessage());
        }
    }

    @Then("the {string} button should be displayed")
    public void the_button_should_be_displayed(String buttonName) {
        try {
            Thread.sleep(1000);
            WebElement Payment = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"checkout-main\"]/div[1]/div[8]/div/button")));
            assertTrue(buttonName + " button not visible", Payment.isDisplayed());
            logger.info(buttonName + " button is displayed.");
        } catch (Exception e) {
            logger.error("Error at: @Then(\"the {string} button should be displayed\")", e);
            ScreenshotUtils.takeScreenshot(driver);
            throw new RuntimeException("Step failed: " + e.getMessage());
        }
    }

    @When("the user selects the button {string}")
    public void the_user_selects_the_button(String buttonName) {
        try {
            WebElement button = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"checkout-main\"]/div[1]/div[8]/div/button")));
            button.click();
            logger.info("Successfully clicked on " + buttonName + " button.");
        } catch (Exception e) {
            logger.error("Error at: @When(\"the user selects the button {string}\")", e);
            ScreenshotUtils.takeScreenshot(driver);
            throw new RuntimeException("Step failed: " + e.getMessage());
        }
    }

    @Then("the system should display a recap of the Client Personal Information and Shipping and Shipment Mode Chosen before {string} with estimated Shipment Date and available Payment methods")
    public void the_system_should_display_a_recap_of_the_client_personal_information_and_shipping_and_shipment_mode_chosen_before_with_estimated_shipment_date_and_available_payment_methods(String shipmentMode) {
        try {
            WebElement clientInfo = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"checkout-main\"]/div[1]/div[3]/div[2]/div")));
            assertTrue("Client personal information not displayed", clientInfo.isDisplayed());

            WebElement shippingModeInfo = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"checkout-main\"]/div[1]/div[5]/div/div")));
            assertTrue(shipmentMode + " shipment mode not displayed", shippingModeInfo.isDisplayed());
            logger.info("Successfully displayed recap of client information and shipping details.");
        } catch (Exception e) {
            logger.error("Error at: @Then(\"the system should display a recap of the Client Personal Information and Shipping and Shipment Mode Chosen before {string} with estimated Shipment Date and available Payment methods\")", e);
            ScreenshotUtils.takeScreenshot(driver);
            throw new RuntimeException("Step failed: " + e.getMessage());
        }
    }

    @When("the user selects {string} as the payment method")
    public void the_user_selects_as_the_payment_method(String paymentMethod) {
        try {
            WebElement paymentOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"paymentOptions\"]/li[1]")));
            paymentOption.click();
            logger.info("Successfully selected " + paymentMethod + " as the payment method.");
        } catch (Exception e) {
            logger.error("Error at: @When(\"the user selects {string} as the payment method\")", e);
            ScreenshotUtils.takeScreenshot(driver);
            throw new RuntimeException("Step failed: " + e.getMessage());
        }
    }

    @Then("System Should display a form under the checked button")
    public void system_should_display_a_form_under_the_checked_button() {
        try {
            WebElement visaInfoForm = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='component_scheme']")));
            assertTrue("Visa info form not displayed", visaInfoForm.isDisplayed());
            logger.info("Visa info form is displayed.");
        } catch (Exception e) {
            logger.error("Error at: @Then(\"System Should display a form under the checked button\")", e);
            ScreenshotUtils.takeScreenshot(driver);
            throw new RuntimeException("Step failed: " + e.getMessage());
        }
    }

    @Then("the user enters the following payment details:")
    public void the_user_enters_the_following_payment_details(io.cucumber.datatable.DataTable dataTable) {
        try {
            Map<String, String> data = dataTable.asMap(String.class, String.class);

            // Card number
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.xpath("//iframe[@title='Iframe for card number']")));
            WebElement numcarte = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='1234 1234 1234 1234']")));
            numcarte.sendKeys(data.get("Card Number"));
            driver.switchTo().defaultContent();

            // Expiry date
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.xpath("//iframe[@title='Iframe for expiry date']")));
            WebElement dateExp = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='MM/YY']")));
            dateExp.sendKeys(data.get("Expiry Date"));
            driver.switchTo().defaultContent();

            // CVC
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.xpath("//iframe[@title='Iframe for security code']")));
            WebElement codeSecurite = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='CVC']")));
            codeSecurite.sendKeys(data.get("CVC"));
            driver.switchTo().defaultContent();

            // Name on card
            WebElement nomCarte = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='J. Smith']")));
            nomCarte.sendKeys(data.get("Name on Card"));
            logger.info("Successfully entered payment details.");
        } catch (Exception e) {
            logger.error("Error at: @Then(\"the user enters the following payment details\")", e);
            ScreenshotUtils.takeScreenshot(driver);
            throw new RuntimeException("Step failed: " + e.getMessage());
        }
    }

    @Then("the button verify my information should be display")
    public void the_button_verify_my_information_should_be_display() {
        try {
            WebElement verifyInfoButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"checkout-main\"]/div[1]/div[8]/button[1]")));
            assertTrue("Verify My Information button not displayed", verifyInfoButton.isDisplayed());
            logger.info("Verify My Information button is displayed.");
        } catch (Exception e) {
            logger.error("Error at: @Then(\"the button verify my information should be display\")", e);
            ScreenshotUtils.takeScreenshot(driver);
            throw new RuntimeException("Step failed: " + e.getMessage());
        }
    }
    @And("the user Click on “Verify My Information” Button")
    public void theUserClickOnVerifyMyInformationButton() {
        WebElement verifyInfoButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"checkout-main\"]/div[1]/div[8]/button[1]")));
        verifyInfoButton.click();
        logger.info("Verify My Information button is displayed.");
    }

    @Then("the user Click on {string} Button")
    public void the_user_click_on_button(String buttonName) {
        try {
            WebElement button = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"checkout-main\"]/div[1]/div[8]/button[1]")));
            button.click();
            logger.info("Successfully clicked on " + buttonName + " button.");
        } catch (Exception e) {
            logger.error("Error at: @Then(\"the user Click on {string} Button\")", e);
            ScreenshotUtils.takeScreenshot(driver);
            throw new RuntimeException("Step failed: " + e.getMessage());
        }
    }

    @Then("System Should display the Terms and Conditions Acceptance and Consent and Agreement Section.")
    public void system_should_display_the_terms_and_conditions_acceptance_and_consent_and_agreement_section() {
        try {
            WebElement NewsLetter = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"checkout-main\"]/div[1]/div[7]/div[2]/label/span/div")));
            assertTrue("NewsLetter section not displayed", NewsLetter.isDisplayed());
            WebElement acceptanceSection = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"checkout-main\"]/div[1]/div[7]/div[3]/label/span")));
            assertTrue("Terms and conditions section not displayed", acceptanceSection.isDisplayed());
            logger.info("Terms and conditions section is displayed.");
        } catch (Exception e) {
            logger.error("Error at: @Then(\"System Should display the Terms and Conditions Acceptance and Consent and Agreement Section\")", e);
            ScreenshotUtils.takeScreenshot(driver);
            throw new RuntimeException("Step failed: " + e.getMessage());
        }
    }

    @Then("the user checks {string}")
    public void the_user_checks(String checkboxLabel) {
        try {
            WebElement acceptanceAndConsent = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("/html/body/div[2]/div/div/div[1]/div[7]/div[3]/label")));
            acceptanceAndConsent.click();

            logger.info("Successfully checked " + checkboxLabel + ".");
        } catch (Exception e) {
            logger.error("Error at: @Then(\"the user checks {string}\")", e);
            ScreenshotUtils.takeScreenshot(driver);
            throw new RuntimeException("Step failed: " + e.getMessage());
        }
    }
    @And("the user click on {string} button")
    public void theUserClickOnButton(String arg0) {
        WebElement PayButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("checkout-main-button")));
        PayButton.click();
    }
    @Then("the system should display a thank you page with an order summary")
    public void the_system_should_display_a_thank_you_page_with_an_order_summary() {
        try {
            WebElement thankyoupage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"checkout-main\"]/div[1]/div[2]/div[2]")));
            assertTrue("Thank you page not displayed", thankyoupage.isDisplayed());
            WebElement orderNumberElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"checkout-main\"]/div[1]/div[2]/div[1]/p[1]/span")));
            assertTrue("Order number not displayed", orderNumberElement.isDisplayed());
            logger.info("Thank you page with order summary is displayed.");
            String orderNumber = orderNumberElement.getText();
            File projectRoot = new File(System.getProperty("user.dir"));
            File resourcesFolder = new File(projectRoot, "src/test/resources");
            if (!resourcesFolder.exists()) {
                resourcesFolder.mkdirs();
            }
            File outputFile = new File(resourcesFolder, "order_number.txt");
            try (FileWriter writer = new FileWriter(outputFile, true)) { // true for append mode
                writer.write(orderNumber + System.lineSeparator()); // Add a new line after each entry
            } catch (IOException e) {
                logger.error("Error writing to file", e);
                throw new RuntimeException("Failed to write order number to file: " + e.getMessage());
            }
            logger.info("Order number " + orderNumber + " appended to " + outputFile.getAbsolutePath());
            driver.get(PathConstants.BASE_URL);

        } catch (Exception e) {
            logger.error("Error at: @Then(\"the system should display a thank you page with an order summary\")", e);
            ScreenshotUtils.takeScreenshot(driver);
            throw new RuntimeException("Step failed: " + e.getMessage());
        }
    }
}