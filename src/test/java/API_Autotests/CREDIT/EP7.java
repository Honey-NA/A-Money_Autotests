package API_Autotests.CREDIT;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;

public class EP7 {
    String token;
    @Test (priority = 1)
    public void ValidateToken() {
        Response response = given().header("Content-Type", "application/json")
                .body("{\n" + " \"username\": \"88899946546\",\n" + " \"password\": \"Qwer123!\"\n" + "}")
                .when().post("http://172.17.1.23:30765/api/v1/auth/login");
        Assert.assertEquals(response.statusCode(), 200);
        response.prettyPrint();
        token = response.body().jsonPath().getString("accessToken");
        System.out.println(token);
    }

    @Test(priority = 2)
    public void getUserProfileData() {
            Response response = given().header("Content-Type", "application/json")
                    .header("Authorization", "Bearer "+ token)
                    .when().get("http://172.17.1.23:30765/api/v1/credits/product-document-type/91111377-8492-4011-b0dd-37a770456025");
            Assert.assertEquals(response.statusCode(), 200);
            response.prettyPrint();
    }
}
