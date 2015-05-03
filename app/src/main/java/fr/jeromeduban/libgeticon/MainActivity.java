package fr.jeromeduban.libgeticon;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import fr.jeromeduban.getstoreicon.Manager;
import fr.jeromeduban.getstoreicon.Parameter;

public class MainActivity extends Activity {
	
	
	public TextView txt;
	public ImageView image1;
	public ImageView image2;
	public ImageView image3;
	public ImageView image4;
	public ImageView image5;
	public ImageView image6;

	private Manager m;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		image1 = (ImageView) findViewById(R.id.image1);
		image2 = (ImageView) findViewById(R.id.image2);
		image3 = (ImageView) findViewById(R.id.image3);
		image4 = (ImageView) findViewById(R.id.image4);
		image5 = (ImageView) findViewById(R.id.image5);
		image6 = (ImageView) findViewById(R.id.image6);

		txt = (TextView)findViewById(R.id.tv);




		Parameter param = new Parameter().setCache(true).setSize(100);

		m = new Manager(this, param);



		m.deleteCache(this); //FIXME : to be removed

		m.download(image1,"com.facebook.katana");

		m.setParameter(param.setSize(50));
		
		m.download(image2,"com.netmarble.mherosgb");
		m.download(image3,"com.netmarble.mherosgb");
		m.download(image4,"com.facebook.katana");
		m.download(image5,"com.ustwo.monumentvalley");
		m.download(image6,"com.facebook.katana");

	}


	@Override
	protected void onDestroy() {
		super.onDestroy();

		if (m != null)
			m.cancel();
	}
}