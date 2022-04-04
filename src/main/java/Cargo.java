
public enum Cargo {

	ATENDENTE {
		@Override
		public double calculaComissao(double valor) {
			return valor * 0.1;
		}
	},
	VENDEDOR {
		@Override
		public double calculaComissao(double valor) {
			return valor * 0.15 + 5;
		}
	}, 
	GERENTE {
		@Override
		public double calculaComissao(double valor) {
			return valor * 0.20 + 10;
		}
	};

	public abstract double calculaComissao(double valor);

}
