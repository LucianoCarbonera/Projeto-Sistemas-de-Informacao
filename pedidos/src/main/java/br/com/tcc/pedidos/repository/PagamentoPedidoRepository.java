package br.com.tcc.pedidos.repository;

import br.com.tcc.pedidos.entity.PagamentoPedido;
import br.com.tcc.pedidos.entity.StatusPagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public interface PagamentoPedidoRepository extends JpaRepository<PagamentoPedido, UUID> {

    @Modifying
    @Query("update PagamentoPedido p set p.status = :status, p.atualizadoEm = :dataAtualizacao where p.pedido.id = :pedidoId")
    void atualizaStatusPorPedido(UUID pedidoId, StatusPagamento status, LocalDateTime dataAtualizacao);

    @Modifying
    @Query("update PagamentoPedido p set p.status = :status, p.atualizadoEm = :dataAtualizacao where p.id = :pagamentoId")
    void atualizaStatusPagamento(UUID pagamentoId, StatusPagamento status, LocalDateTime dataAtualizacao);
}
