package liveProject;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class GitHubProject {    

    RequestSpecification reqSpec;
    String sshKey;
    int id;
    final static String baseURL = "https://api.github.com";

    @BeforeClass
    public void setUp() {
        reqSpec = new RequestSpecBuilder().setContentType(ContentType.JSON)
                .addHeader("Authorization", "token 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")
                .setBaseUri(baseURL).build();
    }

    @Test(priority = 1)
    public void addSSHKey() {
        Response response = given().spec(reqSpec)
                .body("{\r\n" + "   \"title\":\"TestAPIKey\",\r\n"
                + "   \"key\":\"ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQCjGFy4bXKR+d6oHCz7KSZM5fisB9UkpsinfNm1p3v1vDcQkjjQnV6C3vJjcnsSh4EWXBfpQFQ3GDKF3xN05ey/Vjh86Fflhtfzoq/hG7eOAynjlHEpGlJH/eVtO0awQ1NIpZZ4NOHtnrwLMUScKxOW7eplLaAOj7p29O/IFs5b1cHHpGj3Vb1GcfCkRdrQxKfx4A9R6y6ejIgNFK/Dal0KKJCt44UVdBtL07kGQHsFlXA35Fja56JGfQNZZsA0fg/44ChPqF1DPqM1nMv+z0B1xPtGZOF7vnwdw5PWwVoShCKcPjBwM6XNTZc+iQ574plR6ZYCZo9c5V5Bo/vQoNTz\"\r\n"
                + "}")
                .when().post("/user/keys");

        System.out.println("Response for Post: " + response.asPrettyString());
        id = response.then().extract().body().path("id");
        response.then().statusCode(201);
    }

    @Test(priority = 2)
    public void getSSHKey() {
        Response response = given().spec(reqSpec).pathParam("keyId", id)
                .when().get("/user/keys/{keyId}");

        System.out.println("Response for Get: " + response.asPrettyString());
        response.then().statusCode(200);
    }

    @Test(priority = 3)
    public void deleteSSHKey() {
        Response response = given().spec(reqSpec).pathParam("keyId", id)
                .when().delete("/user/keys/{keyId}");

        System.out.println("Response for Delete: " + response.asPrettyString());
        response.then().statusCode(204);
    }
}
