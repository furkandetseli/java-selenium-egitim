package tests.K04_WebDriverOlusturmaVeKullanma.D02_farkliDriverKullanimi_driverManageMethodlari;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;

public class C01_FarkliWebDriverKullanma {

    public static void main(String[] args) throws InterruptedException {
        WebDriverManager.edgedriver().setup();
        WebDriver driver = new EdgeDriver();


        driver.get("https://www.testotomasyonu.com");


        Thread.sleep(2000);
        driver.quit();
    }
}
