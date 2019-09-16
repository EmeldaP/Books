package com.example.books;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

public class Book  implements Parcelable {
    public String title;
    public String subTitle;
    public String authors;
    public String publisher;
    public String  publishedDate;
    public String id;
    public String description;
    public String thumbnail;

    public Book(String title, String subTitle, String[] authors, String publisher,
                String publishedDate, String id,String description,String thumbnail) {
        this.title = title;
        this.subTitle = subTitle;
        this.authors = TextUtils.join(",", authors);
        this.publisher = publisher;
        this.publishedDate = publishedDate;
        this.id = id;
        this.description = description;
        this.thumbnail = thumbnail;
    }

    public Book(String string, String string1, String s, String[] authors, String string2, String string3) {
    }

    protected Book(Parcel in) {
        title = in.readString();
        subTitle = in.readString();
        authors = in.readString();
        publisher = in.readString();
        publishedDate = in.readString();
        id = in.readString();
        description=in.readString();
        thumbnail = in.readString();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    public Book(String string, String string1, String s, String[] authors, String publishedDate, String id, String description, String thumbnail) {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(subTitle);
        parcel.writeString(authors);
        parcel.writeString(publisher);
        parcel.writeString(publishedDate);
        parcel.writeString(id);
        parcel.writeString(description);
        parcel.writeString(thumbnail);
    }

    @BindingAdapter({"android:imageURL"})
    public static void loadImage(ImageView view,String imageUrl){
        //if books doesnt have cover
        //should use book
        if (!imageUrl.isEmpty()) {
          //  Picasso.with(view.getContext())
           //         .load(imageUrl)
             //       .placeholder(R.drawable.book_open)
               //     .into(view);
        }else
        { view.setBackgroundResource(R.drawable.book_open);
        }

 }
}
