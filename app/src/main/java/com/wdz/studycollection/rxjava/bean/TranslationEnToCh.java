package com.wdz.studycollection.rxjava.bean;

import java.util.List;

public class TranslationEnToCh {

    private String ph_en;
    private String ph_am;
    private String ph_en_mp3;
    private String ph_am_mp3;
    private String ph_tts_mp3;
    private List<String> word_mean;

    private int error_Code;
    private String message;
    private String out;

    public String getPh_en() {
        return ph_en;
    }

    public void setPh_en(String ph_en) {
        this.ph_en = ph_en;
    }

    public String getPh_am() {
        return ph_am;
    }

    public void setPh_am(String ph_am) {
        this.ph_am = ph_am;
    }

    public String getPh_en_mp3() {
        return ph_en_mp3;
    }

    public void setPh_en_mp3(String ph_en_mp3) {
        this.ph_en_mp3 = ph_en_mp3;
    }

    public String getPh_am_mp3() {
        return ph_am_mp3;
    }

    public void setPh_am_mp3(String ph_am_mp3) {
        this.ph_am_mp3 = ph_am_mp3;
    }

    public String getPh_tts_mp3() {
        return ph_tts_mp3;
    }

    public void setPh_tts_mp3(String ph_tts_mp3) {
        this.ph_tts_mp3 = ph_tts_mp3;
    }

    public List<String> getWord_mean() {
        return word_mean;
    }

    public void setWord_mean(List<String> word_mean) {
        this.word_mean = word_mean;
    }

    public int getError_Code() {
        return error_Code;
    }

    public void setError_Code(int error_Code) {
        this.error_Code = error_Code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getOut() {
        return out;
    }

    public void setOut(String out) {
        this.out = out;
    }

    @Override
    public String toString() {
        return "TranslationEnToCh{" +
                "ph_en='" + ph_en + '\'' +
                ", ph_am='" + ph_am + '\'' +
                ", ph_en_mp3='" + ph_en_mp3 + '\'' +
                ", ph_am_mp3='" + ph_am_mp3 + '\'' +
                ", ph_tts_mp3='" + ph_tts_mp3 + '\'' +
                ", word_mean=" + word_mean +
                ", error_Code=" + error_Code +
                ", message='" + message + '\'' +
                ", out='" + out + '\'' +
                '}';
    }
}
