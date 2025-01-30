package tests.K04_WebDriverOlusturmaVeKullanma.D01_WebDriverOlusturma_GetveNavigateMethodlari;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;

public class C01_IlkOtomasyon {

    public static void main(String[] args) throws InterruptedException {
        // Bir test class'ı olutusturulduğunda ilk yapilmasi gereken sey
        // Webdriver edinmektir.

        WebDriver driver = new EdgeDriver();

        driver.get("https://www.testotomasyonu.com");

        Thread.sleep(3000);

        driver.close();

    }

}
