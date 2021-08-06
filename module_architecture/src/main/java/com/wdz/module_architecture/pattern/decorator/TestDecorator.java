package com.wdz.module_architecture.pattern.decorator;

public class TestDecorator {
    public static void main(String[] args) {
        Room room = new NewRoom();
        Kitchen kitchen = new Kitchen(room);
        kitchen.fitment();
    }
}
