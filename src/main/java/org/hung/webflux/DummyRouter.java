package org.hung.webflux;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.netty.http.server.HttpServer;

@Configuration
public class DummyRouter {
	
	@Bean
	public RouterFunction<ServerResponse> route(DummyHandler handler) {
		return RouterFunctions
				.route()
				  .GET("/echo", handler::echo)
				  .GET("/echo2", handler::echo2)
				  .GET("/interval", handler::interval)
				  .build();
	}
	
	//@Bean
	public HttpServer nettyHttpServer(DummyHandler handler) {
		RouterFunction<ServerResponse> routerFn = RouterFunctions
		  .route()
		    .GET("/echo",handler::echo)
		      .build();
		
		HttpHandler httpHandler = RouterFunctions.toHttpHandler(routerFn);
		
		ReactorHttpHandlerAdapter adapter = new ReactorHttpHandlerAdapter(httpHandler);
		
		HttpServer httpServer = 
				HttpServer.create()
				  .port(8088)
				  .metrics(true)
				  .handle(adapter);
		
		httpServer.bind().block();
				
		return httpServer;
	}

}
