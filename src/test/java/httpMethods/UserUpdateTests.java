package httpMethods;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pojo.User;

import static io.restassured.RestAssured.given;

public class UserUpdateTests {

    @BeforeClass
    public void setupConfiguration(){
        RestAssured.baseURI  = "https://swaggerpetstore.przyklady.javastart.pl";
        RestAssured.basePath = "v2";
    }

    @Test
    public void  givenCorrectUserDataWhenFirstNameLastNameAreUpdatedThenUserDataIsUpdatedTest(){

        User user = new User();

        user.setId(666);
        user.setUsername("seconduser");
        user.setFirstName("Andrew");
        user.setLastName("Rook");
        user.setEmail("Andrew@mail.com");
        user.setPassword("hardpassword");
        user.setPhone("+123456789");
        user.setUserStatus(666);

        given().body(user).log().all().contentType("application/json")
                .when().post("user")
                .then().log().all().statusCode(200);

        user.setFirstName("Andy");
        user.setLastName("Anderson");
        given().body(user).log().all().contentType("application/json")
                .when().put("user/seconduser")
                .then().log().all().statusCode(200);

    }
}
