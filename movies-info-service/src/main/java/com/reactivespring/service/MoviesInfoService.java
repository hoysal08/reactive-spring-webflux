package com.reactivespring.service;

import com.reactivespring.domain.MovieInfo;
import com.reactivespring.repository.MovieInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MoviesInfoService {

    @Autowired
    private MovieInfoRepository movieInfoRepository;

    public Mono<MovieInfo> addMovieInfo(MovieInfo movieInfo) {
        return movieInfoRepository.save(movieInfo);
    }

    public Flux<MovieInfo> getAllMovieInfos() {
        return movieInfoRepository.findAll();
    }

    public Mono<MovieInfo> getMovieById(String movieId) {
        return movieInfoRepository.findById(movieId);
    }

    public Mono<MovieInfo> updateMovieInfo(String movieId, MovieInfo updatedMovieInfo) {
        return movieInfoRepository.findById(movieId).flatMap(movieInfo -> {
            movieInfo.setCast(updatedMovieInfo.getCast());
            movieInfo.setYear(updatedMovieInfo.getYear());
            movieInfo.setName(updatedMovieInfo.getName());
            movieInfo.setRelease_date(updatedMovieInfo.getRelease_date());
            return movieInfoRepository.save(movieInfo);
        });
    }

    public Mono<Void> deleteMovieById(String movieId) {
        return movieInfoRepository.deleteById(movieId);
    }

    public Flux<MovieInfo> getMovieByYear(Integer year) {
        return movieInfoRepository.findByYear(year);
    }
}
