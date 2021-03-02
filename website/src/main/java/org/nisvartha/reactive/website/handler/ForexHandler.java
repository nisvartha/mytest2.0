package org.nisvartha.reactive.website.handler;

import org.nisvartha.reactive.website.entity.Forex;
import org.nisvartha.reactive.website.repository.ForexRepository;
import org.nisvartha.reactive.website.vo.ForexVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class ForexHandler {
	@Autowired
	private ForexRepository repository;
	
	public Mono<ServerResponse> getForexList(ServerRequest request) {
		Flux<Forex> forexList = repository.findAll();
		Mono<ServerResponse> notFound = ServerResponse.notFound().build();
		Flux<ForexVO> forexVoList = forexList.map(f -> {
			return new ForexVO(f.getId(), f.getFromCur(), f.getToCur(), f.getRateCur());
		});
		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(forexVoList, ForexVO.class).switchIfEmpty(notFound);
	}
	
	public Mono<ServerResponse> getForexById(ServerRequest request) {
		String forexId = request.pathVariable("id");
		Mono<ServerResponse> notFound = ServerResponse.notFound().build();
		Mono<Forex> forex = repository.findById(forexId);
		Mono<ForexVO> forexVo = Mono.from(forex.map(f -> {
			return new ForexVO(f.getId(), f.getFromCur(), f.getToCur(), f.getRateCur());
		}));
		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(forexVo, ForexVO.class)
				.switchIfEmpty(notFound);
	}
	public Mono<ServerResponse> getForexByFromCurToCur(ServerRequest request) {
		String fromCur = request.pathVariable("fromCur");
		String toCur = request.pathVariable("toCur");
		Mono<ServerResponse> notFound = ServerResponse.notFound().build();
		Mono<Forex> forex = repository.findByFromCurAndToCur(fromCur, toCur);
		Mono<ForexVO> forexVo = Mono.from(forex.map(f -> {
			return new ForexVO(f.getId(), f.getFromCur(), f.getToCur(), f.getRateCur());
		}));
		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(forexVo, ForexVO.class)
				.switchIfEmpty(notFound);
	}
	
	public Mono<ServerResponse> addForex(ServerRequest serverRequest) {
		Mono<ForexVO> forexVo = serverRequest.bodyToMono(ForexVO.class);
		Mono<ServerResponse> notFound = ServerResponse.notFound().build();
		Mono<Forex> forex = forexVo.map(f -> new Forex(f.getFromCur(), f.getToCur(), f.getRateCur()))
				.flatMap(repository::save);
		forexVo = Mono.from(forex.map(f -> {
			return new ForexVO(f.getId(), f.getFromCur(), f.getToCur(), f.getRateCur());
		}));
		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(forexVo, ForexVO.class).switchIfEmpty(notFound);
	}
	
	public Mono<ServerResponse> updateForex(ServerRequest serverRequest) {
		Mono<ForexVO> forexVo = serverRequest.bodyToMono(ForexVO.class);
		Mono<ServerResponse> notFound = ServerResponse.notFound().build();
		System.out.println("Just about to upate the details provided---------------->>>>>>");
		Mono<Forex> forex = forexVo.map(f -> new Forex(f.getId(), f.getFromCur(), f.getToCur(), f.getRateCur()))
				.flatMap(repository::save);
		forexVo = Mono.from(forex.map(f -> {
			return new ForexVO(f.getId(), f.getFromCur(), f.getToCur(), f.getRateCur());
		}));
		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(forexVo, ForexVO.class).switchIfEmpty(notFound);
	}
	
	public Mono<ServerResponse> deleteForex(ServerRequest serverRequest) {
		Mono<ForexVO> forexVo = serverRequest.bodyToMono(ForexVO.class);
		return forexVo.flatMap(f -> repository.findById(f.getId())).flatMap(f -> repository.delete(f))
				.then(ServerResponse.ok().build(Mono.empty()));
	}
	
}