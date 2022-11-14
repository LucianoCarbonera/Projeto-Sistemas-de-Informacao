package br.com.tcc.pagamentos.dto;

import br.com.tcc.pagamentos.entity.StatusPagamento;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class PagamentoProcessadoDTO {

    private UUID id;
    private UUID pedidoId;
    private BigDecimal valor;
    private StatusPagamento status;

}
