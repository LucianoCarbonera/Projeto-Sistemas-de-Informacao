CREATE TABLE pagamento_pedido (
    id uuid NOT NULL,
    pedido_id uuid NOT NULL,
    numero_cartao varchar(16) NOT NULL,
    validade varchar(7) NOT NULL,
    cvc varchar(3) NOT NULL,
    valor numeric NOT NULL,
    status varchar(255) NOT NULL,
    criado_em timestamp,
    atualizado_em timestamp,
    CONSTRAINT pk_pagamento_pedido_id PRIMARY KEY (id)
);
