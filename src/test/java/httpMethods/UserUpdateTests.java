package httpMethods;

import org.testng.annotations.Test;
import pojo.User;

import static io.restassured.RestAssured.given;

public class UserUpdateTests {
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
                .when().post("https://swaggerpetstore.przyklady.javastart.pl/v2/user")
                .then().log().all().statusCode(200);

        user.setFirstName("Andy");
        user.setLastName("Anderson");
        given().body(user).log().all().contentType("application/json")
                .when().put("https://swaggerpetstore.przyklady.javastart.pl/v2/user/seconduser")
                .then().log().all().statusCode(200);

    }
}
