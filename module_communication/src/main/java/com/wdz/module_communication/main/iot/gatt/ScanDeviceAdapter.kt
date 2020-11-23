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
    lateinit var onClickListener:OnClickListener;
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
        val connect = holder.getView(R.id.bt_connect) as TextView
        val disconnect = holder.getView(R.id.bt_disconnect) as TextView
        if (data!!.bluetoothDevice.address.startsWith("9F:EB")){
            tvDeviceInfo.setTextColor(context.resources.getColor(R.color.gray_pure))
        }
        else{
            tvDeviceInfo.setTextColor(context.resources.getColor(R.color.black_deep))
        }
        tvDeviceInfo.setText(data!!.bluetoothDevice.address)
        tvDeviceInfo.setText(data!!.bluetoothDevice.address)
        connect.setOnClickListener({
            if (onClickListener!=null){
                onClickListener.onClickConnect(data)
            }
        })
        disconnect.setOnClickListener({
            if (onClickListener!=null){
                onClickListener.onClickDisConnect(data)
            }
        })

    }

    interface OnClickListener{
        fun onClickConnect(myBluetoothDevice: MyBluetoothDevice)
        fun onClickDisConnect(myBluetoothDevice: MyBluetoothDevice)
    }
    fun setOnGattClickListener(onClickListener:OnClickListener){
        this.onClickListener = onClickListener;
    }
}