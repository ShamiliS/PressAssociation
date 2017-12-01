package runner.TestNG;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;

@CucumberOptions(features = "feature/playlist.feature", glue = "")
public class Playlist extends AbstractTestNGCucumberTests {

}
