package com.Prince.FragMinecraft.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatUtils {
    public static String parseChatMessage(String chatMessage){
        String contentRegex = "content=\\\"([^\"]+)?\\\",";
        String childrenRegex = "children=\\[TextComponentImpl\\{content=\\\"([^\"]+)?\\\",";
        String hypixelWierdThingRegex = "children=\\[\\]\\}, TextComponentImpl\\{content=\\\"([^\"]+)?\\\",";
        Pattern contentPattern = Pattern.compile(contentRegex, Pattern.MULTILINE);
        Pattern childrenPattern = Pattern.compile(childrenRegex, Pattern.MULTILINE);
        Pattern hypixelWierdPattern = Pattern.compile(hypixelWierdThingRegex, Pattern.MULTILINE);

        String currCheck = chatMessage;
        String message = "";
        Matcher matcher = contentPattern.matcher(currCheck);
        matcher.find();
        if (matcher.group(1) != null) {
            message+=matcher.group(1);
        }
        matcher = childrenPattern.matcher(currCheck);
        while (matcher.find()) {
            for (int i = 1; i <= matcher.groupCount(); i++) {
                if(matcher.group(i)!=null) {
                    message += matcher.group(i);
                }
            }
        }
        matcher = hypixelWierdPattern.matcher(currCheck);
        while (matcher.find()) {
            for (int i = 1; i <= matcher.groupCount(); i++) {
                if(matcher.group(i)!=null) {
                    message += matcher.group(i);
                }
            }
        }
        return message;
    }
}
