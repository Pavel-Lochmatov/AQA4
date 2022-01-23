package ru.netology.web;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import static org.junit.jupiter.api.Assertions.assertEquals;

import io.github.bonigarcia.wdm.WebDriverManager;

public class OrderTest {
    private WebDriver driver;

    @BeforeAll
    public static void setUpAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999");
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    public void shouldOrderSuccess() {
        driver.findElement(By.cssSelector("input[name='name']")).sendKeys("Василий Петров-Иванов");
        driver.findElement(By.cssSelector("input[name='phone']")).sendKeys("+79270000000");
        driver.findElement(By.cssSelector("span[class='checkbox__box']")).click();
        driver.findElement(By.cssSelector("button")).click();
        String text = driver.findElement(By.cssSelector("p[data-test-id='order-success']")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }

    @Test
    public void shouldValidationName() {
        driver.findElement(By.cssSelector("input[name='name']")).sendKeys("777");
        driver.findElement(By.cssSelector("button")).click();
        String text = driver.findElement(By.cssSelector("span[data-test-id='name']")).findElement(By.cssSelector("span span[class='input__sub']")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }

    @Test
    public void shouldValidationPhone() {
        driver.findElement(By.cssSelector("input[name='name']")).sendKeys("Вася Иванов");
        driver.findElement(By.cssSelector("input[name='phone']")).sendKeys("+7927");
        driver.findElement(By.cssSelector("button")).click();
        String text = driver.findElement(By.cssSelector("span[data-test-id='phone']")).findElement(By.cssSelector("span span[class='input__sub']")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    @Test
    public void shouldValidationCheckbox() {
        driver.findElement(By.cssSelector("input[name='name']")).sendKeys("Вася Иванов");
        driver.findElement(By.cssSelector("input[name='phone']")).sendKeys("+79012345678");
        driver.findElement(By.cssSelector("button")).click();
        String text = driver.findElement(By.cssSelector("label[data-test-id='agreement']")).findElement(By.cssSelector("span[class='checkbox__text']")).getCssValue("color");
        assertEquals("rgba(255, 92, 92, 1)", text);
    }

    @Test
    public void shouldValidationNoName() {
        driver.findElement(By.cssSelector("input[name='phone']")).sendKeys("+79012345678");
        driver.findElement(By.cssSelector("span[class='checkbox__box']")).click();
        driver.findElement(By.cssSelector("button")).click();
        String text = driver.findElement(By.cssSelector("span[data-test-id='name']")).findElement(By.cssSelector("span span[class='input__sub']")).getText();
        assertEquals("Поле обязательно для заполнения", text.trim());

    }

    @Test
    public void shouldValidationNoPhone() {
        driver.findElement(By.cssSelector("input[name='name']")).sendKeys("Вася Иванов");
        driver.findElement(By.cssSelector("span[class='checkbox__box']")).click();
        driver.findElement(By.cssSelector("button")).click();
        String text = driver.findElement(By.cssSelector("span[data-test-id='phone']")).findElement(By.cssSelector("span span[class='input__sub']")).getText();
        assertEquals("Поле обязательно для заполнения", text.trim());

    }


}
