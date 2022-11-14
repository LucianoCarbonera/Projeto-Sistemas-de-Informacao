package br.com.tcc.pedidos.event;

import br.com.tcc.pedidos.config.amqp.EventsConstants;
import br.com.tcc.pedidos.dto.PedidoDTO;
import br.com.tcc.pedidos.service.PedidoService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class PedidoCriadoEventListener {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @EventListener
    public void pedidoCriadoEvent(PedidoDTO pedidoCriado){
        System.out.println("-----------> Publicando evento do pedido criado com id " + pedidoCriado.getId());
        rabbitTemplate.convertAndSend(EventsConstants.EXCHANGE_PEDIDO_EVENTS, "", pedidoCriado);
        pedidoService.atualizaStatusAguardandoPagamento(pedidoCriado.getId());
    }

}
