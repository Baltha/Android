package com.example.movieproject.app;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Debug;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;
import java.util.SortedMap;
//Classe qui permet de set les valeurs des différents champs de la liste de vidéos.
public class CustomListAdapter extends BaseAdapter {

    static class ViewHolder {
        TextView titleView;
        TextView realisateurView;
        TextView dateView;
        ImageView imageView;
        Button visibleView;
        Button dispoView;
    }
    private ArrayList listData;

    private LayoutInflater layoutInflater;
    private Hashtable<Integer, View> cache;
    private Hashtable<Integer, Integer> cacheRelat;

    public CustomListAdapter(Context context, ArrayList listData) {
        this.listData = listData;
        layoutInflater = LayoutInflater.from(context);
        this.cache = new Hashtable<Integer, View>();
        this.cacheRelat = new Hashtable<Integer, Integer>();
    }

    public Hashtable<Integer, View> getCache(){
        return this.cache;
    }

    public void setCache(Hashtable<Integer, View> cache){
        this.cache = cache;
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View cView, ViewGroup parent) {
        if (!this.cacheRelat.containsKey(position)/*cView == null*/) {

            Video video = (Video) listData.get(position);
            if(this.cache.containsKey(video.getId())) {
                this.cacheRelat.put(position, video.getId());
                return this.cache.get(video.getId());
            }

            ViewHolder holder;
            View convertView = layoutInflater.inflate(R.layout.list_row_layout, null);
            holder = new ViewHolder();
            holder.titleView = (TextView) convertView.findViewById(R.id.title);
            holder.realisateurView = (TextView) convertView.findViewById(R.id.realisateur);
            holder.dateView = (TextView) convertView.findViewById(R.id.date);
            holder.imageView = (ImageView) convertView.findViewById(R.id.thumbImage);
            holder.visibleView=(Button) convertView.findViewById(R.id.visible);
            holder.dispoView=(Button) convertView.findViewById(R.id.dispo);



            holder.titleView.setText(video.getNom());
            holder.realisateurView.setText(video.getRealisateur());
            holder.dateView.setText("("+video.getDateSortie()+")");

            if(video.getVisible()==1)
            {

                holder.visibleView.setBackground(convertView.getResources().getDrawable(R.drawable.icon_vu_on));

            }
            else
            {
                holder.visibleView.setBackground(convertView.getResources().getDrawable(R.drawable.icon_vu_off));
            }
            if(video.getDispo()==1)
            {
                holder.dispoView.setBackground(convertView.getResources().getDrawable(R.drawable.icon_pret_on));
            }
            else
            {
                holder.dispoView.setBackground(convertView.getResources().getDrawable(R.drawable.icon_pret_off));
            }

            if (holder.imageView != null) {
                new ImageDownloaderTask(holder.imageView).execute(video.getimageC());
            }

           // this.cache.put(position, holder);
            //convertView.setTag(holder);
            this.cache.put(video.getId(), convertView);
            this.cacheRelat.put(position, video.getId());
            return convertView;

        } else {
            //holder = (ViewHolder) convertView.getTag();
            return this.cache.get(this.cacheRelat.get(position));
           // return new View;
        }




        //holder.dispoView.setText(video.getDispo());

        //return cView;
// return convertView;
    }


}