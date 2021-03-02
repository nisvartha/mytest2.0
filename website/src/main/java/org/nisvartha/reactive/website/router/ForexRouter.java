package org.nisvartha.reactive.website.router;

import org.nisvartha.reactive.website.handler.ForexHandler;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
@EnableSwagger2
public class ForexRouter {
	private String PATHURL="/forex";
	
	@Bean
	public RouterFunction<ServerResponse> route(ForexHandler handler) {
		return RouterFunctions
				.route(RequestPredicates.GET(PATHURL).and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
						handler::getForexList)
				.andRoute(
						RequestPredicates.GET(PATHURL+"{id}").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
						handler::getForexById)
				.andRoute(RequestPredicates.POST(PATHURL).and(RequestPredicates.accept(MediaType.APPLICATION_JSON))
						.and(RequestPredicates.contentType(MediaType.APPLICATION_JSON)), handler::addForex)
				.andRoute(RequestPredicates.PUT(PATHURL).and(RequestPredicates.accept(MediaType.APPLICATION_JSON))
						.and(RequestPredicates.contentType(MediaType.APPLICATION_JSON)), handler::updateForex)
				.andRoute(RequestPredicates.DELETE(PATHURL).and(RequestPredicates.accept(MediaType.APPLICATION_JSON))
						.and(RequestPredicates.contentType(MediaType.APPLICATION_JSON)), handler::deleteForex)
				.andRoute(RequestPredicates.GET("/forex/fromCur/{fromCur}/toCur/{toCur}")
						.and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), handler::getForexByFromCurToCur);
		
	}
}
 