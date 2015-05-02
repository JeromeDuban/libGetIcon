package fr.jeromeduban.getstoreicon;

import android.content.Context;
import android.widget.ImageView;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by jduban on 02/05/15.
 */
public class Manager {

    private Context c;
    private ArrayList<LoadImage> list;
    private Parameter param;

    public Manager(Context context){
        this.c = context;
        list = new ArrayList<>();
    }

    public Manager(Context context, Parameter param){
        this.c = context;
        this.param = param;
        list = new ArrayList<>();
    }

    public void download(ImageView image, String packageName){
        LoadImage l = new LoadImage(c,image, param);
        l.execute(packageName);

        list.add(l);
    }

    public void setParameter(Parameter param){
        this.param = param;
    }




}
