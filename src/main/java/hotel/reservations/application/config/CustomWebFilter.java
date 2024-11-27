package hotel.reservations.application.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class CustomWebFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        logHttpWebFilter(exchange);
        return chain.filter(exchange);
    }

    private void logHttpWebFilter(ServerWebExchange exchange){
        ServerHttpRequest request = exchange.getRequest();
        log.info("Before -> Path: "+request.getPath()+" - Method: "+request.getMethod());

        ServerHttpResponse response = exchange.getResponse();
        log.info("After -> Path: "+request.getPath()+" - Status: "+response.getStatusCode());


    }
}
