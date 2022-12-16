package com.example.contacts_server;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    Context context;
    ArrayList<News> arr;
    public CustomAdapter(Context context, ArrayList<News> arr){
        this.context=context;
        this.arr=arr;
    }
    @NonNull
    @Override
    public CustomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.row_layout,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.ViewHolder holder, int position) {
        News news=arr.get(position);
        holder.title.setText(news.title);
        holder.desc.setText(news.desc);
        try {
            URL url=new URL(news.img_url);
            Bitmap bmp= BitmapFactory.decodeStream(url.openConnection().getInputStream());
            holder.img.setImageBitmap(bmp);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    public void update(ArrayList<News> a){
        arr=a;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return arr.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView title,desc;
        ImageView img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title=itemView.findViewById(R.id.title);
            desc=itemView.findViewById(R.id.description);
            img=itemView.findViewById(R.id.image);
        }
    }
}
