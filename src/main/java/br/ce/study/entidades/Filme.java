package br.ce.study.entidades;

import br.ce.study.DescontoPorcentagem;

public class Filme {

    private String nome;
    private Integer estoque;
    private Double precoLocacao;

    private DescontoPorcentagem desconto;

    public Filme() {
    }

    public Filme(String nome, Integer estoque, Double precoLocacao) {
        this.nome = nome;
        this.estoque = estoque;
        this.precoLocacao = precoLocacao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getEstoque() {
        return estoque;
    }

    public void setEstoque(Integer estoque) {
        this.estoque = estoque;
    }

    public Double getPrecoLocacao() {
        return precoLocacao;
    }

    public void setPrecoLocacao(Double precoLocacao) {
        this.precoLocacao = precoLocacao;
    }

    public DescontoPorcentagem getDesconto() {
        return desconto;
    }

    public void setDesconto(DescontoPorcentagem desconto) {
        this.desconto = desconto;
    }

    public double calculaPorcentagem() {
        DescontoPorcentagem desc = this.getDesconto();
        return desc.calculaPorcentagem(precoLocacao);
    }
}