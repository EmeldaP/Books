package com.example.books;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.example.books.databinding.ActivityBookDetailBinding;

public class BookDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        Book book = getIntent().getParcelableExtra("Books");
        //activating data binding &
        // using layout file to update to use data bind
      ActivityBookDetailBinding binding = DataBindingUtil.setContentView(this,R.layout
              .activity_book_detail);
        binding.setBook(book);
    }
}
