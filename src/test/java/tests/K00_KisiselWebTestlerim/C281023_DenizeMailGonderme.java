package tests.K00_KisiselWebTestlerim;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utilities.ReusableMethods;
import utilities.TestBase_Each;
import org.openqa.selenium.JavascriptExecutor;

import java.time.Duration;

public class C281023_DenizeMailGonderme extends TestBase_Each {
    @Test
    public void test01(){
        driver.get("https://www.google.com.tr/");
        ReusableMethods.bekle(1);

        driver.findElement(By.xpath("//*[@id=\"gb\"]/div/div[2]/a")).click();
        ReusableMethods.bekle(1);

        WebElement mail = driver.findElement(By.xpath("//*[@id=\"identifierId\"]"));
        mail.sendKeys("denizicokseviyorum1@gmail.com");
        ReusableMethods.bekle(1);

        driver.findElement(By.xpath("//*[@id=\"identifierNext\"]/div/button/span")).click();
        ReusableMethods.bekle(1);

        WebElement password = driver.findElement(By.xpath("//*[@id=\"password\"]/div[1]/div/div[1]/input"));
        password.sendKeys("123Parola.");
        ReusableMethods.bekle(1);

        driver.findElement(By.xpath("//*[@id=\"passwordNext\"]/div/button/span")).click();
        ReusableMethods.bekle(2);

        //driver.findElement(By.xpath("//*[@id=\"yDmH0d\"]/div[1]/div[1]/div[2]/div/div/div[3]/div/div[2]/div/div/button/span")).click();
        //ReusableMethods.bekle(1);

        //driver.findElement(By.xpath("//*[@id=\"yDmH0d\"]/c-wiz[2]/div/div/div/div/div[2]/button[1]/span")).click();
        //ReusableMethods.bekle(1);

        //driver.findElement(By.xpath("//*[@id=\"yDmH0d\"]/c-wiz[3]/div/div/div/div/div[2]/div[1]/button/span")).click();
        //ReusableMethods.bekle(1);

        driver.get("https://mail.google.com/mail/u/0/#inbox");
        ReusableMethods.bekle(1);

        driver.findElement(By.xpath("/html/body/div[6]/div[3]/div/div[2]/div[1]/div[1]/div/div")).click();
        ReusableMethods.bekle(25);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15)); // 15 saniye bekleme süresi
        WebElement mailToField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\":bd\"]"))); //*[@id=":bd"]
        mailToField.sendKeys("unsalekind@gmail.com");
        ReusableMethods.bekle(1);

        WebElement baslik = driver.findElement(By.xpath("//*[@id=\":7o\"]"));
        baslik.sendKeys("SENİ ÇOK SEVİYORUM!");
        ReusableMethods.bekle(1);

        WebElement mesaj = driver.findElement(By.xpath("//*[@id=\":92\"]"));
        mesaj.sendKeys("SENİ ÇOK SEVİYORUM! BU BENİM KİŞİSEL ÇABAMLA İLK DENEMEM. BUNUN ANLAMLI BİR ŞEY OLMASINI ÇOK İSTEMİŞTİM. BU MESAJ YAZILIM TEST OTOMASYONU İLE GÖNDERİLDİ! DENİZ SENİ ÇOK SEVİYORUM <3");
        ReusableMethods.bekle(5);

        driver.findElement(By.xpath("//*[@id=\":7e\"]")).click();
        ReusableMethods.bekle(5);

        System.out.println("BAŞARILI BİÇİMDE MAİL GÖNDERİLDİ!");
    }
}
