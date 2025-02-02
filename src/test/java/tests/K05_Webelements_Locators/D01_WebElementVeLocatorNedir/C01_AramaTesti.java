package tests.K05_Webelements_Locators.D01_WebElementVeLocatorNedir;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
public class C01_AramaTesti {
    public static void main(String[] args) throws InterruptedException {

        //1- Bir test class’i olusturun ilgili ayarlari yapin
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        //2- https://www.testotomasyonu.com adresine gidin
        driver.get("https://www.testotomasyonu.com");

        //3- urun arama kutusunu locate edin ve kaydedin
        /*
                Kodlarimiz ile reel browser'daki bir webelement'i kullanabilmek icin
                once o webelementi kodlarimizla tanimlayip
                bir obje olarak class'da kaydetmeliyiz
         */

        WebElement aramaKutusu = driver.findElement(By.id("global-search"));

        //4- arama kutusuna “shoe” yazdirin
        aramaKutusu.sendKeys("shoe");

        //5- arama islemini yapabilmek icin ENTER tusuna basin
        aramaKutusu.submit();

        Thread.sleep(2000);

        //6- sayfayi kapatin
        driver.quit();



    }
}
