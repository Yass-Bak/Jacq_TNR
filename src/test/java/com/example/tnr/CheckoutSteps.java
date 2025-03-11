package com.example.tnr;

import Helpers.SelectorsHelpers;
import Utils.ScreenshotUtils;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CheckoutSteps {
    private static final Logger logger = LoggerFactory.getLogger(CheckoutSteps.class);
    private WebDriver driver = Hooks.driver;
    private String baseUrl = Hooks.BASE_URL;
    private WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));

    @Given("I am on the checkout page France As Region And Accepting all Cookies")
    public void i_am_on_the_checkout_page() {
        try {
            WebElement region = wait
                    .until(ExpectedConditions.elementToBeClickable(By.id("popin-locale")));
            WebElement cookies = wait
                    .until(ExpectedConditions.elementToBeClickable(By.id("onetrust-accept-btn-handler")));
            region.click();
            cookies.click();
            logger.info("Successfully set region to France and accepted cookies");
        } catch (Exception e) {
            logger.error("Error at: @Given(\"I am on the checkout page France As Region And Accepting all Cookies\")",
                    e);
            ScreenshotUtils.takeScreenshot(driver);
            throw new RuntimeException("Step failed: " + e.getMessage());
        }
    }

    @When("I fill the cart with a product")
    public void i_choose_a_product_to_add() {
        try {
           //Bags
            /* driver.navigate().to("https://development.jacquemus.com/en_fr/baskets-tote-bags-women");
            By Sac = By.xpath("//*[@id=\"searchWrapper\"]/infinite-scroll/tile-container/product-tile[1]/div/div[2]/div[3]/div[1]/a/img");
            Helpers.clickWhenClickable(driver,Sac,10);*/
            driver.navigate().to("https://development.jacquemus.com/en_fr/the-soli-basket/223BA045-3060-434.html");

            // Step 1: Navigate to Men's section
           /* By menSectionLocator = By.id("Men");
            Helpers.clickWhenClickable(driver, menSectionLocator, 10);
            logger.info("Navigate to Men's section successfully.");

            // Step 2: Navigate to T-shirts collection
            WebElement tshirtsLink = driver.findElement(By.xpath("//*[@id=\"t_shirts_men\"]"));
            tshirtsLink.click();
            logger.info("Navigate to T-shirts collection successfully.");

            // Step 3: Verify T-shirts section
            By headerLocator = By.xpath("//*[@id=\"navMobileListWrapper\"]/auto-scroll-horizontal/div/h1");
            WebElement header = wait.until(ExpectedConditions.visibilityOfElementLocated(headerLocator));
            assertEquals("T-shirts", header.getText());
            logger.info("Current section header: " + header.getText());

            // Step 4: Select product
            By productLocator = By.xpath("//product-tile[5]");
            Helpers.clickWhenClickable(driver, productLocator, 10);

            // Step 5: Select size
            By sizeLocator = By.xpath("//div[@id='productSizesWrapper']/ul/li[3]/button");
            Helpers.clickWhenClickable(driver, sizeLocator, 10);
            logger.info("Size selected successfully.");*/

            // Step 6: Add to cart
            By addToCartLocator = By.id("addToCartBtn");
            SelectorsHelpers.clickWhenClickable(driver, addToCartLocator, 10);
            logger.info("Add to cart successfully.");
            // Wait for cart panel
            WebElement panel = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.id("panel-minicart")));
			//Cas de Broken
			assertTrue("Panel is not visible", panel.isDisplayed());
        } catch (Exception e) {
            logger.error("Test failed at step: {}", e.getMessage(), e);
            ScreenshotUtils.takeScreenshot(driver);
            throw new RuntimeException("Step failed: " + e.getMessage());
        }
    }
/*
@When("I fill the cart with a product")
public void i_choose_a_product_to_add() {
    try {
        DevTools.NetworkCheck(driver);
        // Click to open account header
        WebElement accountHeader = driver.findElement(By.xpath("//*[@id=\"headerSecondaryNav\"]/div[2]/div/div[1]/span"));
        accountHeader.click();

            WebElement email = wait.until(
                    ExpectedConditions.presenceOfElementLocated(By.id("email"))
            );

            WebElement password = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.id("loginpassword"))
            );
            Actions actions = new Actions(driver);
            actions.click(email)
                    .pause(Duration.ofSeconds(10))
                    .sendKeys("khitem.guerbouj.ext@gmail.com")
                    .pause(Duration.ofSeconds(20))
                    .click(password)
                    .pause(Duration.ofSeconds(10))
                    .sendKeys("Yassine_1144!")
                    .pause(Duration.ofSeconds(20))
                    .perform();
            WebElement logincustomer = wait.until(
                    ExpectedConditions.presenceOfElementLocated(By.id("dwfrm_profile_logincustomer"))
            );
            actions.moveToElement(logincustomer).sendKeys(Keys.ENTER).perform();
           // JavascriptExecutor js = (JavascriptExecutor) driver;
            //js.executeScript("arguments[0].click();", logincustomer);
            logger.info("Clicked");
    } catch (Exception e) {
        logger.error("Test failed at step: {}", e.getMessage(), e);
        ScreenshotUtils.takeScreenshot(driver);
        throw new RuntimeException("Step failed: " + e.getMessage());
    }
}*/
    @And("I check if have item in my cart")
    public void i_have_items_in_my_cart() {
        try {
            driver.get(baseUrl);
            WebElement cartButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("panel-minicart-toggle")));
            cartButton.click();
            logger.info("Step 1: Open cart successfully.");

            WebElement cartItems = wait
                    .until(ExpectedConditions.visibilityOfElementLocated(By.id("panelMinicartAmount")));
            assertEquals("1", cartItems.getText());
            logger.info("Check number of items in my cart successfully.");
        } catch (Exception e) {
            logger.error("Error at: @And(\"I check if have item in my cart\")", e);
            ScreenshotUtils.takeScreenshot(driver);
            throw new RuntimeException("Step failed: " + e.getMessage());
        }
    }

    @And("I fill my shipping details")
    public void i_fill_in_my_shipping_details() {
        try {
            WebElement checkoutButton = wait
                    .until(ExpectedConditions.elementToBeClickable(By.id("miniCart__buttons__checkout")));
            checkoutButton.click();
            WebElement Email = driver.findElement(By.id("email-guest"));
            Email.sendKeys("khitem.guerbouj.ext@gmail.com");
            WebElement LoginButton = wait
                    .until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"guest-customer\"]/fieldset/div[2]/button[1]")));
            LoginButton.click();
            WebElement PasswordField = wait
                    .until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"password\"]")));
            PasswordField.sendKeys("Yassine_1144!");
            Actions actions = new Actions(driver);
            actions.moveToElement(LoginButton).sendKeys(Keys.ENTER).build().perform();
            WebElement Shipping = wait
                    .until(ExpectedConditions.visibilityOfElementLocated(By.id("shipmentSelector-default")));
            assertTrue("Shiiping form not visible", Shipping.isDisplayed());

            WebElement selectedOption = wait.until(ExpectedConditions
                    .visibilityOfElementLocated(By.className("shipmentSelector")));
            assertEquals(
                    "95 Rue Gontier Patin - Abbeville - 80100 - Khitem Guerbouj 95 Rue Gontier Patin Aix-en-provence Abbeville,  80100",
                    selectedOption.getText());
        } catch (Exception e) {
            logger.error("Error at: @And(\"I fill in my shipping details\")", e);
            ScreenshotUtils.takeScreenshot(driver);
            throw new RuntimeException("Step failed: " + e.getMessage());
        }
    }

    @And("I select a shipping method")
    public void i_select_a_shipping_method() {
        try {
            WebElement shippingMethod = wait.until(
                    ExpectedConditions.elementToBeClickable(By.cssSelector("checkoutMain__radio")));
            shippingMethod.click();
            logger.info("Shipping method selected successfully.");
        } catch (Exception e) {
            logger.error("Error at: @And(\"I select a shipping method\")", e);
            ScreenshotUtils.takeScreenshot(driver);
            throw new RuntimeException("Step failed: " + e.getMessage());
        }
    }

    @And("I fill in my payment details")
    public void i_fill_in_my_payment_details() {
        try {
            WebElement proceedToPaymentButton = wait.until(ExpectedConditions
                    .elementToBeClickable(By.xpath("/html/body/div[2]/div/div/div[1]/div[8]/div/button")));
            proceedToPaymentButton.click();

            WebElement paymentMethodSelector = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("/html/body/div[2]/div/div/div[1]/div[6]/div/form/fieldset[2]/div/ul/li[1]/input")));
            paymentMethodSelector.click();

            WebElement cardNumberField = wait.until(ExpectedConditions
                    .visibilityOfElementLocated(By.id("adyen-checkout-encryptedCardNumber-1740736840107")));
            WebElement expirationField = wait.until(ExpectedConditions
                    .visibilityOfElementLocated(By.id("adyen-checkout-encryptedExpiryDate-1740736840108")));
            WebElement cvvField = wait.until(ExpectedConditions
                    .visibilityOfElementLocated(By.id("adyen-checkout-encryptedSecurityCode-1740736840109")));

            cardNumberField.sendKeys("4111 1111 1111 1111");
            expirationField.sendKeys("03/30");
            cvvField.sendKeys("737");
            logger.info("Payment details filled successfully.");
        } catch (Exception e) {
            logger.error("Error at: @And(\"I fill in my payment details\")", e);
            ScreenshotUtils.takeScreenshot(driver);
            throw new RuntimeException("Step failed: " + e.getMessage());
        }
    }

    @And("I click the Verify My Information button")
    public void i_click_the_verify_my_information_button() {
        try {
            SelectorsHelpers.clickWhenClickable(driver, By.xpath("//*[@id=\"checkout-main\"]/div[1]/div[8]/button[1]"), 10);
            WebElement paymentSummary = wait
                    .until(ExpectedConditions.visibilityOfElementLocated(By.className("checkoutMain__stepInfo")));
            assertEquals(" Récapitulatif et paiement", paymentSummary.getText());
        } catch (Exception e) {
            logger.error("Error at: @And(\"I click the Verify My Information button\")", e);
            ScreenshotUtils.takeScreenshot(driver);
            throw new RuntimeException("Step failed: " + e.getMessage());
        }
    }

    @And("I Accept the Term and Conditions")
    public void i_accept_the_term_and_conditions() {
        try {
            WebElement termsCheckbox = wait
                    .until(ExpectedConditions.elementToBeClickable(By.xpath("//label[@for='optin-newsletter']")));
            WebElement conditionsCheckbox = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("/html[1]/body[1]/div[2]/div[1]/div[1]/div[1]/div[7]/div[3]/label[1]")));
            termsCheckbox.click();
            conditionsCheckbox.click();
            logger.info("Terms and conditions accepted successfully.");
        } catch (Exception e) {
            logger.error("Error at: @And(\"I Accept the Term and Conditions\")", e);
            ScreenshotUtils.takeScreenshot(driver);
            throw new RuntimeException("Step failed: " + e.getMessage());
        }
    }

    @And("I click the Pay button")
    public void i_click_the_pay_button() {
        try {
            WebElement payButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("checkout-main-button")));
            payButton.click();
            logger.info("Pay button clicked successfully.");
        } catch (Exception e) {
            logger.error("Error at: @And(\"I click the Pay button\")", e);
            ScreenshotUtils.takeScreenshot(driver);
            throw new RuntimeException("Step failed: " + e.getMessage());
        }
    }

    @Then("I should see an order confirmation thanks message")
    public void i_should_see_an_order_confirmation_thanks_message() {
        try {
            WebElement confirmationMessage = wait.until(ExpectedConditions
                    .visibilityOfElementLocated(By.xpath("//*[@id=\"checkout-main\"]/div[1]/div[2]/div[1]/p[1]")));
            String message = confirmationMessage.getText();
            assertTrue("Order confirmation message not found", message.contains("Merci"));
            logger.info("Order confirmation message displayed: " + message);
        } catch (Exception e) {
            logger.error("Error at: @Then(\"I should see an order confirmation thanks message\")", e);
            ScreenshotUtils.takeScreenshot(driver);
            throw new RuntimeException("Step failed: " + e.getMessage());
        }
    }
}