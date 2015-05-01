package fr.jeromeduban.libgeticon;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import fr.jeromeduban.getstoreicon.LoadImage;
import fr.jeromeduban.getstoreicon.TaskCompleteBitmap;

public class MainActivity extends Activity implements TaskCompleteBitmap {
	
	
	public TextView txt;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		txt = (TextView)findViewById(R.id.tv);		
		LoadImage l = new LoadImage();
		l.execute(this);
		
	}

	@Override
	public void onTaskCompleteBitmap(String src, ImageView icon) {
		Toast.makeText(this, src, Toast.LENGTH_SHORT).show();
	}
}