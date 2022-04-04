
public class Venda {

	private final Funcionario funcionario;
	private final double valor;

	public Venda(Funcionario funcionario, double valor) {
		this.funcionario = funcionario;
		this.valor = valor;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public double getValor() {
		return valor;
	}

	public double calculaComissao() {
		Cargo cargo = this.funcionario.getCargo();
		return cargo.calculaComissao(valor);
	}

}
