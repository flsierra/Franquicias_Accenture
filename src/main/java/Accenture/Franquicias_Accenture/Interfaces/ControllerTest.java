package Accenture.Franquicias_Accenture.Interfaces;

import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class ControllerTest {

    private final DatabaseClient databaseClient;

    @GetMapping
    public Mono<String> test() {
        return databaseClient
                .sql("SELECT 1")
                .fetch()
                .one()
                .map(result -> "Conexión exitosa ✅")
                .onErrorResume(e -> Mono.just("Error: " + e.getMessage()));
    }
}