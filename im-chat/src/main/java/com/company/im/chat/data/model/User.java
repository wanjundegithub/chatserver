package com.company.im.chat.data.model;

public class User {
    private String userName;

    private String password;

    private String sex;

    private int age;

    private String signature;

    public User(String userName, String password, String sex, int age, String signature) {
        this.userName = userName;
        this.password = password;
        this.sex = sex;
        this.age = age;
        this.signature = signature;
    }

    public User(){

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    @Override
    public String toString() {
        return "userName:"+userName+
                ",password:"+password+
                ",sex:"+sex+
                ",age:"+age+
                ",signature:"+signature;
    }
}
