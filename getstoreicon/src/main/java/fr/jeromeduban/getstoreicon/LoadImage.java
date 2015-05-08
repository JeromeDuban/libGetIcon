package fr.jeromeduban.getstoreicon;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoadImage extends AsyncTask<String, Void, Bitmap> {

	private final Context c;
	private ImageView image;
	private Parameter param;
	private String packageName;
	private OnBitmapLoaded callback;

	public LoadImage(Context c, OnBitmapLoaded callback, ImageView image, Parameter param) {
		this.c = c;
		this.image = image;
		this.param = param;
		this.callback = callback;
	}

	@Override
	protected Bitmap doInBackground(String... params) {


		// TODO : check params[0] exists
		File f = new File(c.getCacheDir(),params[0]);
		packageName = params[0];

		if (!f.exists()){
			StringBuilder response = new StringBuilder();

			String storeUrl = "https://play.google.com/store/apps/details?id=" + params[0];



			try {
				// GET play store HTML
				DefaultHttpClient client = new DefaultHttpClient();
				HttpGet httpGet = new HttpGet(storeUrl);
				HttpResponse execute;
				InputStream content;

				execute = client.execute(httpGet);

				if (execute.getStatusLine().getStatusCode() != 200){
					Log.d("LibGetIcon","Response code != 200");
					return null;
				}


				content = execute.getEntity().getContent();
				BufferedReader buffer = new BufferedReader(
						new InputStreamReader(content));
				String s;
				while ((s = buffer.readLine()) != null) {
					response.append(s);
					response.append("\n");
				}
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}

			String start = "<div class=\"cover-container\"> <img class=\"cover-image\" src=\"";
			String end = "\" alt=\"Cover art\"";

			String url = null;

			Pattern pattern = Pattern.compile(start+"(.*?)"+end);
			Matcher matcher = pattern.matcher(response.toString());
			while (matcher.find()) {
				url = matcher.group(1);
			}



//			// Parse html to get the image url
//			Document doc = Jsoup.parse(response.toString());
//			Elements elt = doc.getElementsByClass("cover-container");
//			Element img = elt.select("img").first();
//			String url = img.absUrl("src");


			// Sizee selection
			if (param != null && url !=null)
				url = url.replace("w300","w"+Integer.toString(param.getSize()));


			try {
				// Download image from URL
				HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
				connection.connect();
				InputStream input = connection.getInputStream();

				Bitmap b = BitmapFactory.decodeStream(input);

				if (param != null && param.getCache() && b!=null){
					// Save file to cache
					FileOutputStream fOut = new FileOutputStream(f);
					b.compress(Bitmap.CompressFormat.PNG, 85, fOut);
					fOut.flush();
					fOut.close();
				}

				return b;

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else{
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inPreferredConfig = Bitmap.Config.ARGB_8888;

			return BitmapFactory.decodeFile(f.getAbsolutePath(), options);
		}

		return  null;
	}
	
	@Override
	protected void onPostExecute(Bitmap result) {

		// Set Image
		if (result != null){
			image.setImageBitmap(result);
		}
		else if (param != null && param.getDefaultIcon() != null)
			image.setImageDrawable(param.getDefaultIcon());

		// Send result to manager
		if (callback != null)
			callback.onBitmapLoaded(this, result, packageName);

	}

	public ImageView getImageView(){
		return image;
	}

	public String getPackageName(){
		return packageName;
	}
}

