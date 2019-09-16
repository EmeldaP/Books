package com.example.books;

import android.content.Intent;
import android.os.AsyncTask;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class BookListActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    private ProgressBar mLoadingProgress;
    private RecyclerView rvBook;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        mLoadingProgress = (ProgressBar)findViewById(R.id.progress);
        rvBook =(RecyclerView)findViewById(R.id.rvBook);
        Intent intent = getIntent();
        String query = intent.getStringExtra("Query");
        URL bookUrl;
        try{
            if (query == null || query.isEmpty()) {
                 bookUrl = ApiUtil.buildUrl("cooking");
            }else {
                bookUrl = new URL(query);
            }
                new BooksQueryTask().execute(bookUrl);
            }
            catch(Exception e){

            }

       //
        LinearLayoutManager bookLayout = new LinearLayoutManager(this,
                RecyclerView.VERTICAL,false);
        rvBook.setLayoutManager(bookLayout);

        try {
            //getJson might through exception
            URL bookUrl2 = ApiUtil.buildUrl("cooking");
            //String jsonResult = ApiUtil.getJson(bookUrl);
            new BooksQueryTask().execute(bookUrl2);
        } catch (Exception e) {
            Log.d("error", e.getMessage());
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.book_list_menu,menu);
        final MenuItem search = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        searchView.setOnQueryTextListener(this);
          //retrieve from array list search
        ArrayList<String > recentList = SharedPrefUtil.getQueryList(getApplicationContext());
         //count our seach
        int itemNumn = recentList.size();
        MenuItem recentMenu;
        for (int i=0; i < itemNumn; i++){
            recentMenu = menu.add(Menu.NONE,i,Menu.NONE,recentList.get(i));
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_search:
                Intent intent = new Intent(this,SearchView.class);
                startActivity(intent);
                return true;
                default:
                    int position = item.getItemId() + 1;
                    String preferenceName = SharedPrefUtil.QUERY +String.valueOf(position);
                    String query = SharedPrefUtil.getPreferenceString
                            (getApplicationContext(),preferenceName);
                    String[] prefParams = query.split("\\,");
                    String [] queryParams = new String[4];

                    for (int i = 0; i < prefParams.length;i++){
                        queryParams[i] = prefParams[i];

                    }
                    URL bookUrl = ApiUtil.buildUrl(
                            (queryParams[0] == null)?"": queryParams[0],
                            (queryParams[1] == null)?"": queryParams[1],
                            (queryParams[2] == null)?"": queryParams[2],
                            (queryParams[3] == null)?"": queryParams[3]
                    );
                    new BooksQueryTask().execute(bookUrl);

                    return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        try {
            URL bookUrl = ApiUtil.buildUrl(query);
            new BooksQueryTask().execute(bookUrl);
        }
        catch (Exception e){
            //Log.d("error",e.getMessage());
        }
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public class BooksQueryTask extends AsyncTask<URL, Void, String> {
        @Override
        protected String doInBackground(URL... urls) {
            URL searchUrl = urls[0];
            String result = null;
            try {
                result = ApiUtil.getJson(searchUrl);
            } catch (IOException e) {
                Log.d("Error", e.getMessage());
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {

            TextView error = (TextView)findViewById(R.id.error);
            mLoadingProgress.setVisibility(View.INVISIBLE);
            if (result == null){

                error.setVisibility(View.VISIBLE);
            }
            else {
              rvBook.setVisibility(View.VISIBLE);
                error.setVisibility(View.INVISIBLE);
                //if no internet connect  send message to user
                ArrayList<Book> books = ApiUtil.getBooksFromJson(result);
                String resultString = "";

                BooksAdapter adapter = new BooksAdapter(books);
                rvBook.setAdapter(adapter);
            }

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingProgress.setVisibility(View.VISIBLE);
        }
    }


}
