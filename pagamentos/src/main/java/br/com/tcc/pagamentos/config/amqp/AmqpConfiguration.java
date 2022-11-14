package br.com.tcc.pagamentos.config.amqp;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmqpConfiguration {

    @Bean
    public RabbitAdmin criaRabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public ApplicationListener<ApplicationReadyEvent> inicializaAdmin(RabbitAdmin rabbitAdmin) {
        return event -> rabbitAdmin.initialize();
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
                                         Jackson2JsonMessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }

    @Bean
    public FanoutExchange fanoutExchangePedido() {
        return ExchangeBuilder.fanoutExchange(EventsConstants.EXCHANGE_PEDIDO_EVENTS).build();
    }

    @Bean
    public Queue queuePedidoEventsToPagamento() {
        return QueueBuilder.durable(EventsConstants.QUEUE_PEDIDO_EVENTS_TO_PAGAMENTO).build();
    }

    @Bean
    public Binding bindingQueuePedidoEventsToPagamento() {
        return BindingBuilder.bind(queuePedidoEventsToPagamento()).to(fanoutExchangePedido());
    }

    @Bean
    public FanoutExchange fanoutExchangePagamento() {
        return ExchangeBuilder.fanoutExchange(EventsConstants.EXCHANGE_PAGAMENTO_EVENTS).build();
    }

    @Bean
    public Queue queuePagamentoEventsToPedido() {
        return QueueBuilder.durable(EventsConstants.QUEUE_PAGAMENTO_EVENTS_TO_PEDIDO).build();
    }

    @Bean
    public Binding bindingQueuePagamentoEventsToPedido() {
        return BindingBuilder.bind(queuePagamentoEventsToPedido()).to(fanoutExchangePagamento());
    }

}
