package fr.jeromeduban.getstoreicon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

public class LoadImage extends AsyncTask<String, Void, Bitmap> {

	private ImageView image;

	public LoadImage(ImageView image) {
		this.image = image;
	}

	@Override
	protected Bitmap doInBackground(String... params) {
		StringBuilder response = new StringBuilder();

		//TODO : check parameter

		// GET file from server
		DefaultHttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet("https://play.google.com/store/apps/details?id=" + params[0]);
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

		Document doc = Jsoup.parse(response.toString());
		Elements elt = doc.getElementsByClass("cover-container");
		Element img = elt.select("img").first();
		String src = img.absUrl("src");


		try {
			HttpURLConnection connection = (HttpURLConnection) new URL(src).openConnection();
			connection.connect();
			InputStream input = connection.getInputStream();
			Bitmap d = BitmapFactory.decodeStream(input);

			return d;

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	protected void onPostExecute(Bitmap result) {

		if (result != null){
			image.setImageBitmap(result);
		}

	}
}

