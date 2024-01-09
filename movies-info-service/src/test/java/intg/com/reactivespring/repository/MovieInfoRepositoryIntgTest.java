package com.reactivespring.repository;

import com.reactivespring.domain.MovieInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@DataMongoTest
@ActiveProfiles("test")
class MovieInfoRepositoryIntgTest {

    @Autowired
    MovieInfoRepository movieInfoRepository;

    @BeforeEach
    void setUp() {
        var movieinfos = List.of(new MovieInfo(null, "Batman Begins", 2005, List.of("Christian Bale", "Michael Cane"), LocalDate.parse("2005-06-15")), new MovieInfo(null, "The Dark Knight", 2008, List.of("Christian Bale", "HeathLedger"), LocalDate.parse("2008-07-18")), new MovieInfo("abc", "Dark Knight Rises", 2012, List.of("Christian Bale", "Tom Hardy"), LocalDate.parse("2012-07-20")));
        movieInfoRepository.saveAll(movieinfos).blockLast();
    }

    @Test
    void findAll() {
        var movieInfoFlux = movieInfoRepository.findAll();
        StepVerifier.create(movieInfoFlux).expectNextCount(3).verifyComplete();
    }

    @Test
    void findById() {
        var moviesInfoFlux = movieInfoRepository.findById("abc");
        var moviesInfoFluxs = movieInfoRepository.findById("abc").block();
        StepVerifier.create(moviesInfoFlux).assertNext(movieInfo -> assertEquals("Dark Knight Rises", movieInfo.getName()));
    }

    @Test
    void saveMovieInfo() {
        var movieInfo = new MovieInfo(null, "Dark Knight Rises", 2012, List.of("Christian Bale", "Tom Hardy"), LocalDate.parse("2012-07-20"));

        var moviesInfoMono = movieInfoRepository.save(movieInfo);

        StepVerifier.create(moviesInfoMono)
                .assertNext(movieInfo1 -> {
                    assertNotNull(movieInfo1.getMovieInfoId());
                    assertEquals(movieInfo.getName(), movieInfo1.getName());
                })
                .verifyComplete();
    }

    @Test
    void updateMovieInfo() {
        var moviesInfo = movieInfoRepository.findById("abc").block();
        moviesInfo.setYear(9999);

        var movieInfoMono = movieInfoRepository.save(moviesInfo);

        StepVerifier.create(movieInfoMono)
                .assertNext(movieInfo -> {
                    assertEquals(9999, movieInfo.getYear());
                })
                .verifyComplete();
    }

    @Test
    void deleteMovieInfo() {
        movieInfoRepository.deleteById("abc").log().block();
        var movieInfoFlux = movieInfoRepository.findAll().log();

        StepVerifier.create(movieInfoFlux)
                .expectNextCount(2)
                .verifyComplete();
    }
}