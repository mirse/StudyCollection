package com.wdz.module_architecture.pattern.decorator;

public abstract class RoomDecorator extends Room{
    private Room room;//持有被装饰者的引用
    public RoomDecorator(Room room) {
        this.room = room;
    }

    @Override
    public void fitment() {
        room.fitment();
    }
}


class BedRoom extends RoomDecorator{

    public BedRoom(Room room) {
        super(room);
    }

    @Override
    public void fitment() {
        super.fitment();
        System.out.println("BedRoom 装修");
    }
}

class Kitchen extends RoomDecorator{

    public Kitchen(Room room) {
        super(room);
    }

    @Override
    public void fitment() {
        super.fitment();
        System.out.println("Kitchen 装修");
    }
}
