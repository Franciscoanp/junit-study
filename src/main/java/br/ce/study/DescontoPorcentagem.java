package br.ce.study;

public enum DescontoPorcentagem {

    DESCONTO_75_POR_CENTO {
        @Override
        public double calculaPorcentagem(double valor) {
            return valor * 0.75;
        }
    },
    DESCONTO_50_POR_CENTO {
        @Override
        public double calculaPorcentagem(double valor) {
            return valor * 0.50;
        }
    },
    DESCONTO_25_POR_CENTO {
        @Override
        public double calculaPorcentagem(double valor) {
            return valor * 0.25;
        }
    },
    DESCONTO_100_POR_CENTO {
        @Override
        public double calculaPorcentagem(double valor) {
            return valor * 0.0;
        }
    };

    public abstract double calculaPorcentagem(double valor);
}
