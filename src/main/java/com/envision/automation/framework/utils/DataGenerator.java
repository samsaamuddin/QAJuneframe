package com.envision.automation.framework.utils;

import com.github.javafaker.Faker;

public class DataGenerator {


    public static String getUsername(){
        String username= new Faker().internet().emailAddress();
        return username;

    }
    public static String getPassward(){
        String password= new Faker().internet().password(true);
        return password;

    }
    public static String getLoginName(){
        String loginname= new Faker().name().fullName();
        return loginname;
    }

}
