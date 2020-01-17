package com.wdz.studycollection.datasave.room.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;
import static androidx.room.ForeignKey.RESTRICT;

@Entity(tableName = "clothes",
        primaryKeys = { "persionId","marketId"},
        foreignKeys = {
                @ForeignKey(entity = Person.class,
                        parentColumns = "uid",
                        childColumns = "persionId",
                        onDelete = CASCADE
                ),
                @ForeignKey(entity = Market.class,
                        parentColumns = "uidu",
                        childColumns = "marketId",
                        onDelete = CASCADE
                )
        }
        )
public class Clothes {
    public String color;
    public int persionId;
    public int marketId;


    public Clothes(String color, int persionId, int marketId) {
        this.color = color;
        this.persionId = persionId;
        this.marketId = marketId;
    }

    @Override
    public String toString() {
        return "Clothes{" +
                "color='" + color + '\'' +
                ", persionId=" + persionId +
                ", marketId=" + marketId +
                '}';
    }
}
