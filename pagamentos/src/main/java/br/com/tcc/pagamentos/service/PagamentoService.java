package br.com.tcc.pagamentos.service;

import br.com.tcc.pagamentos.dto.PagamentoPedidoDTO;
import br.com.tcc.pagamentos.dto.PagamentoProcessadoDTO;
import br.com.tcc.pagamentos.dto.PedidoDTO;
import br.com.tcc.pagamentos.entity.PagamentoPedido;
import br.com.tcc.pagamentos.entity.StatusPagamento;
import br.com.tcc.pagamentos.repository.PagamentoPedidoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PagamentoService {

    @Autowired
    private PagamentoPedidoRepository pagamentoPedidoRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    public List<PagamentoProcessadoDTO> registrarProcessarPagamentos(PedidoDTO pedidoDTO) {
        registrarPagamentos(pedidoDTO.getId(), pedidoDTO.getPagamentos());
        return processarPagamentos(pedidoDTO.getId(), pedidoDTO.getPagamentos());
    }

    private void registrarPagamentos(UUID idPedido, List<PagamentoPedidoDTO> pagamentos) {
        pagamentos.forEach(pagamento -> {
            PagamentoPedido pagamentoPedido = modelMapper.map(pagamento, PagamentoPedido.class);
            pagamentoPedido.setPedido(idPedido);
            pagamentoPedidoRepository.save(pagamentoPedido);
        });
    }

    private List<PagamentoProcessadoDTO> processarPagamentos(UUID idPedido, List<PagamentoPedidoDTO> pagamentos) {
        List<PagamentoProcessadoDTO> pagamentosProcessados = new ArrayList<>();
        pagamentos.forEach(pagamento -> {
            PagamentoProcessadoDTO pagamentoProcessado = processarPagamento(idPedido, pagamento);
            pagamentosProcessados.add(pagamentoProcessado);
        });
        return pagamentosProcessados;
    }

    private PagamentoProcessadoDTO processarPagamento(UUID idPedido, PagamentoPedidoDTO pagamento) {
        System.out.println("-----------> Processando pagamento com id " + pagamento.getId() +" do pedido " + idPedido);
        //Simula o envio do pagamento para o gateway com um tempo de espera de 5 segundos
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        StatusPagamento statusPagamentoProcessado = StatusPagamento.APROVADO;
        if ("999".equals(pagamento.getCvc())) {
            statusPagamentoProcessado = StatusPagamento.NAO_AUTORIZADO;
        }

        if ("888".equals(pagamento.getCvc())) {
            statusPagamentoProcessado = StatusPagamento.ERRO;
        }

        System.out.println("-----------> Pagamento " + pagamento.getId() + " processado: " + statusPagamentoProcessado);
        pagamentoPedidoRepository.atualizaStatus(idPedido, statusPagamentoProcessado, LocalDateTime.now());
        return PagamentoProcessadoDTO.builder()
                .id(pagamento.getId()) //
                .pedidoId(idPedido)
                .valor(pagamento.getValor()) //
                .status(statusPagamentoProcessado) //
                .build();
    }



}
