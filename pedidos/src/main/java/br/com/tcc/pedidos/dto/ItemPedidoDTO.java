package br.com.tcc.pedidos.dto;


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
public class ItemPedidoDTO {

    private UUID id;
    private String descricao;
    private Integer quantidade;
    private BigDecimal valorTotal;

}
