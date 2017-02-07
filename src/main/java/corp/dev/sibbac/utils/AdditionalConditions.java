package corp.dev.sibbac.utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;

/**
 *	Se utiliza para aguardar por el renderizado de la pagina
 *	cuando se utiliza Angular en la app. 
 */
public class AdditionalConditions {
    public static ExpectedCondition<Boolean> angularHasFinishedProcessing() {
        return new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                return Boolean.valueOf(((JavascriptExecutor) driver).executeScript(
                	"return (window.angular !== undefined) && (angular.element(document).injector() !== undefined) "
                		+ "&& (angular.element(document).injector().get('$http').pendingRequests.length === 0)").toString());
            }
        };
    }
}
