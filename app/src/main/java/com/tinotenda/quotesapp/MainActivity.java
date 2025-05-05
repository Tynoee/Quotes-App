package com.tinotenda.quotesapp;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private QuoteAdapter adapter;
    private List<Quote> quotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.quotesRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        quotes = loadQuotesFromFile();
        adapter = new QuoteAdapter(quotes);
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));

    }

    private List<Quote> loadQuotesFromFile() {
        List<Quote> quotes = new ArrayList<>();
        try (InputStream stream = getAssets().open("quotations.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String quoteText = line;
                String author = reader.readLine(); // The next line is the author
                if (author != null) {
                    quotes.add(new Quote(quoteText, author));
                    Log.d("QuotesLoader", "Loaded quote: " + quoteText + " - " + author);
                }
            }
        } catch (IOException e) {
            Log.e("QuotesLoader", "Error reading quotes: " + e.toString());
        }
        return quotes;
    }
}
