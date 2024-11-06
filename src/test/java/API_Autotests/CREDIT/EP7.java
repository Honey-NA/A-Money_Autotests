package API_Autotests.CREDIT;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import java.util.concurrent.TimeUnit;

public class EP7 {
    String token;

    //Валидация токена
    @Test (priority = 1)
    public void ValidateToken() {
        Response response = given().header("Content-Type", "application/json")
                .body("{\n" + " \"username\": \"88899946546\",\n" + " \"password\": \"Qwer123!\"\n" + "}")
                .when().post("http://172.17.1.23:30765/api/v1/auth/login");
        Assert.assertEquals(response.statusCode(), 200);
        response.prettyPrint();
        token = response.body().jsonPath().getString("accessToken");
    }

    //проверка EP
    @Test(priority = 2)
    public void getDocumentList200() {
        Response response = given().header("Content-Type", "application/json")
                .header("Authorization", "Bearer "+ token)
                .when().get("http://172.17.1.23:30765/api/v1/credits/product-document-type/91111377-8492-4011-b0dd-37a770456025");
        Assert.assertEquals(response.statusCode(), 200);
        response.prettyPrint();
    }

    //проверка EP с неправильным токеном
    @Test(priority = 3)
    public void getDocumentList403WrongToken() {
        Response response = given().header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token + "123")
                .when().get("http://172.17.1.23:30765/api/v1/credits/product-document-type/91111377-8492-4011-b0dd-37a770456025");
        Assert.assertEquals(response.statusCode(), 403);
        response.prettyPrint();
    }

    //проверка EP без токена
    @Test(priority = 4)
    public void getDocumentList403NoToken() {
        Response response = given().header("Content-Type", "application/json")
                .when().get("http://172.17.1.23:30765/api/v1/credits/product-document-type/91111377-8492-4011-b0dd-37a770456025");
        Assert.assertEquals(response.statusCode(), 403);
        response.prettyPrint();
    }

    //проверка EP c неправильным id продукта
    @Test(priority = 5)
    public void getDocumentList500() {
        Response response = given().header("Content-Type", "application/json")
                .header("Authorization", "Bearer "+ token)
                .when().get("http://172.17.1.23:30765/api/v1/credits/product-document-type/91111377-8492-4011-b0dd-37a770456025123");
        Assert.assertEquals(response.statusCode(), 500);
        response.prettyPrint();
    }

    //проверка EP c неправильным URL
    @Test(priority = 6)
    public void getDocumentList404() {
        Response response = given().header("Content-Type", "application/json")
                .header("Authorization", "Bearer "+ token)
                .when().get("http://172.17.1.23:30765/api/v1/credits/product-document-type123/91111377-8492-4011-b0dd-37a7704560251");
        Assert.assertEquals(response.statusCode(), 404);
        response.prettyPrint();
    }

    //проверка EP с просроченным токеном (время жизни токена 5 минут)
    @Test(priority = 7)
    public void getDocumentList403ExpiredToken() throws InterruptedException {
        TimeUnit.MINUTES.sleep(5);
        Response response = given().header("Content-Type", "application/json")
                .header("Authorization", "Bearer "+ token)
                .when().get("http://172.17.1.23:30765/api/v1/credits/product-document-type/91111377-8492-4011-b0dd-37a770456025");
        Assert.assertEquals(response.statusCode(), 403);
        response.prettyPrint();
    }
}
//123123
//123123123123