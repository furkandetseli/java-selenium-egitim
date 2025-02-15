package tests.K00_KisiselWebTestlerim;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.NoSuchElementException;
import utilities.ReusableMethods;
import utilities.TestBase_Each;
import org.openqa.selenium.*;

import org.openqa.selenium.support.ui.Select;
import java.util.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.*;
import java.net.*;
import java.nio.file.*;

public class C03_HepsiburadadanYukseliletisimeÜrünEkleme extends TestBase_Each {
    @Test
    public void test01() throws InterruptedException, IOException {
        // Excel dosyasına erişim
        String excelDosyaYolu = "src/test/java/tests/K00_KisiselWebTestlerim/urunler.xlsx";
        FileInputStream fileInputStream = new FileInputStream(excelDosyaYolu);
        Workbook workbook = WorkbookFactory.create(fileInputStream);
        Sheet sayfa1 = workbook.getSheet("Sayfa1");

        // Excel'deki mevcut ürün başlıklarını bir listeye al
        List<String> mevcutUrunler = new ArrayList<>();
        for (int i = 0; i <= sayfa1.getLastRowNum(); i++) {
            Row row = sayfa1.getRow(i);
            if (row != null && row.getCell(0) != null) {
                mevcutUrunler.add(row.getCell(0).getStringCellValue());
            }
        }

        System.out.println(mevcutUrunler);

        // Hepsiburada'dan ürün sayfasına git
        String url = "https://www.hepsiburada.com/nimmy-cep-telefonu-aksesuarlari-xc-371967-b59909";
        driver.get(url);
        Thread.sleep(3000); // Sayfanın yüklenmesi için bekle

        // Ana pencerenin handle değerini al
        String mainWindowHandle = driver.getWindowHandle();

        // Yükseliletisimi yeni bir pencerede açmak
        ((JavascriptExecutor) driver).executeScript("window.open('https://yukseliletisim.com/');");
        ReusableMethods.bekle(2); // Yükseliletisim sayfasının yüklenmesi için bekle

        // Yeni pencereye geçiş yap
        Set<String> windowHandles = driver.getWindowHandles();
        for (String handle : windowHandles) {
            if (!handle.equals(mainWindowHandle)) {
                driver.switchTo().window(handle);
                break;
            }
        }

        // Yükseliletisim sayfasında işlemleri yap
        driver.findElement(By.xpath("/html/body/header/div/div[3]/a[2]")).click();
        ReusableMethods.bekle(2);
        WebElement login = driver.findElement(By.id("email"));
        login.sendKeys("detseli@hotmail.com");
        ReusableMethods.bekle(1);
        WebElement password = driver.findElement(By.id("password"));
        password.sendKeys("123Parola.");
        ReusableMethods.bekle(1);
        WebElement button = driver.findElement(By.xpath("/html/body/main/div/div/div/form/div[3]/label/input"));
        button.click();
        ReusableMethods.bekle(1);
        driver.findElement(By.xpath("/html/body/main/div/div/div/form/button")).click();
        ReusableMethods.bekle(1);
        String adminpanel = "https://yukseliletisim.com/admin/";
        driver.get(adminpanel);
        ReusableMethods.bekle(1);
        driver.findElement(By.xpath("/html/body/div/aside/nav/ul/li[2]/a")).click();
        System.out.println("Başarılı!");

        String yukselWindowHandle = driver.getWindowHandle();

        // Ana pencereye geri dön
        driver.switchTo().window(mainWindowHandle);
        Thread.sleep(2000);

        // Ürünleri listele
        List<WebElement> urunler = driver.findElements(By.className("productListContent-zAP0Y5msy8OHn5z7T_K_"));

        // Bütün ürünleri tek tek tıklayarak verileri çek
        for (int i = 0; i < urunler.size(); i++) {
            List<WebElement> urunlerGuncel = driver.findElements(By.className("productListContent-zAP0Y5msy8OHn5z7T_K_")); // Listeyi güncelle
            WebElement urun = urunlerGuncel.get(i);
            urun.click(); // Yeni sekmede açılması gerekebilir

            // Yeni açılan sekmeyi bul ve ona geç
            Set<String> newWindowHandles = driver.getWindowHandles();
            for (String handle : newWindowHandles) {
                if (!handle.equals(mainWindowHandle) && !handle.equals(yukselWindowHandle)) {
//                if (!handle.equals(mainWindowHandle)) {
                        driver.switchTo().window(handle);
                        break;

                }
            }

            // Ürün penceresinin handle değerini al
            String currentWindowHandle = driver.getWindowHandle();
            Thread.sleep(1000); // Sayfa yüklenmesi için bekle

            try {
                // Ürün başlığını al
                String baslik = driver.findElement(By.tagName("h1")).getText();
                System.out.println("Ürün: " + baslik);

                // Eğer ürün daha önce eklenmişse, atla
                if (mevcutUrunler.contains(baslik)) {
                    System.out.println("Bu ürün zaten eklenmiş: " + baslik);
                    driver.close();
                    driver.switchTo().window(mainWindowHandle);
                    continue; // Aynı ürünü eklememek için bir sonraki ürüne geç
                }
                ReusableMethods.bekle(2);
                // İndirimli fiyat kontrolü
                WebElement indirimliFiyatElementi = null;
                try {
                    indirimliFiyatElementi = driver.findElement(By.cssSelector("div.z7kokklsVwh0K5zFWjIO.nUoGYtEwzHyrjX2lvABI span"));
                } catch (NoSuchElementException e) {
                    // Eğer indirimli fiyat yoksa hata alınacaktır, bu durumda indirimsiz fiyatı alacağız
                }
                ReusableMethods.bekle(2);
                String fiyat = "";
                if (indirimliFiyatElementi != null) {
                    // İndirimli fiyatı al
                    fiyat = indirimliFiyatElementi.getText();
                } else {
                    // İndirimli fiyat yoksa, indirimsiz fiyatı al
                    WebElement standartFiyatElementi = driver.findElement(By.cssSelector("div.z7kokklsVwh0K5zFWjIO span"));
                    fiyat = standartFiyatElementi.getText();
                }
                ReusableMethods.bekle(2);
                // Fiyatı temizle: virgülden sonrası, TL ve nokta kaldır
                fiyat = fiyat.split(",")[0];  // Virgülden sonrasını almaz
                fiyat = fiyat.replace("TL", "").replace(".", "");  // TL'yi ve noktayı kaldır
                ReusableMethods.bekle(2);
                System.out.println(fiyat);

//                WebElement descriptionElement = driver.findElement(By.cssSelector(".sf-ProductDescription-ELfL1tuePGs5IVj8XHGy p"));
//                String description = descriptionElement.getText();
//                System.out.println(description);

                ReusableMethods.bekle(2);
                driver.switchTo().window(yukselWindowHandle);
                System.out.println("yeni sayfa");
                ReusableMethods.bekle(1);

                driver.findElement(By.xpath("/html/body/div/main/div/div[1]/a")).click();
                ReusableMethods.bekle(1);

                WebElement urunadi = driver.findElement(By.xpath("//*[@id=\"name\"]"));
                urunadi.sendKeys(baslik);
                ReusableMethods.bekle(2);

                WebElement categorydropdown = driver.findElement(By.id("category"));
                Select cselect = new Select(categorydropdown);
                cselect.selectByVisibleText("KAPAK & KILIF");  // "Kapak & Kılıf" seçilir
                ReusableMethods.bekle(2);
                System.out.println("kategori seçildi");

                String brand = driver.findElement(By.xpath("//h1[@data-test-id='title']/a")).getText();
                System.out.println("Marka: " + brand);  // Marka adını ekrana yazdır
                WebElement branddropdown = driver.findElement(By.id("brand"));
                Select bselect = new Select(branddropdown);
                bselect.selectByVisibleText("Nimmy");
                System.out.println("marka seçildi");
                ReusableMethods.bekle(2);


//                WebElement descriptionbar = driver.findElement(By.xpath("//*[@id=\"description\"]"));
//                descriptionbar.sendKeys(description);
//                ReusableMethods.bekle(2);

                driver.findElement(By.xpath("//*[@id=\"productForm\"]/div[1]/button[3]")).click();
                ReusableMethods.bekle(1);

                WebElement pricebar = driver.findElement(By.xpath("//*[@id=\"price\"]"));
                pricebar.sendKeys(fiyat);
                ReusableMethods.bekle(2);

                driver.findElement(By.xpath("//*[@id=\"productForm\"]/div[1]/button[4]"));
                WebElement stockbar = driver.findElement(By.xpath("//*[@id=\"stock\"]"));
                stockbar.sendKeys("1");
                ReusableMethods.bekle(2);

                driver.findElement(By.xpath("//*[@id=\"productForm\"]/div[1]/button[2]"));
                ReusableMethods.bekle(1);
                driver.switchTo().window(currentWindowHandle);
                ReusableMethods.bekle(1);
                List<WebElement> dots = driver.findElements(By.cssSelector("li[id^='pdp-carousel_dots']"));

                // Görsel URL'sini al
                String imageUrl = "";
                for (int a = 0; a < dots.size(); a++) {
                    // Ürün resmini al
                    List<WebElement> imageElements = driver.findElements(By.cssSelector("li[id^='pdp-carousel__slide'] img.i9jTSpEeoI29_M1mOKct"));
                    WebElement img = imageElements.get(a);
                    imageUrl = img.getAttribute("src");
                    String altText = img.getAttribute("alt");

                    //"Peşin fiyatına" içerenleri atla
                    if (altText != null && altText.contains("Peşin fiyatına")) {
                        continue; // Bu görseli atla
                    }

                    driver.get(imageUrl);
                    ReusableMethods.bekle(1);
                    String saveDir = "src/test/java/tests/K00_KisiselWebTestlerim/gorsel/";  // Kaydedilecek dizini belirleyin
                    String  fileName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);  // Dosya adını URL'den çıkar
                    String savePath = saveDir + fileName;  // Dosyanın tam yolu
                    downloadImage(imageUrl,savePath);
                    ReusableMethods.bekle(1);
                    driver.navigate().back();
                    ReusableMethods.bekle(1);
                    driver.findElement(By.xpath("//*[@id=\"pdp-carouselContainer\"]/div[1]/div[2]")).click();
                    ReusableMethods.bekle(1);
                    driver.switchTo().window(yukselWindowHandle);
                    // buraya görseli eklicez
                    WebElement fileInput = driver.findElement(By.name("images"));

                    // Dosya yolunu gönder (çoklu dosya yükleme desteği var)
                    fileInput.sendKeys(savePath);
                    driver.switchTo().window(currentWindowHandle);
                    ReusableMethods.bekle(1);
                }

                // Excel dosyasına yazma işlemi
                int ilkBosSatirIndex = sayfa1.getLastRowNum() + 1;
                Row newRow = sayfa1.getRow(ilkBosSatirIndex);
                if (newRow == null) {
                    newRow = sayfa1.createRow(ilkBosSatirIndex); // Satır yoksa oluştur
                }
                newRow.createCell(0).setCellValue(baslik);
                newRow.createCell(1).setCellValue(fiyat);
                // Başlıkları ekledikten sonra listeye kaydet

                mevcutUrunler.add(baslik);
                FileOutputStream fileOutputStream = new FileOutputStream(excelDosyaYolu);
                workbook.write(fileOutputStream);

                driver.switchTo().window(yukselWindowHandle);
                ReusableMethods.bekle(1);
                driver.findElement(By.xpath("//*[@id=\"productForm\"]/div[3]/button")).click();
                driver.switchTo().window(currentWindowHandle);
                ReusableMethods.bekle(1);

                System.out.println("Ürün başarıyla kaydedildi: " + baslik + " " +fiyat);

            } catch (Exception e) {
                System.out.println("Ürün bilgileri alınamadı!");
            }

            // Açılan pencereyi kapat
            driver.close();

//            driver.switchTo().window(yukselWindowHandle);

            // Eski pencereye geri dön
            driver.switchTo().window(mainWindowHandle);
            Thread.sleep(2000); // Ana sayfaya dönerken bekleme ekleyelim
        }

        // Dosyayı kaydet
        FileOutputStream fileOutputStream = new FileOutputStream(excelDosyaYolu);
        workbook.write(fileOutputStream);

        // Dosyaları kapat
        fileInputStream.close();
        fileOutputStream.close();
        workbook.close();

        driver.quit();
    }

    public void downloadImage(String imageUrl, String savePath) throws IOException {
        // URL'den resmi indir
        URL url = new URL(imageUrl);
        InputStream in = url.openStream();
        Files.copy(in, Paths.get(savePath), StandardCopyOption.REPLACE_EXISTING);
        in.close();
    }
}
