package com.example.movieproject.app;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.LinearLayout;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by AdrienPC on 02/06/2014.
 * Ici une classe maison, les images risquant d'être parfois, trop lourde, gros problèmes de gestion mémoire sous android;
 * Cette classe a donc été implémenté pour rezize l'image au dimention fournit en paramètre, création d'un fichier cache qui correspond à l'image,
 * et bien sur, l'utilisateur fournit une URL pour l'image, par conséquent il a fallut aller la chercher et l'interpréter, tout cela de façon Asynchrone.
 */
public abstract class Downloader<T> extends AsyncTask<String, Void, Bitmap> {

    public class Dimensions {
        public int width;
        public int height;

        public Dimensions(int width, int height){
            this.width = width;
            this.height = height;
        }
    }

    protected final WeakReference viewRef;
    protected Dimensions d;
    private File tmp;
    //EN dehors de la hauteur et de la largeur de l'image, il y a aussi l'indicatif du fichier temporaire, ce dernier est génére de façon aléatoire via un md5.
    public Downloader(T view, int width, int height, File tmp) {
        d = new Dimensions(width, height);
        this.tmp = tmp;
        viewRef = new WeakReference(view);
    }

    @Override
    // Actual download method, run in the task thread
    protected Bitmap doInBackground(String... params) {
        return downloadBitmap(params[0], d, tmp);
    }

    protected abstract void onDownloaded(Bitmap image);

    @Override
    // Once the image is downloaded, associates it to the imageView
    protected void onPostExecute(Bitmap image) {
        if (isCancelled()) {
            image = null;
        }

        onDownloaded(image);

    }
    /*
        Ici, le fichier temp est crée, on lui insére l'image qui vient d'être traité.
     */
    static Bitmap downloadBitmap(String url, Downloader.Dimensions dim, File tmp) {
        String hash = Downloader.md5(url);
        Log.w("FH", "File hash =>" + url + " // " + hash);
        final File f = new File(tmp, hash + ".tmp");
        if(f.exists())
            return Downloader.getImageFromFile(f, dim);

        final AndroidHttpClient client = AndroidHttpClient.newInstance("Android");
        final HttpGet getRequest = new HttpGet(url);
        try {
            HttpResponse response = client.execute(getRequest);
            final int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                Log.w("ImageDownloader", "Error " + statusCode
                        + " while retrieving bitmap from " + url);
                return null;
            }

            final HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream inputStream = null;
                try {
                    inputStream = entity.getContent();

                    f.deleteOnExit();

                    //sauv le file
                    final FileOutputStream out = (new FileOutputStream(f));
                    try {

                        byte[] buff = new byte[1024];
                        int sz = 0;
                        int offset = 0;
                        while ((sz = inputStream.read(buff)) != -1) {
                            out.write(buff, 0, sz);
                            offset += sz;
                            buff = new byte[1024];
                        }
                        out.flush();
                        out.close();
                    } catch (Exception e) {

                        out.close();
                    } finally {
                        out.close();
                    }


                    inputStream.close();
                    entity.consumeContent();

                    return Downloader.getImageFromFile(f, dim);
                }
                catch(Exception e){
                    Log.v("erreur", e.getMessage().toString() + "// " +e.getCause());

                }
                finally {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    entity.consumeContent();
                }
            }
        } catch (Exception e) {
            getRequest.abort();
            Log.w("ImageDownloader", "Error " + e.getMessage() + " while retrieving bitmap from " + url);
        } finally {
            if (client != null) {
                client.close();
            }
        }
        return null;
    }
    //On récupère l'image du fichier cache.
    public static Bitmap getImageFromFile(File f, Downloader.Dimensions dim){
        try {
            BufferedInputStream fi = new BufferedInputStream(new FileInputStream(f));

            final BitmapFactory.Options pt = new BitmapFactory.Options();
            pt.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(fi, null, pt);
            pt.inSampleSize = Downloader.calculateInSampleSize(pt, dim.width, dim.height);
            pt.inJustDecodeBounds = false;

            fi.close();
            fi = new BufferedInputStream(new FileInputStream(f));

            return BitmapFactory.decodeStream(fi, null, pt);
        }
        catch(FileNotFoundException e){
            return null;
        } catch (IOException e) {
            return null;
        }
    }
    //Fonction de rezize de façon a gagné de la place tout en préservant les proportions.
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
    //Afin de toujours avoir un nom de fichier temp différent
    public static String md5(String s)
    {
        MessageDigest digest;
        try
        {
            digest = MessageDigest.getInstance("MD5");
            digest.update(s.getBytes(),0,s.length());
            String hash = new BigInteger(1, digest.digest()).toString(16);
            return hash;
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return "";
    }
}
