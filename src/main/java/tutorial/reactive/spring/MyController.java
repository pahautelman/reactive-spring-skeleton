package tutorial.reactive.spring;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
public class MyController {

    @GetMapping("/greeting")
    public String greetingMessage() {
        return getDatabaseGreeting() + " " getDatabaseUsername() + "!";
    }

    @GetMapping("/reactive-greeting")
    public Mono<String> reactiveGreetingMessage() {
        return Mono.zip(getGreetingMono(), getUsernameMono())
            .map(tuple -> tuple.getT1() + " " + tuple.getT2() + "!");
    }

    private Mono<String> getGreetingMono() {
        return Mono.just(getGreeting())
            .delayElement(Duration.ofSeconds(3));
    }

    private Mono<String> getUsernameMono() {
        return Mono.just(getUsername())
            .delayElement(Duration.ofSeconds(3));
    }

    private String getDatabaseGreeting() {
        try {
            Thread.sleep(Duration.ofSeconds(3));
            return getGreeting();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private String getDatabaseUsername() {
        try {
            Thread.sleep(Duration.ofSeconds(3));
            return getUsername();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private String getGreeting() {
        return "Hello";
    }

    private String getUsername() {
        return "World";
    }
}