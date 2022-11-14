package br.com.tcc.pedidos.entity;

public enum StatusPedido {
    CRIADO,
    AGUARDANDO_PAGAMENTO,
    PAGAMENTO_APROVADO,
    PAGAMENTO_NAO_AUTORIZADO,
    ERRO_PAGAMENTO
}
