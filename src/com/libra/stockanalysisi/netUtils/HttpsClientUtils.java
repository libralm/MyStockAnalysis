package com.libra.stockanalysisi.netUtils;

import android.accounts.NetworkErrorException;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class HttpsClientUtils {

	private HttpClient client;

	public HttpsClientUtils(int pConnectionTimeout_ms, int pSoTimeout_ms) {
		HttpParams my_httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(my_httpParams, pConnectionTimeout_ms);
		HttpConnectionParams.setSoTimeout(my_httpParams, pSoTimeout_ms);
		this.client = new DefaultHttpClient(my_httpParams);
	}

	public String get(String uri) throws NetworkErrorException {
		HttpGet get = new HttpGet(uri);
		String response = sendDataByGet(get);
		return response;
	}

	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@SuppressLint("NewApi")
	public InputStream postMyDownloadRequest(String pUri) throws IOException {
		HttpPost m_HttpPost = new HttpPost(pUri);
		try {
			HttpResponse postRet = client.execute(m_HttpPost);
			if (postRet.getStatusLine().getStatusCode() == 200) {
				InputStream m_Content = postRet.getEntity().getContent();
				return m_Content;
			} else {
				return null;
			}
		} catch (ClientProtocolException e) {
			throw new IOException(e);
		} catch (IOException e) {
			throw new IOException(e);
		}
	}

	public String sendDataByPost(HttpPost post, List<NameValuePair> datas) throws ClientProtocolException, IOException {
		String result = "";
		post.setEntity(new UrlEncodedFormEntity(datas, HTTP.UTF_8));
		HttpResponse response = client.execute(post);
		if (response.getStatusLine().getStatusCode() == 200) {
			result = EntityUtils.toString(response.getEntity());
			return result;
		} else {
			return null;
		}
	}

	public String sendDataByGet(HttpGet get) throws NetworkErrorException {
		HttpResponse resp = null;
		String result = "";

		try {
			resp = client.execute(get);
			if (resp.getStatusLine().getStatusCode() == 200) {
				result = EntityUtils.toString(resp.getEntity(),"gb2312");
				return result;
			} else {
				throw new RuntimeException();
			}
		} catch (ClientProtocolException e) {
			throw new NetworkErrorException(e);
		} catch (ParseException e) {
			throw new NetworkErrorException(e);
		} catch (IOException e) {
			throw new NetworkErrorException(e);
		}
	}
}
