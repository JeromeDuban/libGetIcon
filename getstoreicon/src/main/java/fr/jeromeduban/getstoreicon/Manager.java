package fr.jeromeduban.getstoreicon;

import android.widget.ImageView;

/**
 * Created by jduban on 02/05/15.
 */
public class Manager {

    public Manager(){
        //TODO Add cache parameter
        //
    }

    public void download(ImageView image, String packageName){
        LoadImage l = new LoadImage(image);
        l.execute(packageName);
    }

}
