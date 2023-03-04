package parameters;

import org.testng.annotations.Test;
import pojo.Category;
import pojo.Pet;
import pojo.Tag;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertTrue;

public class QueryParamsTests {
    @Test
    public void givenExistingPetWithStatusSoldWhenGetPetWithSoldStatusThenPetWithStatusIsReturnedTest(){
        Pet gugu = new Pet();
        Category category = new Category();
        Tag tag  = new Tag();

        gugu.setName("Guguś");
        gugu.setId(777);
        gugu.setStatus("sold");
        gugu.setPhotoUrls(Collections.singletonList("www.anyphoto.com/photo.jpg"));
        gugu.setCategory(category);
        gugu.setTags(Collections.singletonList(tag));
        category.setId(1);
        tag.setId(1);
        tag.setName("dogs-category");

        given().body(gugu).log().all().contentType("application/json")
                .when().post("https://swaggerpetstore.przyklady.javastart.pl/v2/pet")
                .then().log().all().statusCode(200);

   Pet[] pets =   given().queryParam("status", "sold").log().all().body(gugu).contentType("application/json")
                .when().get("https://swaggerpetstore.przyklady.javastart.pl/v2/pet/findByStatus")
                   .then().log().all().statusCode(200).extract().as(Pet[].class);

       assertTrue(pets.length > 0, "List of Pets");

    }
}


