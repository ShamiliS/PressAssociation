package runner.jUnit;

import cucumber.api.CucumberOptions;

//@RunWith(Cucumber.class)
@CucumberOptions(features = "feature/song.feature", glue = "")
public class Song {

}
