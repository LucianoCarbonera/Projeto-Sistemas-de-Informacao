package br.com.tcc.pedidos.event;

import br.com.tcc.pedidos.config.amqp.EventsConstants;
import br.com.tcc.pedidos.dto.PagamentoProcessadoDTO;
import br.com.tcc.pedidos.service.PedidoService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class PagamentoProcessadoEventListener {

    @Autowired
    private PedidoService pedidoService;

    @RabbitListener(queues = EventsConstants.QUEUE_PAGAMENTO_EVENTS_TO_PEDIDO)
    public void pagamentoProcessadoEvent(PagamentoProcessadoDTO pagamentoProcessado) {
        System.out.println("-----------> Pagamento processado. Id: " + pagamentoProcessado.getId() +". Status: " + pagamentoProcessado.getStatus()) ;
        pedidoService.receberEventoPagamentoProcessado(pagamentoProcessado);
    }

}
