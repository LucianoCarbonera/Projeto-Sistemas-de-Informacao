package br.com.tcc.pedidos.service;

import br.com.tcc.pedidos.dto.PagamentoProcessadoDTO;
import br.com.tcc.pedidos.dto.PedidoDTO;
import br.com.tcc.pedidos.entity.ItemPedido;
import br.com.tcc.pedidos.entity.Pedido;
import br.com.tcc.pedidos.entity.StatusPagamento;
import br.com.tcc.pedidos.entity.StatusPedido;
import br.com.tcc.pedidos.repository.PagamentoPedidoRepository;
import br.com.tcc.pedidos.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private PagamentoPedidoRepository pagamentoPedidoRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public PedidoDTO obterPorId(UUID id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new EmptyResultDataAccessException("Pedido não encontrado para o id " + id, 1));
        return modelMapper.map(pedido, PedidoDTO.class);
    }

    @Transactional
    public PedidoDTO criarPedido(PedidoDTO dto) {
        Pedido pedido = modelMapper.map(dto, Pedido.class);
        pedido.setDataHora(LocalDateTime.now());
        pedido.setStatus(StatusPedido.CRIADO);

        pedido.getItens().forEach(item -> item.setPedido(pedido));
        pedido.getPagamentos().forEach(pagamento -> {
            pagamento.setStatus(StatusPagamento.CRIADO);
            pagamento.setPedido(pedido);
        });

        BigDecimal valorTotalPedido = pedido.getItens().stream()
                .map(ItemPedido::getValorTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
        pedido.setValorTotal(valorTotalPedido);

        Pedido pedidoSalvo = pedidoRepository.save(pedido);

        PedidoDTO pedidoCriado = modelMapper.map(pedidoSalvo, PedidoDTO.class);
        return pedidoCriado;
    }

    @Transactional
    public void atualizaStatusAguardandoPagamento(UUID id) {
        pedidoRepository.atualizaStatus(id, StatusPedido.AGUARDANDO_PAGAMENTO, LocalDateTime.now());
        pagamentoPedidoRepository.atualizaStatusPorPedido(id, StatusPagamento.AGUARDANDO, LocalDateTime.now());
    }

    @Transactional
    public void receberEventoPagamentoProcessado(PagamentoProcessadoDTO pagamentoProcessado) {
        pagamentoPedidoRepository.atualizaStatusPagamento(pagamentoProcessado.getId(), pagamentoProcessado.getStatus(), LocalDateTime.now());
        verificaAtualizaStatusPedido(pagamentoProcessado.getPedidoId());
    }

    private void verificaAtualizaStatusPedido(UUID pedidoId) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new EmptyResultDataAccessException("Pedido não encontrado para o id " + pedidoId, 1));

        StatusPedido statusAtualizar = buscaStatusIdealRelacionadoPagamentos(pedido);
        if(statusAtualizar != StatusPedido.AGUARDANDO_PAGAMENTO) {
            pedidoRepository.atualizaStatus(pedidoId, statusAtualizar, LocalDateTime.now());
        }
    }

    private StatusPedido buscaStatusIdealRelacionadoPagamentos(Pedido pedido) {
        StatusPedido statusIdeal = pedido.getStatus();
        boolean todosPagamentosAprovados = pedido.getPagamentos().stream()
                .allMatch(pagamento -> StatusPagamento.APROVADO.equals(pagamento.getStatus()));
        if(todosPagamentosAprovados) {
            statusIdeal =  StatusPedido.PAGAMENTO_APROVADO;
        }

        boolean existePagamentoNegado = pedido.getPagamentos().stream()
                .anyMatch(pagamento -> StatusPagamento.NAO_AUTORIZADO.equals(pagamento.getStatus()));
        if(existePagamentoNegado) {
            statusIdeal =  StatusPedido.PAGAMENTO_NAO_AUTORIZADO;
        }

        boolean existePagamentoComErro = pedido.getPagamentos().stream()
                .anyMatch(pagamento -> StatusPagamento.ERRO.equals(pagamento.getStatus()));
        if(existePagamentoComErro) {
            statusIdeal =  StatusPedido.ERRO_PAGAMENTO;
        }

        return statusIdeal;
    }

}
