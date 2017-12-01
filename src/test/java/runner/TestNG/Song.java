package runner.TestNG;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;

@CucumberOptions(features = "feature/song.feature", glue = "")
public class Song extends AbstractTestNGCucumberTests{

}
