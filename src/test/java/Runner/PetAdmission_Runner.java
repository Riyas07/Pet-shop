package Runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "Feature",
glue = "Stepdef",tags = "@petAdmission")
public class PetAdmission_Runner extends AbstractTestNGCucumberTests {
}
