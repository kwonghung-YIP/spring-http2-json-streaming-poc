package org.hung.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//@RestController
public class DummyController {

	@GetMapping("/echo")
	public String echo() {
		return "echo";
	}

}
