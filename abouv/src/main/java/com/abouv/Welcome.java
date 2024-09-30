package com.abouv;

import java.time.Duration;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Welcome {
    
    private WebDriver driver;
    String url ="https://sg-app.abouv.com/welcome";
    
    private By signUpLink  = By.xpath("//a[normalize-space()='Sign up']");
    private By skipButton  = By.xpath("/html/body/div/div[3]/div[2]/button[1]");
    private By countryCode  = By.xpath("//span[normalize-space()='+91']");
    private By enterMobileNumber = By.xpath("//input[@placeholder='Mobile Number']");
    private By clickContinue = By.xpath("//button[@type='submit']");
    private By otpInput = By.xpath("//input[@aria-label='Please enter OTP character 1']");
    private By continuOnOTP = By.xpath("//button[@type='submit']");
    
    private By incorrectOTPerr = By.xpath("//div[@class='w-[90%] max-w-[300px] p-1 bg-[#FECACA] rounded-xl text-[#800000] gap-2 flex items-center justify-center text-sm font-baloo-2 font-medium mb-1 mx-auto']");

    @BeforeClass
    public void setUp() {
        ChromeOptions co = new ChromeOptions();
        co.addArguments("--remote-allow-origins=*");
        System.setProperty("webdriver.chrome.driver", "D:\\Driver\\chromedriver-win64\\chromedriver.exe");
        driver = new ChromeDriver(co);
        
        driver.get(url);
        driver.manage().window().maximize();
    }

    @Test(priority = 1)
    public void signUp() {
        String pageTitle = driver.getTitle();
        System.out.println("Page Title after sign up: " + pageTitle);
        
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        WebElement signUpElement = wait.until(ExpectedConditions.elementToBeClickable(signUpLink));
        signUpElement.click();
    }

    @Test(priority = 2)
    public void clickSkip() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); 
        boolean isSkipButtonPresent = false;
        
        try {
            WebElement skipElement = wait.until(ExpectedConditions.visibilityOfElementLocated(skipButton));
            if (skipElement.isDisplayed()) {
                isSkipButtonPresent = true;
                skipElement.click();
                System.out.println("Skip button clicked.");
            }
        } catch (Exception e) {
            System.out.println("Skip button is not available.");
        }
        
        if (!isSkipButtonPresent) {
            System.out.println("Proceeding to the next method as skip button was not clicked.");
        }
    }

    @Test(priority = 3)
    public void signUpUsingMobile() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Check the country code
        WebElement countryCodeElement = wait.until(ExpectedConditions.visibilityOfElementLocated(countryCode));
        String countryCodeText = countryCodeElement.getText();
        
        if (countryCodeText.equals("+91")) {
            // Enter mobile number
            WebElement mobileInputElement = driver.findElement(enterMobileNumber);
            mobileInputElement.sendKeys("6372992622"); // Replace with the actual mobile number to be entered
            System.out.println("Mobile number entered.");
        } else {
            System.out.println("Please change the country code to +91.");
        }
        
        WebElement element = driver.findElement(clickContinue);
        element.click();
        System.out.println("Clicked on Continue");
    }

    @Test(priority = 4)
    public void enterOtpManually() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter the OTP received: ");
        String otp = scanner.nextLine();
        
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement otpInputElement = wait.until(ExpectedConditions.visibilityOfElementLocated(otpInput));
        otpInputElement.sendKeys(otp);
        
        // Click submit
        WebElement submitOtp = wait.until(ExpectedConditions.elementToBeClickable(continuOnOTP));
        submitOtp.click();
        System.out.println("OTP submitted.");
        
        // Close the scanner
        scanner.close();
    }
    

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit(); // Close the browser
        }
    }
}
