package com.wdz.module_communication.main.iot.gatt.utils.scan;

/**
 * 扫描策略
 * @Author dezhi.wang
 * @Date 2021/3/3 9:22
 */
public class ScanStrategy {
    private AbstractScanner scanner;
    ScanStrategy(AbstractScanner scanner) {
        this.scanner = scanner;
    }

    public void scan(int scanMode,String scanFilter) {
        scanner.scan(scanMode,scanFilter);
    }
}
