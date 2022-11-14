package br.com.tcc.pedidos.repository;

import br.com.tcc.pedidos.entity.Pedido;
import br.com.tcc.pedidos.entity.StatusPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, UUID> {

    @Modifying
    @Query("update Pedido p set p.status = :status, p.atualizadoEm = :dataAtualizacao where p.id = :id")
    void atualizaStatus(UUID id, StatusPedido status, LocalDateTime dataAtualizacao);

}
