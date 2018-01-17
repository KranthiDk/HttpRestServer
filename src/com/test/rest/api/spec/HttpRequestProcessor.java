package com.test.rest.api.spec;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HttpRequestProcessor extends Thread {

	Logger logger = Logger.getLogger("HttpRequestProcessor");
	private HttpRequest request;
	private ResourceConfiguration config;

	public HttpRequestProcessor(ResourceConfiguration config) {
		this.config = config;
	}

	public void run() {
		String httpResponse = "";
		OutputStream response = null;
		try {

			logger.log(Level.INFO, "Processing request!!");
			Resource resource = config.find(request.getRequestPath());
			if (resource == null) {
				httpResponse = "HTTP/1.1 404 \r\n\r\n <h1>Not Found</h1>";
			} else if (!request.getMethod().equals(resource.getHttpMethod())) {
				logger.log(Level.SEVERE, "Request Method '" + request.getMethod() + "' is not supported.");
				httpResponse = "HTTP/1.1 405 \r\n\r\n <h1>Method Not Allowed</h1>";

			} else {
				Class cls = resource.getClassType();
				Method m = cls.getMethod(resource.getMethod().getName());
				String returnValue = (String) m.invoke(cls.newInstance());
				httpResponse = "HTTP/1.1 200 OK\r\n\r\n" + returnValue;

			}

			response = request.getClientSocket().getOutputStream();
			response.write(httpResponse.getBytes("UTF-8"));
		} catch (Exception e) {
			logger.log(Level.WARNING, "Unable to process request. Reason: " + e.getMessage());
			httpResponse = "HTTP/1.1 500\r\n\r\n" + "Internal Server Error";
		} finally {
			try {
				if (response != null)
					response.close();
				request.getClientSocket().close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public void processRequest(HttpRequest request) {
		this.request = request;
		this.run();
	}

}
