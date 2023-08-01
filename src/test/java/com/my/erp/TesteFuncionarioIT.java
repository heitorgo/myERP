package com.my.erp;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import com.my.erp.domain.model.Cargo;
import com.my.erp.domain.model.Empresa;
import com.my.erp.domain.model.Funcionario;
import com.my.erp.domain.model.Loja;
import com.my.erp.domain.service.CargoService;
import com.my.erp.domain.service.EmpresaService;
import com.my.erp.domain.service.FuncionarioService;
import com.my.erp.domain.service.LojaService;
import com.my.erp.util.DatabaseCleaner;
import com.my.erp.util.ResourceUtils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class TesteFuncionarioIT {

	@LocalServerPort
	private int port;
	private Funcionario funcionario1;
	private String jsonFuncionarioCorreto;
	private String jsonFuncionarioIncorretoSemNome;
	private String jsonFuncionarioIncorretoSemCargo;
	private String jsonFuncionarioIncorretoCargoInexistente;
	private static final Long idFuncionarioInexistente = 100L;
	private static final String DADOS_INVALIDOS_PROBLEM_TYPE = "Dados inválidos";
	private static final String VIOLACAO_NEGOCIO_PROBLEM_TYPE = "Violação de regra de negócio";
	@Autowired
	private FuncionarioService funcionarioService;
	@Autowired
	private EmpresaService empresaService;
	@Autowired
	private LojaService lojaService;
	@Autowired
	private CargoService cargoService;
	@Autowired
	private DatabaseCleaner databaseCleaner;

	@BeforeEach
	public void SetUp() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "/funcionarios";

		databaseCleaner.clearTables();
		prepararDados();
		jsonFuncionarioCorreto = ResourceUtils.getContentFromResource("/json/correto/funcionario-correto.json");
		jsonFuncionarioIncorretoSemNome = ResourceUtils
				.getContentFromResource("/json/incorreto/funcionario/funcionario-incorreto-sem-nome.json");
		jsonFuncionarioIncorretoSemCargo = ResourceUtils
				.getContentFromResource("/json/incorreto/funcionario/funcionario-incorreto-sem-cargo.json");
		jsonFuncionarioIncorretoCargoInexistente = ResourceUtils
				.getContentFromResource("/json/incorreto/funcionario/funcionario-incorreto-cargo-inexistente.json");
	}

	private void prepararDados() {
		Empresa empresa1 = new Empresa();
		empresa1.setNome("Auto peças itu");
		empresa1.setRazaoSocial("Auto peças itu LTDA");
		empresaService.salvar(empresa1);

		Loja loja1 = new Loja();
		loja1.setNome("Loja 1");
		loja1.setEmpresa(empresa1);
		lojaService.salvar(loja1);

		Cargo cargo1 = new Cargo();
		cargo1.setTitulo("Caixa");
		cargo1.setRemuneracao(new BigDecimal(1800.00));
		cargo1.setLoja(loja1);
		cargoService.salvar(cargo1);

		funcionario1 = new Funcionario();
		funcionario1.setNome("Pedro");
		funcionario1.setCargo(cargo1);
		funcionarioService.salvar(funcionario1);
	}

	@Test
	public void deveRetornarStatus200_QuandoConsultarFuncionarios() {
		given().accept(ContentType.JSON).when().get().then().statusCode(HttpStatus.OK.value());
	}

	@Test
	public void deveRetornarStatus200_QuandoConsultarFuncionarioExistente() {
		given().accept(ContentType.JSON).pathParam("id", funcionario1.getId()).when().get("/{id}").then()
				.statusCode(HttpStatus.OK.value());
	}

	@Test
	public void deveRetornarStatus404_QuandoConsultarFuncionarioInexistente() {
		given().accept(ContentType.JSON).pathParam("id", idFuncionarioInexistente).when().get("/{id}").then()
				.statusCode(HttpStatus.NOT_FOUND.value());
	}

	@Test
	public void deveRetornarStatus201_QuandoCadastrarFuncionarioCorreto() {
		given().body(jsonFuncionarioCorreto).accept(ContentType.JSON).contentType(ContentType.JSON).when().post().then()
				.statusCode(HttpStatus.CREATED.value());
	}

	@Test
	public void deveRetornarStatus400_QuandoCadastrarFuncionarioSemNome() {
		given().body(jsonFuncionarioIncorretoSemNome).accept(ContentType.JSON).contentType(ContentType.JSON).when()
				.post().then().statusCode(HttpStatus.BAD_REQUEST.value())
				.body("title", equalTo(DADOS_INVALIDOS_PROBLEM_TYPE));
	}

	@Test
	public void deveRetornarStatus400_QuandoCadastrarFuncionarioSemCargo() {
		given().body(jsonFuncionarioIncorretoSemCargo).accept(ContentType.JSON).contentType(ContentType.JSON).when()
				.post().then().statusCode(HttpStatus.BAD_REQUEST.value())
				.body("title", equalTo(DADOS_INVALIDOS_PROBLEM_TYPE));
	}

	@Test
	public void deveRetornarStatus400_QuandoCadastrarFuncionarioCargoInexistente() {
		given().body(jsonFuncionarioIncorretoCargoInexistente).accept(ContentType.JSON).contentType(ContentType.JSON)
				.when().post().then().statusCode(HttpStatus.BAD_REQUEST.value())
				.body("title", equalTo(VIOLACAO_NEGOCIO_PROBLEM_TYPE));
	}
}
