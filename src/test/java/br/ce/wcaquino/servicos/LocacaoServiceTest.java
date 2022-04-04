package br.ce.wcaquino.servicos;

import static br.ce.wcaquino.utils.DataUtils.isMesmaData;
import static br.ce.wcaquino.utils.DataUtils.obterDataComDiferencaDias;
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

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraException;

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
	public void testeLocacao() throws Exception {
		// cenario
		Usuario usuario = new Usuario("usuario 1");
		List<Filme> filmes = Arrays.asList(new Filme("filme 1", 2, 5.0));
		
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

		Assert.assertNotNull("Data de locação não pode ser null", locacao.getDataLocacao());
		Assert.assertNotNull("Data de retorno não pode ser null", locacao.getDataRetorno());
		Assert.assertTrue("Data de locação deve ser antes da data de retorno", locacao.getDataLocacao().before(locacao.getDataRetorno()));
		
	}*/
	
	// essa forma é mais elegante de usar, porem é mais utlizada quando 
	// apenas a exceção é necessaria para o teste
	
	@Test(expected = FilmeSemEstoqueException.class)
	public void testeLocacao_filmeSemEstoque() throws Exception {
		// cenario
		Usuario usuario = new Usuario("usuario 1");
		List<Filme> filmes = Arrays.asList(new Filme("filme 1", 0, 4.0));

		// acao
		service.alugarFilme(usuario, filmes);
	}

	
	/* Se precisar da exceção e da mensagem, essa forma
	 * é a mais adequada
	 * */
	@Test
	public void testeLocacao_usuarioVazio() throws FilmeSemEstoqueException {
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
	 * é mais especifica para alguns casos
	 */
	@Test
	public void testLocacao_FilmeVazio() throws FilmeSemEstoqueException, LocadoraException {
		// cenario
		Usuario usuario = new Usuario("usuario 1");

		exception.expect(LocadoraException.class);
		exception.expectMessage("filme vaziO");

		// acao
		service.alugarFilme(usuario, null);

		//System.out.println("forma nova");
	}

	/*
	 * @Test public void testeLocacao_filmeSemEstoque_2() { // cenario
	 * LocacaoService service = new LocacaoService(); Usuario usuario = new
	 * Usuario("usuario 1"); Filme filme = new Filme("filme 1", 0, 5.0);
	 * 
	 * // acao try { service.alugarFilme(usuario, filme);
	 * fail("deve lançar uma exceção"); } catch (Exception e) {
	 * assertThat(e.getMessage(), is("filme sem estoque")); } }
	 * 
	 * @Test public void testeLocacao_filmeSemEstoque_3() throws Exception { //
	 * cenario LocacaoService service = new LocacaoService(); Usuario usuario = new
	 * Usuario("usuario 1"); Filme filme = new Filme("filme 1", 0, 5.0);
	 * 
	 * //deve ser declarada antes da ação, pois estamos esperando essas ações. //Se
	 * executada depois, o service lanca a exceção // e essas assertivas nao são
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
