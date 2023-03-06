package respponse;
import java.util.Map;
import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pojo.Category;
import pojo.Pet;
import pojo.Tag;

import java.util.Collections;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.*;

public class GettingHeadersAndCookiesTests {
    @BeforeClass
    public void setupConfiguration() {
        RestAssured.baseURI = "https://swaggerpetstore.przyklady.javastart.pl";
        RestAssured.basePath = "v2";
    }
    @Test

    public void  givenPetWhenPostPetThenPetIsCreatedTest(){
        Category category = new Category();
        category.setId(1);
        category.setName("dogs");

        Tag tag = new Tag();
        tag.setId(1);
        tag.setName("dogs-category");

        Pet pet = new Pet();
        pet.setId(123);
        pet.setCategory(category);
        pet.setPhotoUrls(Collections.singletonList("www.gugu.com/photo.bmp"));
        pet.setTags(Collections.singletonList(tag));
        pet.setStatus("available");

        Response response = given().log().all().body(pet).contentType("application/json")
                .when().post("pet")
                .then().log().all().extract().response();
        int statusCode  = response.getStatusCode();
        String statusLine = response.getStatusLine();
        Headers responseHeaders  = response.getHeaders();
        Map<String, String> cookies = response.getCookies();



        assertEquals(statusLine, "HTTP/1.1 200 OK", "Status line");
        assertEquals(statusCode, 200, "Status code");
        assertNotNull(responseHeaders.get("Date"));
        assertEquals(responseHeaders.get("Content-Type").getValue(), "application/json", "Content type header");
        assertEquals(responseHeaders.get("Server").getValue(), "openresty", "Server header");
        assertTrue(cookies.isEmpty(), "Cookies are empty");

    }
}
