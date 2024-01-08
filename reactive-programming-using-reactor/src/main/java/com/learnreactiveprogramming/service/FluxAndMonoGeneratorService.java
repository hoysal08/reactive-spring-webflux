package com.learnreactiveprogramming.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

public class FluxAndMonoGeneratorService {
    public Flux<String> nameFlux() {
        return Flux.fromIterable(List.of("alex", "ben", "chole"));
    }

    public Flux<String> nameFlux_map() {
        return Flux.fromIterable(List.of("alex", "ben", "chole")).map(String::toUpperCase);
    }

    public Flux<String> nameFlux_map_filter(int StringLength) {
        return Flux.fromIterable(List.of("alex", "ben", "chole")).map(String::toUpperCase).filter(name -> name.length() > StringLength);
    }

    public Flux<String> nameFlux_flatMap(int StringLength) {
        return Flux.fromIterable(List.of("alex", "ben", "chole")).map(String::toUpperCase).filter(name -> name.length() > StringLength).flatMap(s -> splitString(s)).log();
    }

    public Flux<String> nameFlux_flatMap_async(int StringLength) {
        return Flux.fromIterable(List.of("alex", "ben", "chole")).map(String::toUpperCase).filter(name -> name.length() > StringLength).flatMap(s -> splitString_WITHdELAY(s)).log();
    }

    public Flux<String> nameFlux_ConCatMap(int StringLength) {
        return Flux.fromIterable(List.of("alex", "ben", "chole")).map(String::toUpperCase).filter(name -> name.length() > StringLength).concatMap(s -> splitString_WITHdELAY(s)).log();
    }

    public Flux<String> nameFlux_immutability() {
        var namesFlux = Flux.fromIterable(List.of("alex", "ben", "chole"));
        namesFlux.map(String::toUpperCase);
        return namesFlux;
    }

    public Flux<String> splitString(String name) {
        var charArray = name.split("");
        return Flux.fromArray(charArray);
    }

    public Flux<String> splitString_WITHdELAY(String name) {
        var charArray = name.split("");
        var delay = new Random().nextInt(1000);
        return Flux.fromArray(charArray).delayElements(Duration.ofMillis(delay));
    }

    public Mono<String> nameMono() {
        return Mono.just("alex-Mono").log();
    }

    public Mono<String> namesMono_map_filter(int stingLength) {
        return Mono.just("alex")
                .map(String::toUpperCase)
                .filter(s -> s.length() > stingLength);
    }

    public Mono<List<String>> namesMono_flatMap_filter(int stingLength) {
        return Mono.just("alex")
                .map(String::toUpperCase)
                .filter(s -> s.length() > stingLength)
                .flatMap(this::splitStringMono);
    }

    public Flux<String> namesMono_flatMapMany(int stringLength) {
        return Mono.just("alex").map(String::toUpperCase).filter(s -> s.length() > stringLength)
                .flatMapMany(this::splitString);
    }

    private Mono<List<String>> splitStringMono(String s) {
        return Mono.just(List.of(s.split("")));
    }

    //Transform

    public Flux<String> nameFlux_Transform(int StringLength) {
        Function<Flux<String>, Flux<String>> splitString = name -> name.map(String::toUpperCase).filter(_name -> _name.length() > StringLength);
        return Flux.fromIterable(List.of("alex", "ben", "chole")).transform(splitString).flatMap(s -> splitString(s)).defaultIfEmpty("default").log();
    }

    public Flux<String> nameFlux_Transform_switch_if_empty(int StringLength) {

        Function<Flux<String>, Flux<String>> splitString = name -> name.map(String::toUpperCase).filter(_name -> _name.length() > StringLength).flatMap(s -> splitString(s));
        var defaultFlux = Flux.just("default").transform(splitString);
        return Flux.fromIterable(List.of("alex", "ben", "chole"))
                .transform(splitString)
                .switchIfEmpty(defaultFlux)
                .log();
    }

    //Concat and concatWith

    public Flux<String> explore_concat() {
        var abcFlux = Flux.just("A", "B", "C");
        var defFlux = Flux.just("D", "E", "F");

        return Flux.concat(abcFlux, defFlux);
    }

    public Flux<String> explore_concat_with() {
        var abcFlux = Flux.just("A", "B", "C");
        var defFlux = Flux.just("D", "E", "F");
        return abcFlux.concatWith(defFlux).log();
    }

    public Flux<String> explore_concatWith_MOno() {
        var aMono = Mono.just("A");
        var bMono = Mono.just("B");
        return aMono.concatWith(bMono);
    }

    public Flux<String> explore_merge() {
        var abcFlux = Flux.just("A", "B", "C")
                .delayElements(Duration.ofMillis(100));
        var defFlux = Flux.just("D", "E", "F")
                .delayElements(Duration.ofMillis(125));
        return Flux.merge(abcFlux, defFlux);
    }

    public Flux<String> explore_mergeWith() {
        var abcFlux = Flux.just("A", "B", "C")
                .delayElements(Duration.ofMillis(100));
        var defFlux = Flux.just("D", "E", "F")
                .delayElements(Duration.ofMillis(125));
        return abcFlux.mergeWith(defFlux);
    }

    public Flux<String> explore_merge_withMono() {
        var aMono = Mono.just("A");
        var bMono = Mono.just("B");

        return aMono.mergeWith(bMono);
    }

    public Flux<String> explore_mergeSeq() {
        var abcFlux = Flux.just("A", "B", "C")
                .delayElements(Duration.ofMillis(100));
        var defFlux = Flux.just("D", "E", "F")
                .delayElements(Duration.ofMillis(125));
        return Flux.mergeSequential(abcFlux,defFlux);
    }


    public static void main(String[] args) {
        FluxAndMonoGeneratorService fluxAndMonoGeneratorService = new FluxAndMonoGeneratorService();
//        fluxAndMonoGeneratorService.nameFlux().subscribe(name -> System.out.println("Name: " + name));
//        fluxAndMonoGeneratorService.nameMono().subscribe(name -> System.out.println("Mono Name is : " + name));
    }

}
