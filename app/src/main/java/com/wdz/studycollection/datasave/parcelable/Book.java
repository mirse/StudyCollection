package com.wdz.studycollection.datasave.parcelable;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Book implements Parcelable {

    public String bookName;
    public int price;
    //初始化
    public List<String> years=new ArrayList<>();



    protected Book(Parcel in) {
        bookName = in.readString();
        price = in.readInt();
       // years = in.createStringArrayList();
        in.readStringList(years);
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

    public Book(String bookName, int price) {
        this.bookName = bookName;
        this.price = price;
    }





    @Override
    public String toString() {
        return "Book{" +
                "bookName='" + bookName + '\'' +
                ", price=" + price +
                ", years=" + years +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(bookName);
        dest.writeInt(price);
        dest.writeStringList(years);
    }
}
