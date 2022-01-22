package ru.netology.web;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderTest {
    private WebDriver driver;

    @BeforeAll
    public static void setUpAll() {
        System.setProperty("webdriver.chrome.driver", "./driver/chromedriver");
    }

    @BeforeEach
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
    }
    @AfterEach
    public void tearDown() {
        driver.quit();
        driver = null;
    }
    @Test
    public void shouldOrderFindByClass() {
        driver.get("http://localhost:9999");
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Василий");
        elements.get(1).sendKeys("+79270000000");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.className("Success_successBlock__2L3Cw")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }
    @Test
    public void shouldOrderFindBySelector() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("input[name='name']")).sendKeys("Василий");
        driver.findElement(By.cssSelector("input[name='phone']")).sendKeys("+79270000000");
        driver.findElement(By.cssSelector("span[class='checkbox__box']")).click();
        driver.findElement(By.cssSelector("button")).click();
        String text = driver.findElement(By.cssSelector("p[data-test-id='order-success']")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }
    @Test
    public void shouldValidationName() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("input[name='name']")).sendKeys("777");
        driver.findElement(By.cssSelector("button")).click();
        List<WebElement> elements = driver.findElements(By.className("input__sub"));
        String text = elements.get(0).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }
    @Test
    public void shouldValidationPhone() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("input[name='name']")).sendKeys("Вася Иванов");
        driver.findElement(By.cssSelector("input[name='phone']")).sendKeys("+7927");
        driver.findElement(By.cssSelector("button")).click();
        List<WebElement> elements = driver.findElements(By.className("input__sub"));
        String text = elements.get(1).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }
}
