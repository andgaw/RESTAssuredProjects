package httpMethods;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pojo.Category;
import pojo.Pet;
import pojo.Tag;
import pojo.User;

import java.util.Collections;

import static io.restassured.RestAssured.given;
import static org.testng.AssertJUnit.assertEquals;


public class BasicHttpMethodsTests {
    @BeforeClass
    public void setupConfiguration(){
        RestAssured.baseURI  = "https://swaggerpetstore.przyklady.javastart.pl";
        RestAssured.basePath = "v2";
        RestAssured.filters(new RequestLoggingFilter(),new ResponseLoggingFilter());
    }



    @Test
    public void givenPetWhenPostPetThenPetIsCreatedTest() {
        Category category = new Category();
        category.setId(1);
        category.setName("dogs");

        Tag tag = new Tag();
        tag.setId(1);
        tag.setName("dogs-category");

        Pet pet = new Pet();
        pet.setId(123);
        pet.setCategory(category);
        pet.setPhotoUrls(Collections.singletonList("http://photos.com/dog1.jpg"));
        pet.setTags(Collections.singletonList(tag));
        pet.setStatus("availble");

        given().body(pet).contentType("application/json")
                .when().post("pet")
                .then().statusCode(200);

    }





    @Test
    public void givenExistingPetWhenUpdatePetNameThenPetIsChangedTest() {

        Pet pet = new Pet();
        Category category = new Category();
        Tag tag = new Tag();

        pet.setId(1);
        pet.setCategory(category);
        pet.setName("Logan");
        tag.setId(1);
        pet.setPhotoUrls(Collections.singletonList("http://photos.com/dog1.jpg"));
        pet.setTags(Collections.singletonList(tag));
        category.setId(1);
        tag.setId(1);
        tag.setName("dogs-category");
        pet.setStatus("availble");


        given().body(pet).contentType("application/json")
                .when().post("pet")
                .then().statusCode(200);


        given().body(pet).contentType("application/json")
                .when().delete("pet/1")
                .then().statusCode(200);
    }


    @Test
    public void givenCorrectUserDataWhenCreateUserThenUserIsCreatedTest() {
        User user = new User();
        user.setId(445);
        user.setUsername("firstuser");
        user.setFirstName("Krzysztof");
        user.setLastName("Kowalski");
        user.setEmail("krzysztof@test.pl");
        user.setPassword("password");
        user.setPhone("+123456789");
        user.setUserStatus(123);

        given().contentType("application/json")
                .body(user)
                .when().post("user")
                .then().statusCode(200);
        String newUser =
                given().contentType("application/json")
                        .pathParams("username", user.getUsername())
                        .when().get("user/{username}")
                        .then().statusCode(200).extract().jsonPath().getString(user.getFirstName());
        assertEquals(user.getFirstName(), "Krzysztof");
    }

    @Test
    public void givenExistingPetIdWhenGetPetThenReturnPetTest() {

        given().pathParams("username", "firstuser")
                .when().get("user/{username}")
                .then().statusCode(200);

        given().pathParams("username", "firstuser")
                .when().delete("user/{username}")
                .then().statusCode(200);
}}

