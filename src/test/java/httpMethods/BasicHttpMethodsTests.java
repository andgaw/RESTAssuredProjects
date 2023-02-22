package httpMethods;

import pojo.Category;
import pojo.Pet;
import pojo.Tag;
import org.testng.annotations.Test;

import java.util.Collections;

import static io.restassured.RestAssured.given;

public class BasicHttpMethodsTests {
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

        given().log().all().body(pet).contentType("application/json")
                .when().post("https://swaggerpetstore.przyklady.javastart.pl/v2/pet")
                .then().log().all().statusCode(200);

    }

    @Test
    public void givenExistingPetIdWhenGetPetThenReturnPetTest() {

        given().log().uri().log().method()
                .pathParams("username", "firstuser")
                .when().get("https://swaggerpetstore.przyklady.javastart.pl/v2/user/{username}")
                .then().log().all().statusCode(200);

        given().log().uri().log().method()
                .pathParams("username", "firstuser")
                .when().delete("https://swaggerpetstore.przyklady.javastart.pl/v2/user/{username}")
                .then().log().all().statusCode(200);


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
        tag.setName("dogs-category");
        pet.setStatus("availble");


        given().log().all().body(pet).contentType("application/json")
                .when().post("https://swaggerpetstore.przyklady.javastart.pl/v2/pet")
                .then().log().all().statusCode(200);



        given().log().all().body(pet).contentType("application/json")
                .when().delete("https://swaggerpetstore.przyklady.javastart.pl/v2/pet/1")
                .then().log().all().statusCode(200);
    }
}

