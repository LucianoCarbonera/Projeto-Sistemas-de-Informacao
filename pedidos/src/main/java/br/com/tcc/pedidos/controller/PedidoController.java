package br.com.tcc.pedidos.controller;

import br.com.tcc.pedidos.dto.PedidoDTO;
import br.com.tcc.pedidos.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/pedido")
public class PedidoController {

    @Autowired
    private PedidoService service;

    @Autowired
    private ApplicationEventPublisher publisher;

    @GetMapping("/{id}")
    public ResponseEntity<PedidoDTO> obterPorId(@PathVariable @NotNull UUID id) {
        PedidoDTO dto = service.obterPorId(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<PedidoDTO> criarPedido(@RequestBody @Valid PedidoDTO dto, UriComponentsBuilder uriBuilder) {
        PedidoDTO pedidoCriado = service.criarPedido(dto);
        URI endereco = uriBuilder.path("/pedidos/{id}").buildAndExpand(pedidoCriado.getId()).toUri();
        publisher.publishEvent(pedidoCriado);
        return ResponseEntity.created(endereco).body(pedidoCriado);
    }

}
