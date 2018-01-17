package com.test.rest.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.test.rest.api.spec.HttpRequest;
import com.test.rest.api.spec.HttpRequestBuilder;
import com.test.rest.api.spec.HttpRequestProcessor;
import com.test.rest.api.spec.ResourceConfiguration;

public class HttpServer {
	static Logger logger = Logger.getLogger("HttpServer");

	public static void main(String args[]) throws Exception {
		// Set the port number.
		int port = 8080;
		if (args != null && args.length != 0) {
			try {
				port = Integer.parseInt(args[0]);
			} catch (NumberFormatException nfe) {
				logger.log(Level.WARNING, "Invalid port number is passed. Using Default port number (" + port + ").");
			}
		}

		// package to scan for rest apis
		String[] packageNames = { "com.test.rest.api.controller", "com.test.rest.api.controller2" };

		logger.log(Level.INFO, "Starting Http Server on port " + port);
		ResourceConfiguration config = null;
		try {
			config = new ResourceConfiguration();
			config.setPackagesToScan(packageNames);
			config.configure();
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Level.SEVERE, "Failed to start server due to above reasons");
			System.exit(1);
		}

		ServerSocket serverSocket = new ServerSocket(port);
		logger.log(Level.INFO, "Listening for connection on port " + port);
		logger.log(Level.INFO, "server started successfully.");
		while (true) {
			Socket clientSocket = serverSocket.accept();
			// Construct an object to process the HTTP request message.
			HttpRequest request = new HttpRequestBuilder(clientSocket).build();
			logger.log(Level.FINER, "Http Request: " + request);
			HttpRequestProcessor requestProcessor = new HttpRequestProcessor(config);
			requestProcessor.processRequest(request);
		}
	}
}
