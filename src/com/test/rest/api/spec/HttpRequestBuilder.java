package com.test.rest.api.spec;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class HttpRequestBuilder {
	private Socket clientSocket;
	private HttpRequest httpRequest = new HttpRequest();

	public HttpRequestBuilder(Socket clientSocket) {
		this.clientSocket = clientSocket;
		httpRequest.setClientSocket(clientSocket);
	}

	public HttpRequest build() {
		try {
			InputStream is = this.clientSocket.getInputStream();

			// Set up input stream filters.
			BufferedReader br = new BufferedReader(new InputStreamReader(is));

			// Get the request line of the HTTP request message.
			String requestLine = br.readLine();
			StringTokenizer tokens = new StringTokenizer(requestLine);
			this.httpRequest.setMethod(tokens.nextToken());
			String uri = tokens.nextToken();
			this.httpRequest.setUri(uri);
			// set request parameters
			String[] uriSplits = uri.split("\\?");
			this.httpRequest.setRequestPath(uriSplits[0]);
			if (uriSplits.length == 2) {
				this.httpRequest.setRequestParams(getQueryMap(uriSplits[1]));
			}

			// set the header lines.
			String headerLine = null;
			while ((headerLine = br.readLine()).length() != 0) {
				String[] hdrs = headerLine.split(":");
				this.httpRequest.getHeaders().put(hdrs[0], hdrs[1]);
			}

			// set payload

			StringBuilder payload = new StringBuilder();
			while (br.ready()) {
				payload.append((char) br.read());
			}
			this.httpRequest.setPayload(payload.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}

		return this.httpRequest;
	}

	public Map<String, String> getQueryMap(String query) {
		String[] params = query.split("&");
		Map<String, String> map = new HashMap<String, String>();
		for (String param : params) {
			String name = param.split("=")[0];
			String value = param.split("=")[1];
			map.put(name, value);
		}
		return map;
	}
}
