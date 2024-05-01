package Manager;

import static io.restassured.RestAssured.oauth2;
import static io.restassured.RestAssured.requestSpecification;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.LogConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import org.PetShop.PropertyManager;

public class Api {
    private static Api apiManager;

    private Api() {

    }

    public static Api getInstance() {
        if (apiManager == null) {
            apiManager = new Api();
            return apiManager;
        } else {
            return apiManager;
        }
    }

    public void setRequestSpec(String token) {
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecification = requestSpecBuilder.setBaseUri(PropertyManager.getInstance().get_baseUrl())
                .setBasePath("/v2")
                .setContentType(ContentType.JSON)
                .setAuth(
                        oauth2(token)
                )
                .setConfig(RestAssuredConfig.config().logConfig(LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails(
                        LogDetail.ALL
                ))).build();
    }
}