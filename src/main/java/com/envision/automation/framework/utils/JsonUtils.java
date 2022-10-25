package com.envision.automation.framework.utils;



import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.json.JsonOutput;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class JsonUtils {
    public JSONObject mainJsoOBj;

    public  JSONObject getMainObject(){
        return mainJsoOBj;
    }

    public void loadTestDataFile(String fileName) throws IOException, ParseException {
        File jsonFile = new File(System.getProperty("user.dir")+"\\src\\test\\resources\\testData\\"+fileName);
        JSONParser parser = new JSONParser();
        Object jsonFileObject = parser.parse(new FileReader(jsonFile));
        this.mainJsoOBj=(JSONObject) jsonFileObject;
    }

    public  JSONObject getJsonObject(JSONObject jsonObject,String jsonObjectName){
        return (JSONObject) jsonObject.get(jsonObjectName);

    }

    public  String getJsonObjectvalue(JSONObject jsonObject, String key){
        return jsonObject.get(key).toString();
    }

}

