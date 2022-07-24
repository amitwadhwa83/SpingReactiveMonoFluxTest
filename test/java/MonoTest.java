import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

public class MonoTest {
    @Test
    void firstMono() {
        Mono.just("A");
    }

    @Test
    void monoWithConsumer() {
        Mono.just("A").log().
                subscribe(System.out::println);
    }

    @Test
    void monoWithDoOn() {
        Mono.just("A").log().
                doOnSubscribe(subs -> System.out.println("Subscribed>>>" + subs)).
                doOnRequest(request -> System.out.println("request>>>" + request)).
                doOnSuccess(complete -> System.out.println("complete>>>" + complete)).
                subscribe(System.out::println);
    }

    @Test
    void emptyMono() {
        Mono.empty().log().
                subscribe(System.out::println);
    }


    @Test
    void emptyCompleteConsumerMono() {
        Mono.empty().log().
                subscribe(System.out::println, null, () -> System.out.println("Done"));
    }

    @Test
    void errorRuntimeExceptionMono() {
        Mono.error(new RuntimeException()).log().subscribe();
    }

    @Test
    void errorExceptionMono() {
        Mono.error(new Exception()).log().subscribe(System.out::println, e -> System.out.println("Error:" + e));
    }

    @Test
    void errorOnErrorResumeMono() {
        Mono.error(new Exception()).onErrorResume(e -> {
            System.out.println("Caught:" + e);
            return Mono.just("B");
        }).log().subscribe(System.out::println, e -> System.out.println("Error:" + e));
    }
}