package com.wdz.module_architecture.pattern.builder;

public class Computer {
    private String cpu;
    private String ram;
    private String usb;
    private String keyboard;
    private String mouse;

    public Computer(Bulider bulider) {
        this.cpu = bulider.cpu;
        this.ram = bulider.ram;
        this.usb = bulider.usb;
        this.keyboard = bulider.keyboard;
        this.mouse = bulider.mouse;
    }

    public static class Bulider{
        private String cpu;
        private String ram;
        private String usb;
        private String keyboard;
        private String mouse;

        public Bulider(String cpu, String ram) {
            this.cpu = cpu;
            this.ram = ram;
        }

        public Bulider setUsb(String usb) {
            this.usb = usb;
            return this;
        }

        public Bulider setKeyboard(String keyboard) {
            this.keyboard = keyboard;
            return this;
        }

        public Bulider setMouse(String mouse) {
            this.mouse = mouse;
            return this;
        }
        public Computer bulid(){
            return new Computer(this);
        }
    }
}
