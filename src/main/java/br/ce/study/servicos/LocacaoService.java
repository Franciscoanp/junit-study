package br.ce.study.servicos;

import br.ce.study.entidades.Filme;
import br.ce.study.entidades.Locacao;
import br.ce.study.entidades.Usuario;
import br.ce.study.exceptions.FilmeSemEstoqueException;
import br.ce.study.exceptions.LocadoraException;
import br.ce.study.utils.DataUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static br.ce.study.DescontoPorcentagem.*;
import static br.ce.study.utils.DataUtils.adicionarDias;

public class LocacaoService {

    public Locacao alugarFilme(Usuario usuario, List<Filme> filmes) throws FilmeSemEstoqueException, LocadoraException {

        if (usuario == null) {
            throw new LocadoraException("usuario vazio");
        }

        if (filmes == null || filmes.isEmpty()) {
            throw new LocadoraException("filme vaziO");
        }

        for (Filme filme : filmes) {
            if (filme.getEstoque() == 0) {
                throw new FilmeSemEstoqueException();
            }
        }

        Locacao locacao = new Locacao();
        locacao.setFilmes(filmes);
        locacao.setUsuario(usuario);
        locacao.setDataLocacao(new Date());

        Double valorTotal = 0.0;

        for (int i = 0; i < filmes.size(); i++) {
            Filme filme = filmes.get(i);
            Double valorFilme = filme.getPrecoLocacao();

            switch (i) {
                case 2: {
                    filme.setDesconto(DESCONTO_75_POR_CENTO);
                    valorFilme = filme.calculaPorcentagem();
                    break;
                }
                case 3: {
                    filme.setDesconto(DESCONTO_50_POR_CENTO);
                    valorFilme = filme.calculaPorcentagem();
                    break;
                }
                case 4: {
                    filme.setDesconto(DESCONTO_25_POR_CENTO);
                    valorFilme = filme.calculaPorcentagem();
                    break;

                }
                case 5: {
                    filme.setDesconto(DESCONTO_100_POR_CENTO);
                    valorFilme = filme.calculaPorcentagem();
                    break;
                }
            }
            valorTotal += valorFilme;

        }

        locacao.setValor(valorTotal);

        // Entrega no dia seguinte
        Date dataEntrega = new Date();
        dataEntrega = adicionarDias(dataEntrega, 1);
        if (DataUtils.verificarDiaSemana(dataEntrega, Calendar.SUNDAY)) {
            dataEntrega = adicionarDias(dataEntrega, 1);
        }
        locacao.setDataRetorno(dataEntrega);

        // Salvando a locacao...
        // TODO adicionar método para salvar

        return locacao;
    }

}