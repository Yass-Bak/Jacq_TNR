Feature: Test Purchase with Visa Payment and Chronopost Shipping in France
  As a customer in France
  I want to purchase a product using Visa and have it shipped with Chronopost
  So that I receive my order securely and on time
  @Checkout
  Scenario: check Purchase Using Visa and Chronopost Shipping
    Given the user accesses the e-commerce website on Google Chrome
    And chose france as a location
    And click on continue without accepting cookiees

    When the user selects a product from the store
    And the user add a product to the cart
    And the user proceds to checkout
    Then the login page should be displayed

    When the user enters valid login
    And the user enters valid password
    And the user clicks on the login button
    Then the shipping information form should be displayed

    When the user fills in the shipping details with
      | Field       | Value     |
      | First Name  | Khitem   |
      | Last Name   | Guerbouj  |
      | Company     | Olive     |
      | Phone Number | 581744332  |
    When the user fills his Adresse or his zip code the system should send checkout details "country, postal code, orderType to OMS
    #And OMS should return the list of eligible delivery methods and estimated delivery dates

    When the user selects "Chronopost" as the shipment method
    Then the "Proceed to Payment" button should be displayed

    When the user selects the button "Proceed to Payment"
    Then the system should display a recap of the Client Personal Information and Shipping and Shipment Mode Chosen before "Chronopost" with estimated Shipment Date and available Payment methods

    When the user selects "Visa" as the payment method
    Then System Should display a form under the checked button
    And the user enters the following payment details:
      | Card Number  |4111 1111 1111 1111|
      | Expiry Date  | 03/30 |
      | CVC          | 737    |
      | Name on Card | G.Khitem  |
    And the button verify my information should be display
    And the user Click on "Verify My Information" Button
    # Backend Actions triggered upon card submission
    #Then the system should send an authorization request with payment details to PSP Adyen and Adyen should return an authorization response
    And System Should display the Terms and Conditions Acceptance and Consent and Agreement Section.
    And the user checks "I have read and accepted JACQUEMUS.com terms of sale and their privacy policy"
    And the user click on "Pay" button
    Then the system should display a thank you page with an order summary
    #And the system should send a stock reservation request to OCI (Omnichannel Inventory)