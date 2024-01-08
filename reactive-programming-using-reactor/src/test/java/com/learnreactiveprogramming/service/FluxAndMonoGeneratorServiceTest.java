package com.learnreactiveprogramming.service;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FluxAndMonoGeneratorServiceTest {
    FluxAndMonoGeneratorService fluxAndMonoGeneratorService = new FluxAndMonoGeneratorService();

    @Test
    void nameFlux() {
        var nameFLUX = fluxAndMonoGeneratorService.nameFlux();
        StepVerifier.create(nameFLUX).expectNext("alex", "ben", "chole").verifyComplete();
    }

    @Test
    void nameFlux_map() {
        var namesFlux_map = fluxAndMonoGeneratorService.nameFlux_map();
        StepVerifier.create(namesFlux_map).expectNext("ALEX", "BEN", "CHOLE").verifyComplete();
    }

    @Test
    void nameFlux_immutability() {
        var namesFlux_map = fluxAndMonoGeneratorService.nameFlux_immutability();
        StepVerifier.create(namesFlux_map).expectNext("alex", "ben", "chole").verifyComplete();
    }

    @Test
    void nameFlux_map_filter() {
        var namesFlux_map = fluxAndMonoGeneratorService.nameFlux_map_filter(3);
        StepVerifier.create(namesFlux_map).expectNext("ALEX", "CHOLE").verifyComplete();
    }

    @Test
    void nameFlux_flatMap() {
        var namesFlux_map = fluxAndMonoGeneratorService.nameFlux_flatMap(3);
        StepVerifier.create(namesFlux_map).expectNext("A", "L", "E", "X", "C", "H", "O", "L", "E").verifyComplete();
    }

    @Test
    void nameFlux_flatMap_async() {
        var namesFlux_map = fluxAndMonoGeneratorService.nameFlux_flatMap_async(3);
        StepVerifier.create(namesFlux_map)
//                .expectNext("A","L","E","X",  "C","H","O","L","E")
                .expectNextCount(9)
                .verifyComplete();
    }

    @Test
    void nameFlux_ConCatMap() {
        var namesFlux_map = fluxAndMonoGeneratorService.nameFlux_ConCatMap(3);
        StepVerifier.create(namesFlux_map)
                .expectNext("A", "L", "E", "X", "C", "H", "O", "L", "E")
//                .expectNextCount(9)
                .verifyComplete();
    }

    @Test
    void namesMono_flatMap_filter() {
        var value = fluxAndMonoGeneratorService.namesMono_flatMap_filter(3);
        StepVerifier.create(value).expectNext(List.of("A", "L", "E", "X")).verifyComplete();
    }

    @Test
    void namesMono_flatMapMany() {
        var value = fluxAndMonoGeneratorService.namesMono_flatMapMany(3);
        StepVerifier.create(value).expectNext("A", "L", "E", "X").verifyComplete();
    }

    @Test
    void nameFlux_Transform() {
        var namesFlux_map = fluxAndMonoGeneratorService.nameFlux_Transform(3);
        StepVerifier.create(namesFlux_map).expectNext("A", "L", "E", "X", "C", "H", "O", "L", "E").verifyComplete();
    }

    @Test
    void nameFlux_Transform_1() {
        var namesFlux_map = fluxAndMonoGeneratorService.nameFlux_Transform(6);
        StepVerifier.create(namesFlux_map)
                .expectNext("default")
                .verifyComplete();
    }

    @Test
    void nameFlux_Transform_switch_if_empty() {
        var namesFlux_map = fluxAndMonoGeneratorService.nameFlux_Transform_switch_if_empty(6);
        StepVerifier.create(namesFlux_map)
                .expectNext("D", "E", "F", "A", "U", "L", "T")
                .verifyComplete();
    }
}