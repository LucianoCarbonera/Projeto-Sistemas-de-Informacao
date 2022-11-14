package br.com.tcc.pagamentos.repository;

import br.com.tcc.pagamentos.entity.PagamentoPedido;
import br.com.tcc.pagamentos.entity.StatusPagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public interface PagamentoPedidoRepository extends JpaRepository<PagamentoPedido, UUID> {

    @Modifying(clearAutomatically = true)
    @Query("update PagamentoPedido p set p.status = :status, p.atualizadoEm = :dataAtualizacao where p.pedido = :pedidoId")
    void atualizaStatus(UUID pedidoId, StatusPagamento status, LocalDateTime dataAtualizacao);

}
