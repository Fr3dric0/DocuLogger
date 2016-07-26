package com.fredl.documentation.data;

/**
 * @author: Fredrik F. Lindhagen <fred.lindh96@gmail.com>
 * @created: 25.07.2016
 * @desc: .
 */
public enum ValidDocTags {
    PARAM("parameters", "param", "parameter", "params", "parameters"),
    DESC("description", "desc", "description"),
    RETURN("return", "return");

    private final String name;
    private final String[] prefixes;

    ValidDocTags(String name, String... prefixes){
        this.name = name;
        this.prefixes = prefixes;
    }


    @Override
    public String toString(){
        String pref = "(";

        int idx = 0;
        for(String p : this.prefixes){
            pref += "@"+p;

            if(idx < this.prefixes.length-1){
                pref += ", ";
            }

            idx++;
        }
        pref += ")";

        return this.name+" "+pref;
    }

    /**
     *  @param:     (String)    pref    The custom prefix
     *  @desc:      Checks with the existing prefixes, if the provided one is valid
     *  @return:    (Boolean)   True if the prefix is valid. Else, false
     * */
    public boolean validPrefix(String pref){
        for(String p : this.prefixes){
            if(p.equals(pref.toLowerCase())){
                return true;
            }
        }

        return false;
    }

    public String[] getPrefixes(){
        return this.prefixes;
    }
    public String getName(){
        return this.name;
    }
}
