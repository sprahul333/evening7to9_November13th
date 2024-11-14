package loginApp;

import com.google.common.io.Files;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;

public class LoginTestCases {


    WebDriver driver;

    public WebDriver getDriver(String browser)
    {

        if(browser.equalsIgnoreCase("CHROME"))
        {
            driver=new ChromeDriver();
        }

        else if(browser.equalsIgnoreCase("FIREFOX"))
        {
            driver=new FirefoxDriver();
        }

        else if(browser.equalsIgnoreCase("Edge"))
        {
            driver=new EdgeDriver();
        }

        return driver;
    }

    @Parameters("Browser")
    @BeforeClass //Before triggering the test cases in the given class
    public void createDrivers(String browser)
    {
        driver=getDriver(browser);

        //Maximize the browser window
        driver.manage().window().maximize();

        //Deleting the Cookies
        driver.manage().deleteAllCookies();
    }

    @Parameters("URL")
    @BeforeMethod //Gets Executed before each and every test case
    public void launchApp(String url)
    {
        //Navigate to the URL
        //driver.navigate().to("https://practicetestautomation.com/practice-test-login/");
        driver.get(url);

        //Difference between driver.get() and driver.navigate().to()
        //driver.get() ---> Will launch the application and waits till the application is loaded
        //driver.navigate().to() ---> Will launch the application and will not wait till the application is loaded
    }

    @Test(description = "dummy Test Case")
    public void dummy()
    {
        System.out.println("sample program");
    }

    @DataProvider(name = "loginData") //Providing the test data to the test cases
    public Object[][] getData()
    {
        Object[][] o1=new Object[2][2];

        o1[0]=new Object[]{"student","Password123"};
        o1[1]=new Object[]{"admin","admin"};

        return o1;
    }

    //@Test --- Represents a test case
    //loginApp --- Represents a test method because this method is associated with @Test
    @Test(description = "Login to the application",dataProvider = "loginData")
    public void loginApp(String userName, String password) throws IOException {
        //When there are multiple attributes for a HTML tag, first priority is for ID, as it is not unique across the web page
        //driver.findElement()

        //Element means textbox, radio button, checkbox, button, link, etc
        //driver ---> Represents the browser
        //.findElement() ---> Finds for that particular webelement in that page

        //Syntax of finding an element on the basis of id:
        //driver.findElement(By.id(value));

        WebElement txt_UserName=driver.findElement(By.id("username"));

        //.sendKeys() to enter the data into the text box
        txt_UserName.sendKeys(userName);

        WebElement txt_Password=driver.findElement(By.id("password"));
        txt_Password.sendKeys(password);

        WebElement btn_Submit=driver.findElement(By.id("submit"));
        btn_Submit.click(); //.click() is used to click on a button, radio button etc...

//        (TakesScreenshot)driver --> Capture the screenshot of the browser
//        ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE) --> Captures the screenshot of the browser and stores it in the form of a file
        //Here screenshots will be stored in .png file

        //Screenshots captured will be stored in the temp folders
        File src=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        File dest=new File("screenshot_"+System.currentTimeMillis()+".png");

        //Copying the file from source to destination
        Files.copy(src,dest);
    }

    @AfterClass
    public void closeBrowser() throws InterruptedException
    {
        //Waits for 10 secs before the browser is closed
        Thread.sleep(10000);
        driver.quit(); //Close the browser that we have launched
    }
}
