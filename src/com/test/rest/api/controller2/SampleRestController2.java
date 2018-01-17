package com.test.rest.api.controller2;

import com.test.rest.api.spec.RequestMapping;

public class SampleRestController2 {

	@RequestMapping(path="/hello", method="GET", consumes="application/json", produces="application/json")
	public String hello() {
		return "hello";
	}
}
