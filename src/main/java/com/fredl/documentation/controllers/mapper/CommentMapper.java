package com.fredl.documentation.controllers.mapper;

import com.fredl.documentation.data.ValidDocTags;
import com.fredl.documentation.models.Comment;
import com.fredl.documentation.models.Function;
import com.fredl.documentation.models.Returns;

import java.util.ArrayList;

/**
 * @author:     Fredrik F. Lindhagen <fred.lindh96@gmail.com>
 * @created:    25.07.2016
 * @desc:       CommentMapper is responsible for categorizing and mapping the comments
 */
public class CommentMapper {
    private boolean validComment = false;
    private Function documentation;
    private Comment comment;
    private String func;

    public CommentMapper(Comment comment){
        this.validComment = validateComment(comment.getComment());

        this.func = comment.getFunctionName();
        this.comment = comment;

    }

    public Function map(){
        Function doc = null;
        String description = "";
        String returns = "";
        String params = "";

        if(!this.validComment){
            return null;
        }

        String[] lines = comment.getComment().split("\n");

        String txt = "";
        boolean gatheringTxt = false;
        boolean finishedGathering = false;

        ValidDocTags currentPref = null;
        for(int i = 0; i < lines.length; i++){
            String l = lines[i];

            // STATE: Identify tags
            if(l.contains("@")){

                /*
                *   The '@' sign might occur inside a tag. Therefore we need to doublecheck if the
                *   tag is actually a valid new tag, or just a random occurence in the comment
                * */
                if(gatheringTxt){

                    // Check if the tag is valid or not
                    if(getPrefix(l) != null){
                        finishedGathering = true;
                        i--; // Take one step bak
                    }

                }else{

                    currentPref = getPrefix(l);

                    /*
                    *   If the newly discovered tag is not valid, i.e. null
                    *   Then just skip the current iteration...
                    *
                    *   IF the tag IS valid, then set the gathering state to active
                    * */
                    if(currentPref == null){
                        if(!gatheringTxt){
                            break;
                        }
                    }else{
                        gatheringTxt = true;
                    }
                }
            }

            // STATE: Gathering data
            if(gatheringTxt){
                if(l.contains("*/")){
                    gatheringTxt = false;
                    finishedGathering = true;
                }else{

                    if(!finishedGathering){
                        txt += l;
                    }

                    if(i < lines.length-1){
                        txt += "\n";
                    }
                }
            }

            // STATE: Place data
            if(finishedGathering){

                switch(currentPref){
                    case PARAM:
                        if(params.length() == 0){
                            params = txt;
                        }
                        break;
                    case DESC:
                        if(description.length() < 1){
                            description = txt;
                        }
                        break;
                    case RETURN:
                        if(returns.length() < 1){
                            returns = txt;
                        }
                        break;
                    default:
                        // Do nothing
                }

                txt = "";

                gatheringTxt = false;
                finishedGathering = false;
            }

        }

        doc = new Function(this.func, description);
        doc.setReturnDetails(returns);
        doc.addParam(params);


        return doc;
    }

    private ValidDocTags getPrefix(String line){
        ValidDocTags[] vdtList = ValidDocTags.values();

        // Iterate over every ValidDocTags
        for(ValidDocTags vdt : vdtList){

            String pref;
            for(String p : vdt.getPrefixes()){
                pref = "@"+p;

                if(line.contains(pref)){
                    // If the correct prefix is found, return the current ValidDocTags
                    return vdt;
                }
            }

        }


        return null;
    }





    public boolean validComment(){
        return this.validComment;
    }



    private boolean validateComment(String com){
        ValidDocTags[] vdtList = ValidDocTags.values();

        for(ValidDocTags vdt : vdtList){

            for(String pref : vdt.getPrefixes()){
                // If The commentary contains at least one valid prefix, the comment is valid.
                if(com.toLowerCase().contains("@"+pref)){
                    return true;
                }
            }
        }

        // If none of the valid prefixes was found. return false
        return false;
    }


}
