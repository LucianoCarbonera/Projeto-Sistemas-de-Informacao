package br.com.tcc.pagamentos.config.amqp;

import lombok.experimental.UtilityClass;

@UtilityClass
public class EventsConstants {

    public static final String EXCHANGE_PEDIDO_EVENTS = "pedido.events";
    public static final String QUEUE_PEDIDO_EVENTS_TO_PAGAMENTO = "pedido.events.pedido-criado.pagamento";
    public static final String EXCHANGE_PAGAMENTO_EVENTS = "pagamento.events";
    public static final String QUEUE_PAGAMENTO_EVENTS_TO_PEDIDO = "pagamento.events.pagamento-processado.pedido";
}
