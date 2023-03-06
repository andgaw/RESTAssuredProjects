package httpMethods.serialization;

import io.restassured.RestAssured;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pojo.Category;
import pojo.Pet;
import pojo.Tag;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;


import java.util.Collections;

public class SerializationAndDeserializationTests {

    @BeforeClass
    public void setupConfiguration(){
        RestAssured.baseURI  = "https://swaggerpetstore.przyklady.javastart.pl";
        RestAssured.basePath = "v2";
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


        Pet actualPet = given().log().all().body(pet).contentType("application/json")
                .when().post("pet")
                .then().log().all().statusCode(200).extract().as(Pet.class);

        assertEquals(actualPet.getId(), pet.getId(), "Pet id");
        assertEquals(actualPet.getCategory().getId(), pet.getCategory().getId(), "Category");
        assertEquals(actualPet.getPhotoUrls().get(0), pet.getPhotoUrls().get(0), "Photo");
        assertEquals(actualPet.getTags().get(0).getName(), pet.getTags().get(0).getName(), "Pet Tag");
    }

    @Test
    public void givenExistingPetIdWhenGetPetThenReturnPetTest() {


        Pet pet = new Pet();
        Category category = new Category();
        Tag tag = new Tag();

        pet.setId(5);
        pet.setCategory(category);
        pet.setName("Gugu");
        tag.setId(5);
        pet.setPhotoUrls(Collections.singletonList("http://photos.com/gugu.jpg"));
        pet.setTags(Collections.singletonList(tag));
        category.setId(1);
        tag.setName("dogs-category");
        pet.setStatus("availble");


        Pet gugu = given().log().all().body(pet).contentType("application/json")
                .when().post("pet")
                .then().log().all().statusCode(200).extract().as(Pet.class);



        given().log().method().log().uri()
                .pathParam("petId", 5)
                .when().get("pet/{petId}")
                .then().log().all().statusCode(200);
        assertEquals(gugu.getName(), pet.getName(), "Name");
        assertEquals(gugu.getId(),pet.getId(),"Pet Id");
        assertEquals(gugu.getPhotoUrls(),pet.getPhotoUrls(), "Pet Photo");

    }

}
