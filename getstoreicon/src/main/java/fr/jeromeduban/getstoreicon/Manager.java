package fr.jeromeduban.getstoreicon;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by jduban on 02/05/15 .
 */

public class Manager extends OnBitmapLoaded{

    private Context c;
    private ArrayList<LoadImage> listTask;
    private ArrayList<String> listPackage;
    private ArrayList<ImageView> listImage;
    private Parameter param;
    private boolean internetPermission = false;

    /**
     * Creates a new manager to download icons from Play Store thanks to the application's package
     * @param context Current environnement
     */
    public Manager(Context context){
        this.c = context;
        listTask = new ArrayList<>();
        listImage = new ArrayList<>();
        listPackage = new ArrayList<>();

        if(!checkInternetPermission())
            Toast.makeText(context,"Please add Internet permission",Toast.LENGTH_SHORT).show();
        else
            internetPermission = true;
    }

    /**
     * Creates a new manager to download icons from Play Store thanks to the application's package
     * @param context Current environnement
     * @param param Download parameters
     */
    public Manager(Context context, Parameter param){
        this.c = context;
        this.param = param;
        listTask = new ArrayList<>();
        listImage = new ArrayList<>();
        listPackage = new ArrayList<>();

        if(!checkInternetPermission())
            Toast.makeText(context,"Please add Internet permission",Toast.LENGTH_SHORT).show();
        else
            internetPermission = true;
    }

    /**
     * Download the icon for the given package name and set it in the imageView given;
     * @param image ImageView to fill
     * @param packageName Application's package
     */
    public void download(ImageView image, String packageName){
        LoadImage l = new LoadImage(c,this,image, new Parameter(param));
        l.execute(packageName);

        listTask.add(l);
        listImage.add(image);
        listPackage.add(packageName);
    }

    /**
     * Gives the following parameters to the Manager
     * @param param parameters
     */
    public void setParameter(Parameter param){
        this.param = param;
    }

    @Override
    protected void onBitmapLoaded(LoadImage task, Bitmap b, String packageName) {
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

    /**
     * Cancel all the icon downloads
     */
    public void cancel(){
        for (LoadImage l : listTask){
            l.cancel(true);
        }
    }

    /**
     * Delete the icon cache
     * @param context Current environment
     */
    public void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            if (dir != null && dir.isDirectory()) {
                deleteDir(dir);
            }
        } catch (Exception ignored) {}
    }

    private boolean deleteDir(File dir) {
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

    private boolean checkInternetPermission(){
        String permission = "android.permission.INTERNET";
        int res = c.checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }

}
