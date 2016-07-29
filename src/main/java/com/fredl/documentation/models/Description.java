package com.fredl.documentation.models;

import com.fredl.documentation.data.ValidDocTags;
import com.sun.xml.internal.ws.api.message.ExceptionHasMessage;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: Fredrik F. Lindhagen <fred.lindh96@gmail.com>
 * @created: 29.07.2016
 * @desc:   Class responsible for handling the description.
 *          Consist mainly of a static method.
 *          However as DocuLogger evolves, we might want to extend the support for html-formatting into this
 */
public class Description {
    private static String description;
    private static final String COMMENT_LINE_BUFFER = "*";
    private static String prefix;

    public static String parse(String desc){
        StringBuilder builder = new StringBuilder(desc);

        // Delete the '@desc:' prefix
        prefix = findPrefix(desc, ValidDocTags.DESC);
        int prefixPos = builder.indexOf(prefix);
        builder.delete(prefixPos, prefixPos+prefix.length());

        builder = new StringBuilder(removeTabs(builder.toString().split("\n")));

        return builder.toString();
    }

    private static String removeTabs(String[] lines){
        StringBuilder builder = new StringBuilder();

        int idx = 0;
        for(String l : lines){
            l = l.replace(COMMENT_LINE_BUFFER, "");

            int posLetter = findCharStart(l);

            if(posLetter != -1) {
                builder.append(l.substring(posLetter));
                if(idx < lines.length-1){
                    builder.append("<br/>");
                }
            }

            idx++;
        }

        return builder.toString();
    }

    private static int findCharStart(String str){
        String pattern = "\\S";

        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(str);

        if(m.find()){
            int pos = str.indexOf(m.group(0));
            return pos;
        }

        return -1;
    }

    private static String findPrefix(String desc, ValidDocTags tags){
        String pattern = "";
        for(String t : tags.getPrefixes()){
            pattern = String.format("@%s:", t);

            if(desc.contains(pattern)){
                return pattern;
            }
        }
        return null;
    }
}
