package com.fredl.documentation.models;

/**
 *  @author: Fredrik F. Lindhagen <fred.lindh96@gmail.com>
 *  @created: 23.07.2016
 *  @desc:
 */
public class Returns {
    private String type;
    private String details;

    public Returns(String type, String details){
        this.type = type;
        this.details = details;
    }




    // GETTERS
    public String getDetails(){
        return this.details;
    }
    public String getType(){
        return this.type;
    }

    // toString
    @Override
    public String toString(){
        return String.format("(%s) %s", this.type, this.details);
    }
}
