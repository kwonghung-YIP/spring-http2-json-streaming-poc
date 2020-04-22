package org.hung.web;

import java.time.Duration;

import org.hung.pojo.Person;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;

@RestController
public class DummyController {

	@GetMapping("/echo")
	public String echo() {
		return "echo";
	}
	
	@GetMapping(value="/streaming",produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	public Flux<String> jsonStreaming() {
		return Flux.just("A","B","C","D","E").log();
	}
	
	//public Flux<Person> hotstreaming() {		
	//}
	
	@GetMapping(value="/interval",produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	public Flux<Person> interval() {
		Flux<Long> interval = Flux.interval(Duration.ofSeconds(2));
		return interval.map(l -> {
			Person p = new Person();
			p.setRef(l);
			p.setFirstName("John");
			p.setLastName("Doe");
			return p;
		}).log();
	}

	@GetMapping(value="/interval2",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<Long> interval2() {
		Flux<Long> interval = Flux.interval(Duration.ofSeconds(2)).log();
		return interval;
	}

}
