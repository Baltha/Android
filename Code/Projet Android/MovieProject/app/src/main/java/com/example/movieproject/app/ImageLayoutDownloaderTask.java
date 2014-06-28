package com.example.movieproject.app;

import android.graphics.Bitmap;
import android.widget.LinearLayout;
import com.example.movieproject.app.Downloader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
//Classe pour chargé de façon asyncrhone et dans le cache du téléphone les images qui seront dans le détail de chaque vidéo avec une résolution plus élevé.
class ImageLayoutDownloaderTask extends Downloader<LinearLayout>{

    public ImageLayoutDownloaderTask(LinearLayout view) {
        super(view, 720, 1280, view.getContext().getCacheDir());
    }

    @Override
    protected void onDownloaded(Bitmap image){
        LinearLayout layout = (LinearLayout)(viewRef.get());
        if(layout != null && image != null)
            layout.setBackground(new BitmapDrawable(layout.getResources(), image));
    }

}
