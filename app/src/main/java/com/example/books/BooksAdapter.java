package com.example.books;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.BookViewHolder> implements View.OnClickListener {

    ArrayList<Book> books;
    public BooksAdapter(ArrayList<Book> books) {
        this.books = books;
    }

    @NonNull
    @Override
    //called for new hold viewer
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View itemV = LayoutInflater.from(context).
                inflate(R.layout.book_list_item,parent,false);

        return new BookViewHolder(itemV) ;
    }

    @Override
    //to display data
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
               Book book = books.get(position);
               //bind it to holder
               holder.bind(book);
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    @Override
    public void onClick(View view) {
        int position = getAdapterPosition();
        Book selectedBook = books.get(position);
        Intent intent = new Intent(view.getContext(),BookDetail.class);
        intent.putExtra("Books",selectedBook);
        view.getContext().startActivity(intent);

    }

    private int getAdapterPosition() {
        return 0;
    }

    public class BookViewHolder extends RecyclerView.ViewHolder{
    TextView tvTitle;
    TextView authorss;
    TextView date;
    TextView publisher;

    public BookViewHolder(@NonNull View itemView) {
        super(itemView);
        tvTitle = (TextView)itemView.findViewById(R.id.tvtitle);
        authorss = (TextView)itemView.findViewById(R.id.author);
        date = (TextView)itemView.findViewById(R.id.published);
        publisher=(TextView)itemView.findViewById(R.id.publisher);
      //  itemView.setOnClickListener(this);
    }
    //binding book to textViews
    public void bind(Book book){
        tvTitle.setText(book.title);
       // String authors = "";
        //        for (String author: book.authors){
//            authors+=author;
//            i++;
//            if (i<book.authors.length){
//                authors+=",";
//            }
     //   }
        authorss.setText(book.authors);
        date.setText(book.publishedDate);
        publisher.setText(book.publisher);
        }



    }
}

