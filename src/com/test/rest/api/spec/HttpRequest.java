package com.test.rest.api.spec;

import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpRequest {
	private String method;
	private String uri;
	private String requestPath;
	private Map<String, String> headers = new HashMap<String, String>();
	private Map<String, String> requestParams = new HashMap<String, String>();
	private String payload;
	private Socket clientSocket;

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}

	public Map<String, String> getRequestParams() {
		return requestParams;
	}

	public void setRequestParams(Map<String, String> requestParams) {
		this.requestParams = requestParams;
	}

	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}

	public String getRequestPath() {
		return requestPath;
	}

	public void setRequestPath(String requestPath) {
		this.requestPath = requestPath;
	}

	@Override
	public String toString() {
		return "HttpRequest [method=" + method + ", uri=" + uri + ", requestPath=" + requestPath + ", headers="
				+ headers + ", requestParams=" + requestParams + ", payload=" + payload + "]";
	}

	public void setClientSocket(Socket clientSocket) {
		this.clientSocket = clientSocket;

	}

	public Socket getClientSocket() {
		return this.clientSocket;
	}

}
