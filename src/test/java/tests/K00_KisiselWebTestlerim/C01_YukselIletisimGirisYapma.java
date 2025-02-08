package tests.K00_KisiselWebTestlerim;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import utilities.ReusableMethods;
import utilities.TestBase_Each;

public class C01_YukselIletisimGirisYapma extends TestBase_Each {

    @Test
    public void test1(){
        driver.get("https://yukseliletisim.com");
        ReusableMethods.bekle(2);
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
        System.out.println("Başarılı!");
    }
}
