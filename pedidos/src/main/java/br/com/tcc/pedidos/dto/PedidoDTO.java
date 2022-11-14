package br.com.tcc.pedidos.dto;

import br.com.tcc.pedidos.entity.StatusPedido;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PedidoDTO {

    private UUID id;
    private StatusPedido status;
    private BigDecimal valorTotal;
    private List<ItemPedidoDTO> itens = new ArrayList<>();
    private List<PagamentoPedidoDTO> pagamentos = new ArrayList<>();

}
