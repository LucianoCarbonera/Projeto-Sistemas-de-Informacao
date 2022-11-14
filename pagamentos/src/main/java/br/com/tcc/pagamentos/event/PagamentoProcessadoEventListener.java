package br.com.tcc.pagamentos.event;

import br.com.tcc.pagamentos.config.amqp.EventsConstants;
import br.com.tcc.pagamentos.dto.PagamentoProcessadoDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;


@Component
public class PagamentoProcessadoEventListener {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @EventListener
    private void pagamentoProcessadoEvent(PagamentoProcessadoDTO pagamentoProcessado) {
        System.out.println("-----------> Publicando evento do pagamento processado com id " + pagamentoProcessado.getId());
        rabbitTemplate.convertAndSend(EventsConstants.EXCHANGE_PAGAMENTO_EVENTS, "", pagamentoProcessado);
    }

}
