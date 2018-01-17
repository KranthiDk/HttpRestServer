package com.test.rest.api.controller;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.test.rest.api.spec.RequestMapping;

public class SampleRestController{
	Logger logger = Logger.getLogger("SampleRestController");
	@RequestMapping(path="/hi", method="GET", produces="application/json")
	public String hi() {
		logger.log(Level.FINER,"In SampleRestController.Hi Method");
		return "{\"status\":1}";
	}
}

