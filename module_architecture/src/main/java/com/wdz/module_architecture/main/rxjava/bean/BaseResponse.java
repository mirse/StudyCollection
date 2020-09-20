package com.wdz.module_architecture.main.rxjava.bean;

public class BaseResponse<T>{
    private int status;
    private T content;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "status=" + status +
                ", content=" + content +
                '}';
    }
}
