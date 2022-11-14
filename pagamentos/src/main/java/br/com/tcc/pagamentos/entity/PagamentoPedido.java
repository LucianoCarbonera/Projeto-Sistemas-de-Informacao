package br.com.tcc.pagamentos.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "pagamento_pedido")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class PagamentoPedido {

    @Id
    @Column(name = "id", updatable = false)
    private UUID id;

    @NotNull
    @Column(name = "pedido_id")
    private UUID pedido;

    @NotBlank
    @Size(max = 16)
    @Column(name = "numero_cartao")
    private String numeroCartao;

    @NotBlank
    @Size(max = 7)
    @Column(name = "validade")
    private String validade;

    @NotBlank
    @Size(max = 5)
    @Column(name = "cvc")
    private String cvc;

    @NotNull
    @Positive
    @Column(name = "valor")
    private BigDecimal valor;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusPagamento status;

    @CreatedDate
    @Column(name = "criado_em", updatable = false)
    private LocalDateTime criadoEm;

    @LastModifiedDate
    @Column(name = "atualizado_em")
    private LocalDateTime atualizadoEm;

}
