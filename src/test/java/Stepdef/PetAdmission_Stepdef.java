package Stepdef;

import Manager.Api;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import io.cucumber.java.an.E;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import static io.restassured.RestAssured.given;

import static io.restassured.RestAssured.requestSpecification;

import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import org.PetShop.PetAdmission_pojo;
import org.PetShop.Utils;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.*;

public class PetAdmission_Stepdef {
    private String payload;
    PetAdmission_pojo pojo=new PetAdmission_pojo();
    @Given("genarate token to submit pet admission")
    public void genarate_token_to_submit_pet_admission() {
        Api.getInstance().setRequestSpec(Utils.getInstance().getToken());
    }
    @When("built pet admission payload with following details")
    public void built_pet_admission_payload_with_following_details(io.cucumber.datatable.DataTable dataTable) {
       List<Map<String,String>>datas= dataTable.asMaps(String.class,String.class);
       for (Map<String,String>map:datas)
       {
           for (String key:map.keySet())
           {
               switch (key)
               {
                   case "name":if (!map.get(key).isEmpty())pojo.setName(map.get(key));else throw new RuntimeException("dont have name");
                   break;
                   case "status":if (!map.get(key).isEmpty())pojo.setStatus(map.get(key));else throw new RuntimeException("dont have status");
               }

           }
       }
       pojo.setId(Utils.getInstance().generateRandomNumber());
    }
    @Then("add category into the payload")
    public void add_category_into_the_payload(io.cucumber.datatable.DataTable dataTable) {
        Map<String,Object> category=new LinkedHashMap<>();
        category.put("id",Utils.getInstance().generateRandomNumber());
        List<Map<String,Object>>data=dataTable.asMaps(String.class,Object.class);
        for (Map<String,Object>map:data)
        {
            for (String key:map.keySet())
            {
                switch (key)
                {
                    case "name":if (!map.get(key).toString().isEmpty())category.put(key,map.get(key));else throw  new RuntimeException("dont have the category.name");
                }
            }
        }
        pojo.setCategory(category);
    }
    @Then("add photoUrls into the payload")
    public void add_photo_urls_into_the_payload(io.cucumber.datatable.DataTable dataTable) {
        List<Map<String,String>>data=dataTable.asMaps(String.class,String.class);
        List<String> pu=new ArrayList<>();
        for (Map<String,String>map:data)
        {
            pu.add(map.get("photoUrls"));
        }
        pojo.setPhotoUrls(pu);
    }
    @Then("add the tags into the payload")
    public void add_the_tags_into_the_payload(io.cucumber.datatable.DataTable dataTable) {
       List<Map<String,Object>>tags=new ArrayList<>();
        Map<String,Object>tagMap=null;

        List<Map<String,String>>data=dataTable.asMaps(String.class,String.class);
        for (Map<String,String>map:data)
        {
            tagMap=new LinkedHashMap<>();
            tagMap.put("id",Utils.getInstance().generateRandomNumber());
            tagMap.putAll(map);
            tags.add(tagMap);
        }

        pojo.setTags(tags);
    }
    @And("generate the pet admission request payload")
    public void generateThePetAdmissionRequestPayload() {
        ObjectMapper objectMapper=new ObjectMapper();
        try
        {
          this.payload=  objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(pojo);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    @And("validate the json schema of pet admission request payload")
    public void validateTheJsonSchemaOfPetAdmissionRequestPayload() {
      try
      {
          InputStream schemalocation=new FileInputStream("src/main/resources/PetAdmission_requestPaloadSchema.json");
          ObjectMapper objectMapper=new ObjectMapper();
         JsonNode node= objectMapper.readTree(this.payload);
        Set<ValidationMessage>messages= JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V4).getSchema(schemalocation).validate(node);
         if (messages.isEmpty())
         {
             System.out.println("payload looks is good");
         }
         else {
             for (ValidationMessage v:messages)
             {
                 System.out.print(v.getMessage());
             }
         }
      }
      catch (Exception e)
      {
          e.printStackTrace();
      }
    }
    @Then("execute the pet admission POST request")
    public void execute_the_pet_admission_post_request() {
        try {
            FileOutputStream outputStream=new FileOutputStream("Evidences/PetAdmission.json",true);
            given(requestSpecification)
                    .filter(new RequestLoggingFilter(LogDetail.BODY,new PrintStream(outputStream)))
                    .body(this.payload)
                    .when().post("/pet")
                    .then().assertThat().statusCode(200).extract().response().getBody().asPrettyString();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


}
