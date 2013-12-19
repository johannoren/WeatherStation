package se.noren.weatherstation.adapter;

import java.io.IOException;
import java.net.URI;
import java.util.Map;

import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public class CouchDBAdapter extends RestTemplate {

	private final static String BASE_URL = "http://johanhtpc:5984";

	private MediaType defaultResponseContentType;

	public CouchDBAdapter() {
		setDefaultResponseContentType("text/plain");
	}

	public void setDefaultResponseContentType(String defaultResponseContentType) {
		this.defaultResponseContentType = MediaType
				.parseMediaType(defaultResponseContentType);
	}

	@Override
	protected <T> T doExecute(URI url, HttpMethod method,
			RequestCallback requestCallback,
			final ResponseExtractor<T> responseExtractor)
			throws RestClientException {

		return super.doExecute(url, method, requestCallback,
				new ResponseExtractor<T>() {
					public T extractData(ClientHttpResponse response)
							throws IOException {
						if (response.getHeaders().getContentType() == null
								&& defaultResponseContentType != null) {
							response.getHeaders().setContentType(
									defaultResponseContentType);
						}

						return responseExtractor.extractData(response);
					}
				});
	}

	protected <T> T get(String path, Class<T> type,  Map<String, String> params) {

		T result = getForObject(path, type, params);
		return result;
	}

	
	protected String post(String path, MultiValueMap<String, String> parameters) {

		String result = postForObject(BASE_URL + path, parameters, String.class);
		return result;
	}
}
