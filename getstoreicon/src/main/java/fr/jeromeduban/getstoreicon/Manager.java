package fr.jeromeduban.getstoreicon;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by jduban on 02/05/15.
 */
public class Manager implements OnBitmapLoaded{

    private Context c;
    private ArrayList<LoadImage> listTask;
    private ArrayList<String> listPackage;
    private ArrayList<ImageView> listImage;
    private Parameter param;

    public Manager(Context context){
        this.c = context;
        listTask = new ArrayList<>();
        listImage = new ArrayList<>();
        listPackage = new ArrayList<>();
    }

    public Manager(Context context, Parameter param){
        this.c = context;
        this.param = param;
        listTask = new ArrayList<>();
        listImage = new ArrayList<>();
        listPackage = new ArrayList<>();
    }

    public void download(ImageView image, String packageName){

        LoadImage l = new LoadImage(c,this,image, new Parameter(param));
        l.execute(packageName);

        listTask.add(l);
        listImage.add(image);
        listPackage.add(packageName);
    }

    public void setParameter(Parameter param){
        this.param = param;
    }

    @Override
    public void onBitmapLoaded(LoadImage task, Bitmap b, String packageName) {
//        Toast.makeText(c, "Bitmap Loaded " + packageName, Toast.LENGTH_SHORT).show();


        int index = listTask.indexOf(task);

        // Delete current task from list
        if (index != -1){
            listTask.remove(index);
            listPackage.remove(index);
            listImage.remove(index);
        }
        if (b != null){
            // Looks if bitmap can be used by another task
            for (int i = 0; i < listTask.size(); i++){
                if (listPackage.get(i).equals(packageName)){
                    listTask.get(i).cancel(true);
                    listImage.get(i).setImageBitmap(b);

                    listTask.remove(i);
                    listPackage.remove(i);
                    listImage.remove(i);
                }
            }
        }
    }

    public void cancel(){
        for (LoadImage l : listTask){
            l.cancel(true);
        }
    }

    public void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            if (dir != null && dir.isDirectory()) {
                deleteDir(dir);
            }
        } catch (Exception ignored) {}
    }

    public boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (String aChildren : children) {
                boolean success = deleteDir(new File(dir, aChildren));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

}
