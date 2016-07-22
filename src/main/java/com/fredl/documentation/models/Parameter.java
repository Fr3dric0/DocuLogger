package com.fredl.documentation.models;

/**
 *  @author: Fredrik F. Lindhagen <fred.lindh96@gmail.com>
 *  @created: 22.07.2016
 *  @desc:
 */
public class Parameter {
    private String name;
    private String type;
    private String details;

    public Parameter(String name, String type, String details){
        this.name = name;
        this.type = type;
        this.details = details;
    }

    // GETTERS
    public String getName() {
        return name;
    }
    public String getType() {
        return type;
    }
    public String getDetails() {
        return details;
    }

    // toString
    @Override
    public String toString(){
        return String.format("(%s)\t%s\t%s", this.type, this.name, this.details);
    }
}
