package com.wdz.studycollection.datasave.room.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;
import static androidx.room.ForeignKey.RESTRICT;

@Entity(tableName = "clothes",
        primaryKeys = { "father_id", "fathers_id" },
        foreignKeys = {
                @ForeignKey(entity = Person.class,
                        parentColumns = "uid",
                        childColumns = "father_id",
                        onUpdate = RESTRICT
                ),
                @ForeignKey(entity = Market.class,
                        parentColumns = "marketId",
                        childColumns = "fathers_id",
                        onUpdate = RESTRICT
                )
        },
        indices = {@Index(value = {"fathers_id"})}
        )
public class Clothes {
    public int id;
    public String color;
    public int father_id;
    public int fathers_id;



//    public Clothes(String color, int father_id) {
//        this.color = color;
//        this.father_id = father_id;
//    }
    public Clothes(String color, int father_id,int father_id1) {
        this.color = color;
        this.father_id = father_id;
        this.fathers_id = father_id1;
    }
//    public Clothes(String color,int fathers1_id) {
//        this.color = color;
//        this.fathers_id = fathers1_id;
//    }

    @Override
    public String toString() {
        return "Clothes{" +
                "id=" + id +
                ", color='" + color + '\'' +
                ", father_id=" + father_id +
                '}';
    }
}
