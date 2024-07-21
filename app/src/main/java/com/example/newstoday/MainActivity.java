package com.example.newstoday;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.kwabenaberko.newsapilib.NewsApiClient;
import com.kwabenaberko.newsapilib.models.Article;
import com.kwabenaberko.newsapilib.models.request.TopHeadlinesRequest;
import com.kwabenaberko.newsapilib.models.response.ArticleResponse;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    RecyclerView recyclerView;
    List<Article> articleList= new ArrayList<>();
    RecyclerAdapter recyclerAdapter;
    LinearProgressIndicator linearProgressIndicator;

    Button btn1,btn2,btn3,btn4,btn5,btn6,btn7;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView= findViewById(R.id.recyclerView);
        linearProgressIndicator=findViewById(R.id.progres);
        searchView=findViewById(R.id.searchview);
        btn1=findViewById(R.id.btn1);
        btn2=findViewById(R.id.btn2);
        btn3=findViewById(R.id.btn3);
        btn4=findViewById(R.id.btn4);
        btn5=findViewById(R.id.btn5);
        btn6=findViewById(R.id.btn6);
        btn7=findViewById(R.id.btn7);

        btn1.setOnClickListener(this);
         btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                getNews(null,query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerAdapter= new RecyclerAdapter(articleList);
        recyclerView.setAdapter(recyclerAdapter);
        getNews("GENERAL",null);
    }

    private void getNews(String category,String query) {
        showInProgress(true);
        NewsApiClient newsApiClient= new NewsApiClient("b3f5389a3c554227a36ce91a6aeca285");
        newsApiClient.getTopHeadlines(
                new TopHeadlinesRequest.Builder()
                        .language("en")
                        .q(query)
                        .category(category)
                        .build(),
                new NewsApiClient.ArticlesResponseCallback() {
                    @Override
                    public void onSuccess(ArticleResponse response) {
                        runOnUiThread(()->{
                            showInProgress(false);
                            articleList= response.getArticles();
                            recyclerAdapter.updateData(articleList);
                            recyclerAdapter.notifyDataSetChanged();

                        });



//                        response.getArticles().forEach((a)->{
//                            Log.i("Article",a.getTitle());
//                        });

                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        Log.i("no response",throwable.getMessage());

                    }
                }
        );

    }

    private void showInProgress(boolean show) {
        if(show){
            linearProgressIndicator.setVisibility(View.VISIBLE);
        }else{
            linearProgressIndicator.setVisibility(View.INVISIBLE);
        }
    }


    @Override
    public void onClick(View v) {
        Button button= (Button) v;
        String category=button.getText().toString();
        getNews(category,null);

    }
}