package com.company.im.chat.data.model;

public class Friend {

    private String userName;
    /*
    好友备注
     */
    private String remark;
    /*
    好友签名
     */
    private String signature;

    private int age;

    private String sex;

    public Friend( String userName, String remark, String signature, int age, String sex) {
        this.userName = userName;
        this.remark = remark;
        this.signature = signature;
        this.age = age;
        this.sex = sex;
    }

    public Friend(){

    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return ",userName:"+userName+
                ",remark:"+remark+
                ",signature:"+signature+
                ",sex:"+sex+
                ",age:"+age;
    }
}
