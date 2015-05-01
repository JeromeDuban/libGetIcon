package fr.jeromeduban.getstoreicon;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.os.AsyncTask;
import android.util.Log;

public class LoadImage extends AsyncTask<TaskCompleteBitmap, Void, Result> {
	
	@Override
	protected Result doInBackground(TaskCompleteBitmap... params) {
		StringBuilder response = new StringBuilder();
		
		// GET file from server
		DefaultHttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet("https://play.google.com/store/apps/details?id=com.facebook.katana");
		HttpResponse execute;
		InputStream content;
		try {
			execute = client.execute(httpGet);
			content = execute.getEntity().getContent();
			BufferedReader buffer = new BufferedReader(
					new InputStreamReader(content));
			String s;
			while ((s = buffer.readLine()) != null) {
				response.append(s);
				response.append("\n");
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		
		//Check answer
//		System.out.println(response.toString());
		return new Result(params[0], response.toString());
	}
	
	@Override
	protected void onPostExecute(Result result) {
		Document doc = Jsoup.parse(result.getResponse());
		Elements elt = doc.getElementsByClass("cover-container");
		Element img = elt.select("img").first();
		String src = img.absUrl("src");
		if(result.getCallback() != null)
			result.getCallback().onTaskCompleteBitmap(src, null);
		else
			Log.i("TAG", "Callback null");
	}
}

