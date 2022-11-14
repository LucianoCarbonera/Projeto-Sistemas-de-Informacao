package br.com.tcc.pedidos.dto;

import br.com.tcc.pedidos.entity.StatusPagamento;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PagamentoPedidoDTO {

    private UUID id;
    private String numeroCartao;
    private String validade;
    private String cvc;
    private BigDecimal valor;
    private StatusPagamento status;

}
