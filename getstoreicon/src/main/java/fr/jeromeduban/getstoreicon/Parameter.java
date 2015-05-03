package fr.jeromeduban.getstoreicon;

import android.graphics.drawable.Drawable;

/**
 * Created by jduban on 02/05/15.
 */
public class Parameter {

    private boolean cache = false;
    private int size = 300;
    private Drawable defaultIcon;

    /**
     * Creates new download parameters
     * Defaults: cache = false, size = 300
     */
    public Parameter(){

    }

    protected Parameter(Parameter param) {
        this.size = param.getSize();
        this.cache = param.getCache();
        this.defaultIcon = param.getDefaultIcon();
    }

    /**
     * Set the size in px of the downloaded icon
     * @param size Size in px
     * @return parameters with the given size
     */
    public Parameter setSize(int size){
        this.size = size;
        return this;
    }

    /**
     * Enable / Disable the cache
     * @param cache true = enable, false = disable
     * @return parameters with the cache enable or not
     */
    public Parameter setCache(boolean cache){
        this.cache = cache;
        return this;
    }

    /**
     * Get the value of the cache
     * true = enabled, false = disabled
     * @return cache
     */
    public boolean getCache() {
        return cache;
    }

    /**
     * Get the icon size selected
     * @return size in px
     */
    public int getSize() {
        return size;
    }

    /**
     * Get the default icon if an error occurs
     * @return Drawable icon
     */
    public Drawable getDefaultIcon() {
        return defaultIcon;
    }

    /**
     * Set the default icon if an error occurs
     * @param defaultIcon Drawable icon (Drawable or Mipmap)
     * @return default icon
     */
    public Parameter setDefaultIcon(Drawable defaultIcon) {
        this.defaultIcon = defaultIcon;
        return this;
    }




}
