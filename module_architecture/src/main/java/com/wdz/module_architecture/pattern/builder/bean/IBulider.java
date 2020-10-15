package com.wdz.module_architecture.pattern.builder.bean;

import com.wdz.module_architecture.pattern.builder.Computer;

public interface IBulider {
    IBulider setCpu(String cpu);
    IBulider setRam(String ram);
    IBulider setUsb(String usb);
    IBulider setKeyBoard(String keyboard);
    IBulider setMouse(String mouse);
    MyComputer bulid();

}
