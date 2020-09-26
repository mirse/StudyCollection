package com.wdz.module_communication.main.network.okhttp;

public abstract class DownLoadListener {


    /**
     * 通知当前的下载进度
     * @param progress
     */
    abstract void onProgress(int progress);

    /**
     * 通知下载成功
     */
    abstract void onSuccess();

    /**
     * 通知下载失败
     */
    abstract void onFailed();

    /**
     * 通知下载暂停
     */
    abstract void onPaused();

    /**
     * 通知下载取消事件
     */
    abstract void onCanceled();

}
