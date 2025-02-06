package tests.K06_JUnitFramework.D02_Annotations;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utilities.ReusableMethods;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import java.time.Duration;
import java.util.List;

@TestMethodOrder(OrderAnnotation.class)
public class C05_YukseliletisimTest_mytest {

    static WebDriver driver;
    static WebDriverWait wait;

    @BeforeAll
    public static void setup() {
        // ChromeDriver yolunu belirt (Eğer sistemde yoksa hata verir)
        System.setProperty("webdriver.chrome.driver", "C:\\path\\to\\chromedriver.exe"); // Windows için
        // System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver"); // Mac/Linux için

        driver = new EdgeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        // WebDriverWait nesnesini oluştur
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterAll // class'in en sonunda 1 kere çalışır
    static void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    @Order(1)
    public void test1() {
        System.out.println("🔄 Sayfa açılıyor...");
        driver.get("https://www.izmirtelefontamir.com");

        // Sayfanın tamamen yüklenmesini bekle
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));

        String expectedUrlIcerik = "yukseliletisim";
        String actualUrl = driver.getCurrentUrl();

        if (actualUrl.contains(expectedUrlIcerik)) {
            System.out.println("✅ URL Testi PASSED: " + actualUrl);
        } else {
            System.out.println("❌ URL Testi FAILED: " + actualUrl);
        }

        ReusableMethods.bekle(2);
    }
    @Test
    @Order(2)
    public void test02() {
        System.out.println("📌 Navbar butonları kontrol ediliyor...");

        // Sayfanın tamamen yüklenmesini bekle
        wait.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete'"));

        // Navbar'daki tüm butonları bul
        List<WebElement> navLinks = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(".nav-links .nav-link")));

        for (int i = 0; i < navLinks.size(); i++) {
            navLinks = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(".nav-links .nav-link"))); // Güncel liste al
            WebElement link = navLinks.get(i);

            System.out.println("🔘 Tıklanıyor: " + link.getText());
            link.click(); // Butona tıkla

            // Sayfanın tekrar yüklenmesini bekle
            wait.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete'"));
            System.out.println("🌍 Yeni URL: " + driver.getCurrentUrl());

            ReusableMethods.bekle(3);
        }

        // "Hemen Ara" butonunu test et
        try {
            WebElement callButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".nav-links .nav-call-btn")));
            System.out.println("📞 Tıklanıyor: " + callButton.getText());
            callButton.click();
        } catch (Exception e) {
            System.out.println("⚠️ 'Hemen Ara' butonu bulunamadı!");
        }
    }

    @Test
    @Order(3)
    public void test03() {
        driver.get("https://yukseliletisim.com");
        System.out.println("📌 Yükseliletişim butonları kontrol ediliyor...");
        ReusableMethods.bekle(2);
        WebElement aramakutusu = driver.findElement(By.className("search-input"));
        aramakutusu.sendKeys("iphone"+ Keys.ENTER);
        ReusableMethods.bekle(2);

        List<WebElement> tumurunler = driver.findElements(By.className("product-card"));
        for (int i = 0; i < tumurunler.size(); i++) {
            tumurunler = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("product-card"))); // Güncel liste al
            WebElement link = tumurunler.get(i);

            System.out.println("🔘 Tıklanıyor: " + link.getText());
            link.click(); // Butona tıkla

            // Sayfanın tekrar yüklenmesini bekle
            wait.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete'"));
            System.out.println("🌍 Yeni URL: " + driver.getCurrentUrl());
            ReusableMethods.bekle(3);
            driver.navigate().back();
        }
    }
}
