package br.com.tcc.pagamentos.event;

import br.com.tcc.pagamentos.config.amqp.EventsConstants;
import br.com.tcc.pagamentos.dto.PagamentoProcessadoDTO;
import br.com.tcc.pagamentos.dto.PedidoDTO;
import br.com.tcc.pagamentos.service.PagamentoService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PedidoCriadoEventListener {

    @Autowired
    private PagamentoService pagamentoService;
    @Autowired
    private ApplicationEventPublisher publisher;

    @RabbitListener(queues = EventsConstants.QUEUE_PEDIDO_EVENTS_TO_PAGAMENTO)
    public void pedidoCriadoEvent(PedidoDTO pedidoDTO) {
        List<PagamentoProcessadoDTO> pagamentoProcessados = pagamentoService.registrarProcessarPagamentos(pedidoDTO);
        pagamentoProcessados.forEach(pagamentoProcessado -> publisher.publishEvent(pagamentoProcessado));
    }


}
