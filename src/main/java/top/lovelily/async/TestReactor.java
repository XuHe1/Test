package top.lovelily.async;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

import static reactor.core.publisher.Flux.*;


/**
 * Desc: TestReactor
 * Author: xuhe
 * Date: 2019/12/26 4:14 下午
 * Version: 1.0
 */
public class TestReactor {

    //  an implementation of Publisher, observe 0 to N items
    private static Flux<String> ifhrIds() {
        List<String> ids = new ArrayList<>();
        ids.add("1");
        ids.add("2");
        just("1", "2");
        return fromIterable(ids);
    }

    //  an implementation of Publisher, observe 0 to 1 items
    private static Mono<String> ifhrName(String id) {
        return Mono.just("NameJoe");
    }

    private static Mono<Integer> ifhrStat(String id) {
        return Mono.just(new Integer(103));
    }

    public static void main(String[] args) {
        Flux<Integer> ints = Flux.range(1, 4)
                .map(i -> {
                    if (i <= 3) return i;
                    throw new RuntimeException("Got to 4");
                });

        // a publisher can server many subscriber
        ints.subscribe(i -> System.out.println("First: " +i),
                error -> System.err.println("Error1: " + error));

        ints.subscribe(i -> System.out.println("Second: " + i),
                error -> System.err.println("Error2: " + error),
                () -> System.out.println("Done"),
                subscription -> subscription.request(10));

        ints.onErrorReturn(4).subscribe(i -> System.out.println("Third: " + i));

        Flux<String> ids = ifhrIds();
        Flux<String> combinations =
                ids.flatMap(id -> {
                    Mono<String> nameTask = ifhrName(id);
                    Mono<Integer> statTask = ifhrStat(id);

                    return nameTask.zipWith(statTask,
                            (name, stat) -> "Name " + name + " has stats " + stat);
                });

        Mono<List<String>> result = combinations.collectList();

        List<String> results = result.block();
        // -ea
        assert results.contains("Name NameJoe has stats 103"): "Error";


    }
}
