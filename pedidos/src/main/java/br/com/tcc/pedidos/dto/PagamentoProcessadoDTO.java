package br.com.tcc.pedidos.dto;

import br.com.tcc.pedidos.entity.StatusPagamento;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PagamentoProcessadoDTO {

    private UUID id;
    private UUID pedidoId;
    private BigDecimal valor;
    private StatusPagamento status;

}
