package com.example.newstoday;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kwabenaberko.newsapilib.models.Article;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<Article> articleList;

    public RecyclerAdapter(List<Article> articleList) {
        this.articleList = articleList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.news_card,parent,false);
        return new ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Article article= articleList.get(position);
        ((ViewHolder) holder).title.setText(article.getTitle());
        ((ViewHolder) holder).source.setText(article.getSource().getName());
        Picasso.get().load(article.getUrlToImage())
                .error(R.drawable.error)
                .placeholder(R.drawable.error)
                .into(((ViewHolder) holder).imageView);


        holder.itemView.setOnClickListener((v -> {
            Intent intent= new Intent(v.getContext(), NewsFullActivity.class);
            intent.putExtra("url",article.getUrl());
            v.getContext().startActivity(intent);


        }));


    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    void updateData(List<Article> data){
        articleList.clear();
        articleList.addAll(data);

    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView title,source;
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title= itemView.findViewById(R.id.title_text);
            source=itemView.findViewById(R.id.source);
            imageView=itemView.findViewById(R.id.news_image);
        }
    }
}
