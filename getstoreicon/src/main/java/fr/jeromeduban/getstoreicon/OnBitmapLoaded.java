package fr.jeromeduban.getstoreicon;

import android.graphics.Bitmap;

/**
 * Created by jduban on 03/05/15.
 */
public interface OnBitmapLoaded {

    void onBitmapLoaded(LoadImage task, Bitmap b, String packageName);
}
