package org.fmi.plovdiv.runners;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources/org/fmi/plovdiv/features",
    glue = "org.fmi.plovdiv.steps",
    plugin = {"pretty", "html:target/cucumber-reports"},
    monochrome = true
)
public class TestRunner {
}