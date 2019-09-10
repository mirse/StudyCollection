package com.wdz.studycollection.mvp.test1.model;

/**
 * @author dezhi.wang on 2019/07/22
 */
public class DataModel {
    public static BaseModel request(String Token){
        BaseModel model = null;
        try {
            model = (BaseModel) Class.forName(Token).newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return model;
    }
}
