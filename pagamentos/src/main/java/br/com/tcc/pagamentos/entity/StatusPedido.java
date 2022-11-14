package br.com.tcc.pagamentos.entity;

public enum StatusPedido {
    CRIADO,
    AGUARDANDO_PAGAMENTO,
    PAGAMENTO_CONFIRMADO,
    PAGAMENTO_NEGADO,
    PAGAMENTO_NAO_AUTORIZADO,
    ERRO_PAGAMENTO
}
