package br.ce.study.servicos;

import static br.ce.study.utils.DataUtils.isMesmaData;
import static br.ce.study.utils.DataUtils.obterDataComDiferencaDias;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import br.ce.study.entidades.Filme;
import br.ce.study.entidades.Locacao;
import br.ce.study.entidades.Usuario;
import br.ce.study.exceptions.FilmeSemEstoqueException;
import br.ce.study.exceptions.LocadoraException;

public class LocacaoServiceTest {

	private LocacaoService service;
	
	@Rule
	public ErrorCollector error = new ErrorCollector();

	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Before
	public void setup() {
		service = new LocacaoService();
	}
	
	@Test
	public void deveAlugarFilme() throws Exception {
		// cenario
		Usuario usuario = new Usuario("usuario 1");
		List<Filme> filmes = List.of(new Filme("filme 1", 2, 5.0));
		
		// acao
		Locacao locacao = service.alugarFilme(usuario, filmes);

		// verificacao
		assertThat(locacao.getValor(), is(equalTo(5.0)));
		error.checkThat(isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
		error.checkThat(isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1)), is(true));

	}

	/*@Test
	public void testeLocacao_DataLocacao() throws Exception {
		// cenario
		LocacaoService service = new LocacaoService();
		Usuario usuario = new Usuario("usuario 1");
		Filme filme = new Filme("filme 1", 2, 5.0);

		// acao
		Locacao locacao = service.alugarFilme(usuario, filme);

		Assert.assertNotNull("Data de loca��o n�o pode ser null", locacao.getDataLocacao());
		Assert.assertNotNull("Data de retorno n�o pode ser null", locacao.getDataRetorno());
		Assert.assertTrue("Data de loca��o deve ser antes da data de retorno", locacao.getDataLocacao().before(locacao.getDataRetorno()));
		
	}*/
	
	// essa forma � mais elegante de usar, porem � mais utlizada quando 
	// apenas a exce��o � necessaria para o teste
	
	@Test(expected = FilmeSemEstoqueException.class)
	public void deveLancarExcecaoAoAlugarFilmeSemEstoque() throws Exception {
		// cenario
		Usuario usuario = new Usuario("usuario 1");
		List<Filme> filmes = Arrays.asList(new Filme("filme 1", 0, 4.0));

		// acao
		service.alugarFilme(usuario, filmes);
	}

	
	/* Se precisar da exce��o e da mensagem, essa forma
	 * � a mais adequada
	 * */
	@Test
	public void naoDeveAlugarFilmeSemUsuario() throws FilmeSemEstoqueException {
		// cenario
		List<Filme> filmes = Arrays.asList(new Filme("filme 1", 2, 5.0));

		// acao
		try {
			service.alugarFilme(null, filmes);
			fail();
		} catch (LocadoraException e) {
			assertThat(e.getMessage(), is("usuario vazio"));
		}
		
		//System.out.println("forma robusta");

	}

	/* essa atende grande maioria dos casos, porem a forma robusta 
	 * � mais especifica para alguns casos
	 */
	@Test
	public void naoDeveAlugarFilmeSemFilme() throws FilmeSemEstoqueException, LocadoraException {
		// cenario
		Usuario usuario = new Usuario("usuario 1");

		exception.expect(LocadoraException.class);
		exception.expectMessage("filme vaziO");

		// acao
		service.alugarFilme(usuario, null);

		//System.out.println("forma nova");
	}

	@Test
	public void devePagar75PorCentroNoFilme3() throws FilmeSemEstoqueException, LocadoraException {
		//cenario
		Usuario usuario = new Usuario("usuario 1");
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 2, 4.0),
											new Filme("Filme 2", 2, 4.0),
											new Filme("Filme 3", 2, 4.0));
		//acao
		Locacao resultado = service.alugarFilme(usuario, filmes);

		//verificacao
		assertThat(resultado.getValor(), is(11.0));

	}

	@Test
	public void devePagar50PorCentroNoFilme4() throws FilmeSemEstoqueException, LocadoraException {
		//cenario
		Usuario usuario = new Usuario("usuario 1");
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 2, 4.0),
				new Filme("Filme 2", 2, 4.0),
				new Filme("Filme 3", 2, 4.0),
				new Filme("Filme 4", 2, 4.0));
		//acao
		Locacao resultado = service.alugarFilme(usuario, filmes);

		//verificacao
		assertThat(resultado.getValor(), is(13.0));

	}

	@Test
	public void devePagar25PorCentroNoFilme5() throws FilmeSemEstoqueException, LocadoraException {
		//cenario
		Usuario usuario = new Usuario("usuario 1");
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 2, 4.0),
											new Filme("Filme 2", 2, 4.0),
											new Filme("Filme 3", 2, 4.0),
											new Filme("Filme 4", 2, 4.0),
											new Filme("Filme 5", 2, 4.0));
		//acao
		Locacao resultado = service.alugarFilme(usuario, filmes);

		//verificacao
		assertThat(resultado.getValor(), is(14.0));

	}
	@Test
	public void devePagar0PorCentroNoFilme6() throws FilmeSemEstoqueException, LocadoraException {
		//cenario
		Usuario usuario = new Usuario("usuario 1");
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 2, 4.0),
											new Filme("Filme 2", 2, 4.0),
											new Filme("Filme 3", 2, 4.0),
											new Filme("Filme 4", 2, 4.0),
											new Filme("Filme 5", 2, 4.0),
											new Filme("Filme 6", 2, 4.0));
		//acao
		Locacao resultado = service.alugarFilme(usuario, filmes);

		//verificacao
		assertThat(resultado.getValor(), is(14.0));

	}
	/*
	 * @Test public void testeLocacao_filmeSemEstoque_2() { // cenario
	 * LocacaoService service = new LocacaoService(); Usuario usuario = new
	 * Usuario("usuario 1"); Filme filme = new Filme("filme 1", 0, 5.0);
	 * 
	 * // acao try { service.alugarFilme(usuario, filme);
	 * fail("deve lan�ar uma exce��o"); } catch (Exception e) {
	 * assertThat(e.getMessage(), is("filme sem estoque")); } }
	 * 
	 * @Test public void testeLocacao_filmeSemEstoque_3() throws Exception { //
	 * cenario LocacaoService service = new LocacaoService(); Usuario usuario = new
	 * Usuario("usuario 1"); Filme filme = new Filme("filme 1", 0, 5.0);
	 * 
	 * //deve ser declarada antes da a��o, pois estamos esperando essas a��es. //Se
	 * executada depois, o service lanca a exce��o // e essas assertivas nao s�o
	 * executadas
	 * 
	 * exception.expect(Exception.class);
	 * exception.expectMessage("filme sem estoque");
	 * 
	 * // acao service.alugarFilme(usuario, filme);
	 * 
	 * }
	 */

}
