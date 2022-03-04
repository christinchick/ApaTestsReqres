package reqres;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;

public class ReqresTestsAPI {
    @Test
    void listUsers() {
        String response =
                given()
                        .when()
                        .get("https://reqres.in/api/users?page=2")
                        .then()
                        .statusCode(200)
                        .extract().response().asString();
        System.out.println("Response: " + response);
    }

    @Test
    void registerUnsuccessful() {

        String data = "{ \"email\": \"eve.holt@reqres.in\"}";

        given()
                .contentType(JSON)
                .body(data)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .statusCode(400)
                .body("error", is("Missing password"));
    }

    @Test
    void registerSuccessful() {

        String data = "{\n" +
                "    \"email\": \"eve.holt@reqres.in\",\n" +
                "    \"password\": \"pistol\"\n" +
                "}";

        given()
                .contentType(JSON)
                .body(data)
                .when()
                .post("https://reqres.in/api/register")
                .then()
                .statusCode(200)
                .body("id", is(4))
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }

    @Test
    void singleUser() {
        String response =
        given()
               .when()
               .get("https://reqres.in/api/users/2")
               .then()
               .statusCode(200)
                .body("id", is(2))
               .extract().response().asString();

        System.out.println("Response: " + response);
    }

    @Test
    void notFoundUser() {
        String response =
        get("https://reqres.in/api/users/23")
               .then()
               .statusCode(404)
               .extract().response().asString();

        System.out.println("Response: " + response);

    }
   @Test
   public void deleteUser() throws IOException, InterruptedException {
       int userId = 7;
       String deleteEndpoint = "https://reqres.in/api/users/" + userId;

       var request = HttpRequest.newBuilder()
               .uri(URI.create(deleteEndpoint))
               .header("Content-Type", "application/json")
               .DELETE()
               .build();

       var client = HttpClient.newHttpClient();

       var response = client.send(request, HttpResponse.BodyHandlers.ofString());

       System.out.println(response.statusCode());
       System.out.println(response.body());
        }
}