package fr.jeromeduban.libgeticon;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import fr.jeromeduban.getstoreicon.Manager;

public class MainActivity extends Activity {
	
	
	public TextView txt;
	public ImageView image;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		image = (ImageView) findViewById(R.id.image);

		txt = (TextView)findViewById(R.id.tv);		

		Manager m = new Manager();

		m.download(image,"com.facebook.katana");

	}
}