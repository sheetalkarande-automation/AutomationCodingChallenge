package NaveenAutomationChallenge;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.List;

public class Challenge1  {

    static  WebDriver driver;
    @Test
    public static void getPopulationDetails() throws InterruptedException {

        String strTotalPopulation ="//div[@id='maincounter-wrap']//span[@class='rts-counter']";

        String strSubDetails= "//div[text()='Today' or text()='This year']/parent::div//span[@class='rts-counter']";

        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "//resources//drivers//chromedriver.exe");
        ChromeOptions ops = new ChromeOptions();
        ops.addArguments("--disable-notifications");
        ops.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
        driver = new ChromeDriver(ops);

        driver.get("https://www.worldometers.info/world-population/");
        driver.manage().window().maximize();
        Thread.sleep(30000);
        int count = 1;
        while (count<=20){

            System.out.println("============================================");
            System.out.println(count+" Sec");
            System.out.println("*************Total Population*************");
            getCount(strTotalPopulation);

            System.out.println("*************Sub Population*************");
            getCount(strSubDetails);

            Thread.sleep(1000);
            count++;
        }




    }

    public static void getCount(String strXpath){

        List<WebElement> list = driver.findElements(By.xpath(strXpath));

        for (WebElement ele : list){

            System.out.println(ele.getText());


        }
    }


}
