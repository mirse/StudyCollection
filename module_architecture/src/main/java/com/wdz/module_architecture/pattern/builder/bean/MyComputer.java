package com.wdz.module_architecture.pattern.builder.bean;

import com.wdz.module_architecture.pattern.builder.Computer;

public class MyComputer {
    private String cpu;
    private String ram;
    private String usb;
    private String keyboard;
    private String mouse;



    public static class MyComputerBuilder implements IBulider{

        private MyComputer myComputer;

        public MyComputerBuilder() {
            myComputer = new MyComputer();
        }

        @Override
        public IBulider setCpu(String cpu) {
            myComputer.cpu = cpu;
            return this;
        }

        @Override
        public IBulider setRam(String ram) {
            myComputer.ram = ram;
            return this;
        }

        @Override
        public IBulider setUsb(String usb) {
            return null;
        }

        @Override
        public IBulider setKeyBoard(String keyboard) {
            return null;
        }

        @Override
        public IBulider setMouse(String mouse) {
            return null;
        }

        @Override
        public MyComputer bulid() {
            return null;
        }
    }
}
