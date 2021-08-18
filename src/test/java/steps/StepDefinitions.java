package steps;

import static io.restassured.RestAssured.basePath;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.reset;
import static java.util.Objects.isNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
//import models.framework.RestrictionReport;
import pojo.BearerToken;
import properties.GetProperties;
import utils.ReusableMethods;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class StepDefinitions {

	private static String hostschema = "https";
	private static String apigeeVersion = "";
	private static Integer hostport = null;
	private static String path = "";
	private static String jsonBodyString = "";
	private static ExtractableResponse jsonresponse;
	private static String stringresponse;
	private static Map<String, String> headers = new HashMap<>();
	private static Map<String, String> requestparameters = new HashMap<>();
	private static Map<String, String> queryparameters = new HashMap<>();
	private static GetProperties props = new GetProperties();
	final static Logger logger = LogManager.getLogger(StepDefinitions.class);

//	RestrictionReport response = new RestrictionReport();
	ObjectMapper mapper = new ObjectMapper();

	// ------------------------------------GHERKIN
	// FUNCTIONS---------------------------------------
	
	@Given("^Eu executo o caso de teste \"([^\"]*)\"$")
	public void i_run_the_test_case(String testCase) throws Throwable {
		clear_all_for_new_test();
		Hooks.scenario.log("Testcase : " + testCase);
	}

	@Given("^Eu uso o domínio \"([^\"]*)\"$")
	public void i_use_the_domain(String domain) throws Throwable {
		Hooks.setHostname(domain);
		baseURI = getRequestUrl();
		Hooks.scenario.log("Domain : " + baseURI);
	}

	@Given("^Eu seleciono o (APIGEE|OPENSHIFT) do ambiente de (DESENV|HOMOLOG|PROD)$")
	public void select_environment(String type, String env) throws IOException {
		if (type.equals("APIGEE")) {
			switch (env) {
			case "DESENV":
				Hooks.setHostname(props.getValueFromEnvironment("apigeeDesenv"));
				logger.info("Ambiente selecionado API: DESENVOLVIMENTO " + type + " : " + Hooks.hostname);
				break;
			case "HOMOLOG":
				Hooks.setHostname(props.getValueFromEnvironment("apigeeHomolog"));
				logger.info("Ambiente selecionado API: HOMOLOGAÇÃO " + type + " : " + Hooks.hostname);
				break;
			case "PROD":
				Hooks.setHostname(props.getValueFromEnvironment("apigeeProd"));
				logger.info("Ambiente selecionado API: PRODUÇÃO " + type + " : " + Hooks.hostname);
				break;
			}
		} else if (type.equals("OPENSHIFT")) {
			switch (env) {
			case "DESENV":
				Hooks.setHostname(props.getValueFromEnvironment("openShiftDesenv"));
				logger.info("Ambiente selecionado API: DESENVOLVIMENTO " + type + " : " + Hooks.hostname);
				break;
			case "HOMOLOG":
				Hooks.setHostname(props.getValueFromEnvironment("openShiftHomolog"));
				logger.info("Ambiente selecionado API: HOMOLOGAÇÃO " + type + " : " + Hooks.hostname);
				break;
			case "PROD":
				Hooks.setHostname(props.getValueFromEnvironment("openShiftProd"));
				logger.info("Ambiente selecionado API: PRODUÇÃO " + type + " : " + Hooks.hostname);
				break;
			}
		} else {
			logger.error("Ambiente inválido: " + type);
		}
		baseURI = getRequestUrl();
		Hooks.scenario.log("Domain : " + baseURI);
	}

	@And("^Eu uso a rota \"([^\"]*)\"$")
	public void i_use_the_route(String route) throws Throwable {
		StepDefinitions.path = apigeeVersion + ReusableMethods.replaceVariablesValues(route);
		StepDefinitions.path = StepDefinitions.path.replaceAll(" ", "%20");
		StepDefinitions.path = StringUtils.stripAccents(StepDefinitions.path);
		basePath = StepDefinitions.path;
		Hooks.scenario.log("Route : " + basePath);
		logger.info("Route : " + basePath);
	}

	@Given("Eu uso o documento {string} para minha requisição")
	public void i_use_the_document_to_my_request(String doc) {
		StepDefinitions.path = StepDefinitions.path + doc;
		basePath = StepDefinitions.path;
		Hooks.scenario.log("Route : " + basePath);
		logger.info("Route : " + basePath);
	}

	@Given("Eu uso o usuário {string} para minha requisição")
	public void i_use_the_user_to_my_request(String user) {
		StepDefinitions.path = StepDefinitions.path + user;
		basePath = StepDefinitions.path;
		Hooks.scenario.log("Route : " + basePath);
		logger.info("Route : " + basePath);
	}

	@Given("^Eu adiciono o usuário e a senha para o body do bearer token$")
	public void setUser() throws Throwable {
		BearerToken token = new BearerToken();
		token.setUsername(props.getValueFromCredential("username"));
		token.setPassword(props.getValueFromCredential("password"));
		setJsonBody(ReusableMethods.converterObjetoParaJson(token));
	}

	@When("^Eu envio a requisição para o novo bearer token usando o clientid \"([^\"]*)\" e \"([^\"]*)\"$")
	public void i_send_the_request_for_new_Bearer_token_using_the_clientid_and(String clientid, String basic)
			throws Throwable {
		generatevalidbearer(clientid, basic);
	}

	@When("^Eu envio a requisição para o novo bearer token usando clientid e secret$")
	public void i_send_the_request_for_new__token_using_secret() throws Throwable {
		generateTokenCliendIdSecret(props.getValueFromEnvironment("IAMClientId"),
				props.getValueFromEnvironment("IAMClientSecret"));
	}

	@When("^Eu envio a requisição para o novo Bearer token usando o basic \"([^\"]*)\"$")
	public void i_send_the_request_for_new_Bearer_token_using_basic(String basic) throws Throwable {
		generatebasic(basic);
	}

	@Then("^Eu salvo o novo Bearer token$")
	public void i_save_the_new_Bearer_token() throws Throwable {
		Hooks.scenario.log("New Bearer Token : " + Hooks.bearerToken);
		Hooks.scenario.log("Bearer expire in : "
				+ (Integer.parseInt(Hooks.bearerexpiresIn) - (System.currentTimeMillis() / 1000)) / 60 + " minutes.");
	}

	@And("^Eu uso o Bearer token salvo no header$")
	public void i_use_the_saved_Bearer_token_as_header() throws Throwable {
		StepDefinitions.headers.put("Authorization", "Bearer " + Hooks.bearerToken);
		StepDefinitions.headers.put("Content-Type", "application/json");
		Hooks.scenario.log("Bearer : " + StepDefinitions.headers.values().toString());
		logger.info("Bearer token used in header request : " + StepDefinitions.headers.values().toString());
	}

	@And("^Eu defino o header \"([^\"]*)\" as \"([^\"]*)\"$")
	public static void setHeader(String key, String value) throws Throwable {
		StepDefinitions.headers.put(key, value);
		Hooks.scenario.log("Headers : " + StepDefinitions.headers.values().toString());
	}

	@And("^Eu limpo todos os headers$")
	public void clearHeaders() throws Throwable {
		StepDefinitions.headers.clear();
	}

	@And("^Eu defino o parâmetro de consulta \"([^\"]*)\" como \"([^\"]*)\"$")
	public void setQueryParameter(String key, String value) throws Throwable {
		StepDefinitions.queryparameters.put(key, value);
		Hooks.scenario.log("Query parameters : " + StepDefinitions.queryparameters.values().toString());
	}

	@And("^Eu limpo todos os parâmetros de consulta$")
	public void clearQueryParameters() throws Throwable {
		StepDefinitions.queryparameters.clear();
	}

	@And("^Eu defino o parâmetro de solicitação \"([^\"]*)\" como \"([^\"]*)\"$")
	public void setRequestParameter(String key, String value) throws Throwable {
		StepDefinitions.requestparameters.put(key, value);
		Hooks.scenario.log("Request parameters : " + StepDefinitions.requestparameters.values().toString());
	}

	@And("^Eu limpo todos os parâmetros de solicitação$")
	public void clearRequestParameters() throws Throwable {
		StepDefinitions.requestparameters.clear();
	}

	@When("^Eu envio a requisição GET$")
	public void i_send_the_GET_request() throws Throwable {
		ExtractableResponse<Response> jsonresponse = given().headers(StepDefinitions.headers).request()
				.params(StepDefinitions.requestparameters).queryParams(StepDefinitions.queryparameters).when().get()
				.then().extract();
		StepDefinitions.jsonresponse = jsonresponse;
		ReusableMethods.rawToJson(jsonresponse);
	}

	@And("^Eu uso o corpo json$")
	public void setJsonBody(String jsonBody) throws Throwable {
		StepDefinitions.jsonBodyString = ReusableMethods.replaceVariablesValues(jsonBody);
		Hooks.scenario.log("Body : " + jsonBodyString);
		logger.info("Post auth body : " + jsonBodyString);
	}

	@When("^Eu envio a requisição POST$")
	public void i_send_the_POST_request() throws Throwable {
		ExtractableResponse<Response> jsonresponse = given().headers(StepDefinitions.headers).request()
				.params(StepDefinitions.requestparameters).queryParams(StepDefinitions.queryparameters)
				.body(jsonBodyString).when().post().then().extract();
		StepDefinitions.jsonresponse = jsonresponse;
	}

	@Then("^Eu capturo a tela com o resultado$")
	public void i_print_the_response() throws Throwable {
		Hooks.scenario.log("Response : " + jsonresponse.response().body().asString());
	}

	@And("^O Http response precisa ser (\\d+)$")
	public void http_response_should_be(int int1) throws Throwable {
		assertEquals(int1, jsonresponse.statusCode());
		Hooks.scenario.log("HTTP Response " + jsonresponse.statusCode() + " returned in response");
	}

	@And("^Eu valido o parâmetro de resposta json \"([^\"]*)\" que deve ser \"([^\"]*)\"$")
	public void i_validate_the_json_response_parameter(String key, String expectedvalue) throws Throwable {
		assertEquals(expectedvalue, jsonresponse.body().jsonPath().getString(key));
		Hooks.scenario.log("Json Parameter " + key + " with value " + jsonresponse.body().jsonPath().getString(key)
				+ " found in response.");
	}

	@And("^Eu valido o parâmetro de resposta xml \"([^\"]*)\" que deve ser \"([^\"]*)\"$")
	public void i_validate_the_xml_response_parameter(String key, String expectedvalue) throws Throwable {
		assertEquals(expectedvalue, jsonresponse.body().xmlPath().getString(key));
		Hooks.scenario.log("Xml Parameter " + key + " with value " + jsonresponse.body().xmlPath().getString(key)
				+ " found in response.");
	}

	@And("^Eu valido o texto \"([^\"]*)\" existente no response$")
	public void i_validate_the_xml_response_parameter(String key) throws Throwable {
		assertTrue(jsonresponse.body().asString().contains(key));
		Hooks.scenario.log("Text Parameter " + key + " found in response.");
	}

	@And("^Eu limpo os parâmetros para um novo teste $")
	private void clear_all_for_new_test() throws Throwable {
		reset();
		clearHeaders();
		clearQueryParameters();
		clearRequestParameters();
		jsonBodyString = "";
		stringresponse = "";
		path = "";
	}
	
	@And("Eu valido o retorno da API {}")
	public void eu_valido_o_retorno_da_api(boolean status) throws JsonMappingException, JsonProcessingException {
//		response = mapper.readValue(Hooks.responseJson, RestrictionReport.class);
//		System.out.println("response.getRestrictions().isAll(): " + response.getRestrictions().isAll());
//		ReusableMethods.tryValidating(status,response.getRestrictions().isAll());
	}
	
	// ------------------------------------COMMON
	// FUNCTIONS---------------------------------------

	public String generatevalidbearer(String clientid, String basic) throws Throwable {
		long timestampSecondsNow = (System.currentTimeMillis() / 1000) - 60;
		if (isNull(Hooks.bearerToken) || isNull(Hooks.bearerexpiresIn)
				|| Long.parseLong(Hooks.bearerexpiresIn) < timestampSecondsNow || !clientid.equals(Hooks.bearerClientId)
				|| !basic.equals(Hooks.bearerBasic)) {
			ExtractableResponse<Response> jsonresponse = given().header("Authorization", basic)
					.header("Content-Type", "application/json").queryParam("clientId", clientid).body(jsonBodyString)
					.when().post().then().assertThat().statusCode(201).extract();
			StepDefinitions.jsonresponse = jsonresponse;
			ReusableMethods.rawToJson(jsonresponse);
			Hooks.bearerToken = jsonresponse.path("accessToken");
			Hooks.bearerexpiresIn = jsonresponse.path("expiresIn");
			Hooks.bearerClientId = clientid;
			Hooks.bearerBasic = basic;
		}
		return Hooks.bearerToken;
	}

	public String generatebasic(String basic) throws Throwable {
		long timestampSecondsNow = (System.currentTimeMillis() / 1000) - 60;
		if (isNull(Hooks.bearerToken) || isNull(Hooks.bearerexpiresIn)
				|| Long.parseLong(Hooks.bearerexpiresIn) < timestampSecondsNow || !basic.equals(Hooks.bearerBasic)) {
			ExtractableResponse<Response> bearerResponse = given().header("Authorization", basic)
					.header("Content-Type", "application/json").queryParam("").body(jsonBodyString).when().post().then()
					.assertThat().statusCode(201).extract();
			Hooks.bearerToken = bearerResponse.path("accessToken");
			Hooks.bearerexpiresIn = bearerResponse.path("expiresIn");
			Hooks.bearerBasic = basic;
		}
		return Hooks.bearerToken;
	}

	public String generateTokenCliendIdSecret(String clientId, String clientSecret) throws Throwable {
		long timestampSecondsNow = (System.currentTimeMillis() / 1000) - 60;
		if (isNull(Hooks.bearerToken) || isNull(Hooks.bearerexpiresIn)
				|| Long.parseLong(Hooks.bearerexpiresIn) < timestampSecondsNow) {
			ExtractableResponse<Response> bearerResponse = given().auth().preemptive().basic(clientId, clientSecret)
					.header("Content-Type", "application/json").queryParam("").body(jsonBodyString).when().post().then()
					.assertThat().statusCode(201).extract();
			Hooks.bearerToken = bearerResponse.path("accessToken");
			Hooks.bearerexpiresIn = bearerResponse.path("expiresIn");
		}
		return Hooks.bearerToken;
	}

	private static String getRequestUrl() {

		if (StepDefinitions.hostport == null) {
			return StepDefinitions.hostschema + "://" + Hooks.hostname;
		} else {
			return StepDefinitions.hostschema + "://" + Hooks.hostname + ":" + StepDefinitions.hostport.toString();
		}
	}
}
