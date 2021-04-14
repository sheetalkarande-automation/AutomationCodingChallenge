package NaveenAutomationChallenge;


/*
Documentation -
Technologies - Selenium, Java, TestNG, Maven
Logic -
  1.Created Generic method to retrieve data from Carousels i.e. getCarouselData(String strCarouselHeaderLabel)
  2.Used JavaScriptExecutor to scroll till expected Carousel gets visible
  3.Used customized xapth which accepts 'strCarouselHeaderLabel' as a parameter and locate Product Name element.
  4.Used FindElements() method to get all the Product Labels.
  5.By traversing product name list - further it gets added into ArrayList object.
  6.Used Collections() class's Sort() method to sort updated Arraylist object in ascending order.
  7.Expected Result - Print ArrayList object elements.
 */

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Challenge2 {

    static WebDriver driver;
    static List<WebElement> listOfProducts;

    @Test
    public static void getDetails() throws InterruptedException {

        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "//resources//drivers//chromedriver.exe");
        ChromeOptions ops = new ChromeOptions();
        ops.addArguments("--disable-notifications");
        ops.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
        driver = new ChromeDriver(ops);

        driver.get("https://www.noon.com/uae-en/");
        driver.manage().window().maximize();

        getCarouselData("Recommended For You");
        getCarouselData("Save big on mobiles & tablets");
        getCarouselData("Top picks in electronics");
        getCarouselData("Top picks in laptops");
        getCarouselData("Bestselling fragrances");

    }

    /*
    getCarouselData(String strCarouselHeaderLabel) - This method is used to retrieve data from Carousels.
    It accept One Parameter i.e. 'strCarouselHeaderLabel' Name of Carousel.
    e.g. getCarouselData("Bestselling fragrances");
     */
    public static void getCarouselData(String strCarouselHeaderLabel) throws InterruptedException{

        System.out.println("**************"+strCarouselHeaderLabel+"**************");
        System.out.println("");

        Boolean nextButtonStatus = false;
        int nextBtnFlag = 1;
        int headerLabel;
        ArrayList<String> productName = new ArrayList<>();
        JavascriptExecutor exe = (JavascriptExecutor)driver;
        WebDriverWait wait = new WebDriverWait(driver,10);

        exe.executeScript("window.scrollTo(0, -document.body.scrollHeight)");
        Thread.sleep(3000);

        do {
            try {
                headerLabel = 1;
                wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//h3[text()='" + strCarouselHeaderLabel + "']"))));

            } catch (NoSuchElementException e) {
                headerLabel = 0;
                exe.executeScript("window.scrollBy(0,500)");
                Thread.sleep(2000);
            }
        }while (headerLabel==0);

        WebElement carouselHeaderLabel = driver.findElement(By.xpath("//h3[text()='"+strCarouselHeaderLabel+"']"));

        try {
            wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//h3[text()='"+strCarouselHeaderLabel+"']/../../..//div[contains(@class,'swiper-button-next')]"))));

        }catch (NoSuchElementException e){

            System.out.println("Next Button is not visible");
            nextBtnFlag = 0;
        }

        //Scroll to Carousel Header Label

        exe.executeScript("arguments[0].scrollIntoView(true);", carouselHeaderLabel);

        do {
            Thread.sleep(2000);
            listOfProducts = driver.findElements(By.xpath("//h3[text()='" + strCarouselHeaderLabel + "']/../../..//div[@data-qa=\"product-name\"]/div"));

            for (WebElement ele : listOfProducts) {

                if (!ele.getText().equals("")){
                    productName.add(ele.getText());
                }

            }

            if(nextBtnFlag==1) {
                WebElement nextBtn = driver.findElement(By.xpath("//h3[text()='" + strCarouselHeaderLabel + "']/../../..//div[contains(@class,'swiper-button-next')]"));

                nextButtonStatus = nextBtn.isDisplayed();

                if (nextButtonStatus){
                    nextBtn.click();
                }
            }

        }while (nextButtonStatus==true);

        //Used Collections in order to sort ArrayList of Product Name
        Collections.sort(productName);

        for (int i=0;i<productName.size();i++){

            System.out.println(productName.get(i));
        }
        System.out.println("");

    }

}
