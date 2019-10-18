package com.wdz.studycollection.datasave.room.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = Person.class,parentColumns = "uid",childColumns = "father_id"))
public class Clothes {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String color;
    public int father_id;

    public Clothes(String color, int father_id) {
        this.color = color;
        this.father_id = father_id;
    }

    @Override
    public String toString() {
        return "Clothes{" +
                "id=" + id +
                ", color='" + color + '\'' +
                ", father_id=" + father_id +
                '}';
    }
}
