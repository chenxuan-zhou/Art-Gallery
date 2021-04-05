package ca.mcgill.ecse321.artgallery;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * This is class works like a tool box to connect with backend.
 * @author: Tracy
 */
public class HttpUtils {
    public static final String DEFAULT_BASE_URL = "https://artgallery-11.herokuapp.com/";

    private static String baseUrl;
    private static AsyncHttpClient client = new AsyncHttpClient();

    /**
     * Address of the backend
     */
    static {
        baseUrl = DEFAULT_BASE_URL;
    }

    public static String getBaseUrl() {
        return baseUrl;
    }

    public static void setBaseUrl(String baseUrl) {
        HttpUtils.baseUrl = baseUrl;
    }

    /**
     * REST API - GET method
     * @param url a relative url
     * @param params request parameter
     * @param responseHandler AsyncHttpResponseHandler
     */
    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    /**
     * REST API - POST method
     * @param url a relative url
     * @param params request parameter
     * @param responseHandler AsyncHttpResponseHandler
     */
    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    /**
     * REST API - PUT method
     * @param url a relative url
     * @param params request parameter
     * @param responseHandler AsyncHttpResponseHandler
     */
    public static void put(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.put(getAbsoluteUrl(url), params, responseHandler);
    }

    /**
     * REST API - GET method
     * @param url an absolute url
     * @param params request parameter
     * @param responseHandler AsyncHttpResponseHandler
     */
    public static void getByUrl(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(url, params, responseHandler);
    }

    /**
     * REST API - POST method
     * @param url an absolute url
     * @param params request parameter
     * @param responseHandler AsyncHttpResponseHandler
     */
    public static void postByUrl(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(url, params, responseHandler);
    }

    /**
     * Get an absolute url by a relative url
     * @param relativeUrl
     * @return the complete url which contains all the paths
     */
    private static String getAbsoluteUrl(String relativeUrl) {
        return baseUrl + relativeUrl;
    }

}
