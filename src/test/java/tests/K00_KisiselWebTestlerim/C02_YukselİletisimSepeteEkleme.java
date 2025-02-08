package tests.K00_KisiselWebTestlerim;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import utilities.ReusableMethods;
import utilities.TestBase_Each;

import java.util.List;

public class C02_YukselİletisimSepeteEkleme extends TestBase_Each {
    @Test
    public void test01() {
        driver.get("https://yukseliletisim.com");
        ReusableMethods.bekle(2);

        // "Telefon Aksesuar" dropdown menüsünü bul
        WebElement telefonAksesuarMenu = driver.findElement(By.xpath("//a[contains(text(),'TELEFON AKSESUAR')]"));

        // Üzerine gelerek açılır menüyü aktif et
        Actions actions = new Actions(driver);
        actions.moveToElement(telefonAksesuarMenu).perform();
        ReusableMethods.bekle(2);

        // "KAPAK & KILIF" seçeneğini tıkla
        WebElement kapakKilif = driver.findElement(By.xpath("//a[contains(text(),'KAPAK & KILIF')]"));
        kapakKilif.click();

        ReusableMethods.bekle(2);
        List<WebElement> tumurunler = driver.findElements(By.className("product-card"));
        for (int i = 0; i < tumurunler.size(); i++) {
            tumurunler = driver.findElements(By.className("product-card")); // Güncel liste al
            WebElement link = tumurunler.get(i);
            link.click(); // Butona tıkla

            // Sayfanın tekrar yüklenmesini bekle
            ReusableMethods.bekle(2);
            driver.findElement(By.className("add-to-cart-btn")).click();
            System.out.println("🌍 Ürün Sepete Eklendi! " + driver.getCurrentUrl());
            ReusableMethods.bekle(1);
            driver.navigate().back();
        }
        ReusableMethods.bekle(1);
        System.out.println("Tüm ürünler sepete eklendi!");
        driver.findElement(By.xpath("/html/body/header/div/div[3]/a[3]")).click();
        ReusableMethods.bekle(5);

    }

}
