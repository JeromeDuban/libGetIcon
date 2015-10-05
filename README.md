#libGetIcon
This library enables to download apps icons from the playstore thanks to its package name. 

# How to use it ?
The jar file of the library can be found in the libs folder. Add it to your project as a library. Add the internet permission to your app. If you don't, a message will ask you do to so

    <uses-permission android:name="android.permission.INTERNET" />

Define the default icon you want. This icon will be displayed while the icon is grabbed from the playstore or if an issue occurs (wrong package name, connection issue,..)

    Drawable defaultIcon = this.getResources().getDrawable(R.drawable.ic_launcher);
    // OR
    Drawable defaultIcon = this.getResources().getDrawable(R.mipmap.ic_launcher);

Then create the download parameters:

* Cache: If true, the icons will be saved in the cache folder. If the icon has to be loaded several times, the icon will be loaded from the cache instead of being downloaded again. Default value = false.
* Size: You can choose the size in px of the icon you want to download. Default value = 300
* Default icon.

Here is an example of the parameters.

    Parameter param = new Parameter()
                        .setCache(true)
                        .setSize(100)
                        .setDefaultIcon(defaultIcon);

Initialize the manager with a context and the parameters:

    Manager m = new Manager(this, param);

Then select download an icon  in an imageView

    m.download(image1, "com.facebo  &=1ok.%20");    // Nothing will be downloaded
    m.download(image2,"com.netmarble.mherosg");	// False package name
    m.download(image3,"com.netmarble.mherosgb");
    m.download(image4,"com.facebook.katana");
    m.download(image5,"com.ustwo.monumentvalley");
    m.download(image6,"com.facebook.katana");

The result of the following code is this one:

<img src="/screenshots/screen.png" width="200px">

You can also empty the cach with the following code

    m.deleteCache(this);

# How to compile the jar ?
On Android Studio, open the terminal and run the following command in the root folder of the project:

    gradlew makeJar

The created jar file will be saved in the libs folder