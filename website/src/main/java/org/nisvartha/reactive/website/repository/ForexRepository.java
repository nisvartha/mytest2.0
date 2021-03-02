package org.nisvartha.reactive.website.repository;

import org.nisvartha.reactive.website.entity.Forex;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import reactor.core.publisher.Mono;

public interface ForexRepository extends ReactiveCrudRepository<Forex, String> {
	Mono<Forex> findByFromCurAndToCur(String fromCur, String toCur);
}