package co.edu.udea.qa.advantageshopping.runners.purchase;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        features = "src/test/resources/features/purchase",
        glue = "co.edu.udea.qa.advantageshopping.stepdefinitions",
        snippets = CucumberOptions.SnippetType.CAMELCASE
)
public class ProductPurchaseRunner {}
