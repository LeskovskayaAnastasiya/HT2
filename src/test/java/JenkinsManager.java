import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;


public class JenkinsManager {

    private WebDriverWait wait;
    private final WebDriver driver;

    // Подготовка элементов страницы.
    @FindBy(xpath = "//body")
    private WebElement body;

    @FindBy(xpath = "//form[@name='login']")
    private WebElement formForLogin;

    @FindBy(xpath = "//input[@id='j_username']")
    private WebElement j_username;

    @FindBy(xpath = "//input[@name='j_password']")
    private WebElement password;

    @FindBy(xpath = "//button[@id='yui-gen1-button']")
    private WebElement logInButton;

    @FindBy(xpath = "//*[@id='tasks']/div[4]/a")
    private WebElement manageJenkinsLink;

    @FindBy(xpath = "//*[@name='username']")
    private WebElement username;

    @FindBy(xpath =  "//*[@name='fullname']")
    private WebElement fullName;

    @FindBy(xpath = "//*[@name='password1']")
    private WebElement passwordUserCrateForm;

    @FindBy(xpath = "//*[@name='password2']")
    private WebElement confirmPassword;

    @FindBy(xpath = "//*[@name='email']")
    private WebElement  eMailAddress;

    @FindBy(xpath = "//*[@id='yui-gen4-button']")
    private WebElement createUserButton;

    @FindBy(xpath = "//a[@href=\"user/someuser/delete\"]")
    private WebElement deleteUser;

    @FindBy(xpath = "//*[@name='delete']")
    private WebElement user_message;

    @FindBy(xpath = "//*[@id='yui-gen4-button']")
    private WebElement yesButton;

    @FindBy(xpath = " //*[@id=\"people\"]/tbody/tr/td")
    //@FindBy(xpath = " //td")
    private WebElement table;

    @FindBy(xpath = "//a[@title='Manage Users']/dl/dt")
    private WebElement tableDt;

    @FindBy(xpath =  "//a[@title='Manage Users']/dl/dd")
    private WebElement tableDd;

    @FindBy(xpath =  "//a[@title='Manage Users']")
    private WebElement manageUsersLink;




    public JenkinsManager(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(this.driver, 30);

        // Провекрка того факта, что мы на верной странице.
        if ((!driver.getTitle().equals("Jenkins")) ||
                (!driver.getCurrentUrl().equals("http://localhost:8081/login?from=%2F"))) {
            throw new IllegalStateException("Wrong site page!");
        }
    }


    // Заполнение имени.
    public JenkinsManager setName(String value) {
        j_username.sendKeys(value);
        return this;
    }

    // Заполнение пароля
    public JenkinsManager setPassword(String value) {
        password.sendKeys(value);
        return this;
    }

    // заполнение формы данными
    public JenkinsManager setNameUserCreateForm(String value) {
        username.sendKeys(value);
        return this;
    }

    public JenkinsManager setPasswordUserCreateForm(String value) {
        passwordUserCrateForm.sendKeys(value);
        return this;
    }

    public JenkinsManager setConfirmPasswordUserCreateForm(String value) {
        confirmPassword.sendKeys(value);
        return this;
    }

    public JenkinsManager setFullNameUserCreateForm(String value) {
        fullName.sendKeys(value);
        return this;
    }

    public JenkinsManager setEmailAddressUserCreateForm(String value) {
        eMailAddress.sendKeys(value);
        return this;
    }

    //Отправка данных из формы.
    public JenkinsManager submitForm() {
        logInButton.click();
        return this;
    }

    public JenkinsManager createUserButtonClick() {
        createUserButton.click();
        return this;
    }

    public JenkinsManager deleteUserClick() {
        deleteUser.click();
        return this;
    }

    public JenkinsManager yesButtonClick() {
        yesButton.click();
        return this;
    }

    //методы перехода по ссылкам
    //переход по ссылке «Manage Jenkins»
    public JenkinsManager goManageJenkinsLink() {
        manageJenkinsLink.click();
        return this;
    }

    public String getManageUserTextDT() {
        return tableDt.getText();
    }

    public String getManageUserTextDD() {
        return tableDd.getText();
    }


    // переход по ссылке Manage Users
    public JenkinsManager goManageUsersLink() {
        manageUsersLink.click();
        return this;
    }

    // проверка существует ли ссылка CreateUser
    public boolean isCreateUserLinkDisplayed() {
        boolean result;
        if (driver.findElement(By.xpath("//a[@href='addUser']")).isDisplayed()){
            result = true;
        }else {
            result = false;
        }
        return result;
    }

    //переход по ссылке CreateUser
    public JenkinsManager goCreateUserLink() {
        driver.findElement(By.xpath("//a[@href='addUser']")).click();
        return this;
    }

    public boolean isLink1Real() {
        if(!driver.findElements(By.xpath("//a[@href=\"user/someuser/delete\"]")).isEmpty()){
            return true;
        }else{
            return false;
        }
    }

    public boolean isLink2Real() {
        if(!driver.findElements(By.xpath("//a[@href=\"user/admin/delete\"]")).isEmpty()){
            return true;
        }else{
            return false;
        }
    }

    // Проверка существования формы с тремя полями типа text и двумя полями типа password,
    //все поля должны быть пустыми.
    public Boolean isFormReal() {

        int countTypeText = 0;
        int countPasswordText = 0;

        Collection<WebElement> forms = driver.findElements(By.tagName("form"));
        if (forms.isEmpty()) {
            return false;
        }
        Iterator<WebElement> i = forms.iterator();
        boolean form_found = false;
        WebElement form = null;
        while (i.hasNext()) {
            form = i.next();
            List<WebElement> inputs = form.findElements(By.tagName("input"));
            for (WebElement input : inputs) {
                if (input.getAttribute("type").equalsIgnoreCase("text") &&
                        (input.getAttribute("value").equals(""))) {
                    countTypeText += 1;
                } else if (input.getAttribute("type").equalsIgnoreCase("password") &&
                        input.getAttribute("value").equals("")) {
                    countPasswordText += 1;
                }
                if ((countTypeText == 3) && (countPasswordText == 2)){
                    form_found = true;
                    return form_found;
                }
            }
        }
        return form_found;
    }

    public String getName() {
        return j_username.getAttribute("value");
    }

    public String getPassword() {
        return password.getAttribute("value");
    }

    public boolean getFormForLogin() {
        return formForLogin.isDisplayed();
    }

    // извлечение данных из формы
    public String getNameUserCreateForm() {
        return username.getAttribute("value");
    }

    public String getPasswordUserCreateForm() {
        return passwordUserCrateForm.getAttribute("value");
    }

    public String getConfirmPasswordUserCreateForm() {
        return confirmPassword.getAttribute("value");    }

    public String getFullNameUserCreateForm() {
        return fullName.getAttribute("value");
    }

    public String getEmailAddressUserCreateForm() {
        return eMailAddress.getAttribute("value");
    }


    public boolean userMessageEquals(String search_string) {
        return user_message.getText().equals(search_string);
    }

    public boolean tableContainsText(String searchString) {
        List <WebElement> webElements =  table.findElements(By.xpath("//a"));
        for (WebElement web : webElements){
            if (web.getText().equals(searchString)){
                return true;
            }
        }
        return false;
    }

    public boolean pageTextContains(String search_string) {
        return body.getText().contains(search_string);
    }

    public String getErrorOnTextAbsence(String search_string) {
        if (!pageTextContains(search_string)) {
            return "No '" + search_string + "' is found inside page text!\n";
        } else {
            return "";
        }
    }
}