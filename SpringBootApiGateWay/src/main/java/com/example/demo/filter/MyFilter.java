package com.example.demo.filter;

import java.util.List;

import org.jspecify.annotations.Nullable;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.RequestPath;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class MyFilter implements GlobalFilter, Ordered {

	/*
	 * @Override public Mono<Void> filter(ServerWebExchange exchange,
	 * GatewayFilterChain chain) {
	 * 
	 * System.out.println("Global Filter ivock");
	 * 
	 * @Nullable String requestAuth =
	 * exchange.getRequest().getHeaders().getFirst("Authorization");
	 * 
	 * if(requestAuth == null || !requestAuth.startsWith("Bearer ")) {
	 * exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED); return
	 * exchange.getResponse().setComplete(); }
	 * 
	 * return chain.filter(exchange);
	 * 
	 * }
	 */

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		System.out.println("Filter is Exicuted.");
		HttpHeaders requestHeader = exchange.getRequest().getHeaders();
		List<String> headerList = requestHeader.getVary();
		headerList.forEach(key -> {
			List<String> values = requestHeader.get(key);
			System.out.println("Key : " + key + " Values : " + values);
		});

		if (!headerList.contains("token")) {
			throw new RuntimeException("Invalid Request");
		}
		List<String> list = requestHeader.get("token");
		if (!list.get(0).equals("Rajesh1234")) {
			throw new RuntimeException("Invalid Token");
		}

		System.out.println("=====================================================================");
		return chain.filter(exchange);
	}

	@Override
	public int getOrder() {
		return -1;
	}

}
