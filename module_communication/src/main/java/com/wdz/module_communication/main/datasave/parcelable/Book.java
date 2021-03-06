package com.wdz.module_communication.main.datasave.parcelable;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Book implements Parcelable {

    public String bookName;
    public int price;
    //初始化
    public List<String> years=new ArrayList<>();


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.bookName);
        dest.writeInt(this.price);
        dest.writeStringList(this.years);

    }

    @Override
    public String toString() {
        return "Book{" +
                "bookName='" + bookName + '\'' +
                ", price=" + price +
                ", years=" + years +
                '}';
    }

    public Book(String bookName, int price) {
        this.bookName = bookName;
        this.price = price;
    }

    protected Book(Parcel in) {
        this.bookName = in.readString();
        this.price = in.readInt();
        this.years = in.createStringArrayList();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel source) {
            return new Book(source);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };
}
