package com.example.books;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.net.URL;

public class SearchAcivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_acivity);

        final EditText eTitle= (EditText) findViewById(R.id.eTitle);
        final EditText eAuthor= (EditText) findViewById(R.id.eAuthor);
        final EditText ePublisher= (EditText) findViewById(R.id.ePublisheer);
        final EditText Isbn= (EditText) findViewById(R.id.eISBN);
        final Button button= (Button) findViewById(R.id.search);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = eTitle.getText().toString().trim();
                String author = eAuthor.getText().toString().trim();
                String publish = ePublisher.getText().toString().trim();
                String isbn = Isbn.getText().toString().trim();
                //when user doesnt write anything on ediText
                if (title.isEmpty() && author.isEmpty() & publish.isEmpty() & isbn.isEmpty()){
                    //retriving from string value
                    String message =getString(R.string.no_search_data);
                    Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
                }
                else {
                    URL queryURL = ApiUtil.buildUrl(title,author,publish,isbn);
                    //shardPreference saving according to search
                    Context context = getApplicationContext();
                    int position = SharedPrefUtil.getPreferenceInt(context,SharedPrefUtil.POSITION);
                    if (position == 0 ||position==5){
                        position = 1;

                    }
                    else {
                        position++;
                    }
                    String key = SharedPrefUtil.QUERY+ String.valueOf(position);
                    String value = title+ "," + author + ","+ publish + "," + isbn ;
                    //for string
                    SharedPrefUtil.setPreferenceString(context,key,value);
                    //for int
                    SharedPrefUtil.setPreferenceInt(context,SharedPrefUtil.POSITION,position);

                        Intent intent =new Intent(getApplicationContext(),BookListActivity.class);
                        intent.putExtra("Query", queryURL);
                        startActivity(intent);
                    }
                }

        });

    }
}
