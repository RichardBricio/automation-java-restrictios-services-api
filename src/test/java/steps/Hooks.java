package steps;


import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.junit.After;
import org.junit.Test;

import java.util.Collection;


public class Hooks {

    private static Collection<String> taggs;
    public static Scenario scenario;
    public static String hostname;
    public static String requestlog;
    public static String responseJson;
    public static String bearerClientId;
    public static String bearerBasic;
    public static String bearerToken;
    public static String bearerexpiresIn;

    public static  void setHostname(String hostname)
    {
        Hooks.hostname = hostname;
    }

    /**
     * @throws Throwable
     */
    
    @Before
    public void runBeforeWithOrder(Scenario scenario) throws Throwable {
        Hooks.scenario = scenario;
        keepScenarion(scenario);
        //proxy("spobrproxy.intranet",3128);
        //authentication = basic( "" , "" );
        //RestAssured.ntlm("","");
        //useRelaxedHTTPSValidation("SSL");  // SSL  no keystore  // TLSv1.2  with certificate
        //configurarBrowser();
    }

    @Test
    public void logtherequests() throws Throwable{
     }

    @After
    public void teardown() {
    }


    public void keepScenarion(Scenario scenario) {
        setTaggs(scenario.getSourceTagNames());
    }

    public static Collection<String> getTaggs() {
        return taggs;
    }

    public static void setTaggs(Collection<String> taggs) {
        Hooks.taggs = taggs;
    }


}
