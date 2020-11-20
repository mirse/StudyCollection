package com.wdz.module_communication.main.iot.gatt

import android.content.Context
import android.widget.TextView
import com.wdz.module_basis.widget.recyclerview.universal.base.MultiSelectTypeAdapter
import com.wdz.module_basis.widget.recyclerview.universal.base.MultiTypeAdapter
import com.wdz.module_communication.R
import com.wdz.module_communication.main.iot.gatt.bean.MyBluetoothDevice

/**

 * @Author dezhi.wang

 * @Date 2020/11/20 16:29

 */
class ScanDeviceAdapter(mContext: Context?, list: MutableList<MyBluetoothDevice>) : MultiSelectTypeAdapter<MyBluetoothDevice>(mContext, list) {
    override fun getLayoutId(): Int {
        return R.layout.recycler_item_scan_device
    }

    override fun getHeadLayoutId(): Int {
        return 0
    }

    override fun getEmptyLayoutId(): Int {
       return 0;
    }

    override fun bindData(holder: BaseViewHolder?, data: MyBluetoothDevice?, position: Int) {
        val tvDeviceInfo = holder!!.getView(R.id.tv_device_info) as TextView
        tvDeviceInfo.setText(data!!.bluetoothDevice.address)
    }
}