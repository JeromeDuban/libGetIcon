package fr.jeromeduban.libgeticon;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import fr.jeromeduban.getstoreicon.Manager;
import fr.jeromeduban.getstoreicon.Parameter;

public class MainActivity extends ActionBarActivity {
	
	
	public TextView txt;

    private String[] packages = {"com.facebo  &=1ok.%20" ,"com.netmarble.mherosg","com.netmarble.mherosgb","com.facebook.katana","com.ustwo.monumentvalley","com.facebook.katana"};
    private String[] result = {"Error", "Error", "Correct", "Correct", "Correct", "Correct"};

	private Manager m;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Drawable defaultIcon = this.getResources().getDrawable(R.drawable.ic_launcher);
		// OR
		defaultIcon = this.getResources().getDrawable(R.mipmap.ic_launcher2);

		Parameter param = new Parameter().setCache(true).setSize(100).setDefaultIcon(defaultIcon);

		m = new Manager(this, param);
        cleanCache(m);

		LinearLayout container = (LinearLayout) findViewById(R.id.container);

        for (int i = 0; i < packages.length; i++){

            LayoutInflater in = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View card = in.inflate(R.layout.card, container, false);

            TextView tv = (TextView) card.findViewById(R.id.packageName);
            tv.setText(packages[i]);

            TextView expectedResult = (TextView) card.findViewById(R.id.result);
            expectedResult.setText(result[i]);
            if (result[i].equals("error")) expectedResult.setTextColor(Color.RED);
            else expectedResult.setTextColor(Color.GREEN);

            ImageView iv = (ImageView) card.findViewById(R.id.icon);
            
            m.download(iv, packages[i]);

            container.addView(card);
        }




	}

    private void cleanCache(Manager m){
        m.deleteCache(this);
        Toast.makeText(MainActivity.this, "Cache cleaned", Toast.LENGTH_LONG).show();
    }


	@Override
	protected void onDestroy() {
		super.onDestroy();

		if (m != null)
			m.cancel();
	}
}