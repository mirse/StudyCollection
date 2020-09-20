package com.wdz.module_communication.main.datasave.room.entity.embedded;

import androidx.room.Embedded;
import androidx.room.Relation;


import com.wdz.module_communication.main.datasave.room.entity.Market;
import com.wdz.module_communication.main.datasave.room.entity.Person;
import com.wdz.module_communication.main.datasave.room.entity.Vendor;

import java.util.List;

public class PersonInfo {
    @Embedded
    public Person person;

    @Relation(parentColumn = "uid",entityColumn = "uidu")
    public List<Market> markets;

    @Relation(parentColumn = "uid",entityColumn = "vendorId")
    public List<Vendor> vendors;

    @Override
    public String toString() {
        return "PersonInfo{" +
                "person=" + person +
                ", markets=" + markets +
                ", vendors=" + vendors +
                '}';
    }
}
