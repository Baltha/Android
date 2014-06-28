package com.example.movieproject.app;

import android.graphics.Bitmap;
import android.widget.ImageView;

import java.io.File;

public class ImageDownloaderTask extends Downloader<ImageView> {

    //Classe pour chargé de façon asyncrhone et dans le cache du téléphone les images qui seront dans les ImageViews de la liste principale.
    public ImageDownloaderTask(ImageView view) {
        super(view, 150, 200, view.getContext().getCacheDir());
    }

    @Override
    protected void onDownloaded(Bitmap image) {
        ImageView imageView = (ImageView)(this.viewRef.get());

        if(imageView == null)
            return;

        if(image != null)
            imageView.setImageBitmap(image);
        else{
            imageView.setImageDrawable(imageView.getContext().getResources()
                    .getDrawable(R.drawable.list_placeholder));
        }

    }
}
