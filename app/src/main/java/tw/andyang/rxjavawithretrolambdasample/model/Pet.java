package tw.andyang.rxjavawithretrolambdasample.model;

import com.google.gson.annotations.SerializedName;

public class Pet {

    @SerializedName("Name")
    private String name;
    @SerializedName("Type")
    private String type;

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }
}
