package runner;



import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;


@RunWith(Cucumber.class)
@CucumberOptions
         (
                features = "src/test/resources/features/BloqueioGeral.feature",
                plugin = {"pretty", "json:target/jsonReports/cucumber-report.json"},
                glue = {"steps"},
                tags= "",
                monochrome = true,
                dryRun = false,
                snippets = CucumberOptions.SnippetType.CAMELCASE
        )

public class TestRunner {
	
}
