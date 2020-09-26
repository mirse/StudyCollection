package com.wdz.module_architecture.rxjava.bean;

public class Translation {
    private static final String TAG = "Translation";
    private int status;
    public Content content;
    public static class Content{
        private String from;
        public String to;
        private String vendor;
        public String out;
        private String cibaUse;
        private String cibaOut;
        private int errNo;

        @Override
        public String toString() {
            return "Content{" +
                    "from='" + from + '\'' +
                    ", to='" + to + '\'' +
                    ", vendor='" + vendor + '\'' +
                    ", out='" + out + '\'' +
                    ", cibaUse='" + cibaUse + '\'' +
                    ", cibaOut='" + cibaOut + '\'' +
                    ", errNo=" + errNo +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "Translation{" +
                "status=" + status +
                ", content=" + content +
                '}';
    }

    public String show(){
        return content.cibaOut;
    }
}
