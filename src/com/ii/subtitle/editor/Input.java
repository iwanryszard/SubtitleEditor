package com.ii.subtitle.editor;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.ArrayList;
import java.io.FileInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author Ivo
 */
public class Input {

    public ArrayList<Subtitle> subList = new ArrayList<Subtitle>();
    public double framesPerSecond = 23.976;

    public String read(File file){

        StringBuilder input = new StringBuilder();

        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(new FileInputStream(file), "UTF-8"));
            String line;
            while (( line = reader.readLine()) != null){
            input.append(line).append("\n");
        }

        return input.toString();
        }
        catch (FileNotFoundException e) {
        return "";
        }
        catch (IOException e) {
        return "";
        }



    }

    public void inputSRT(String input){
        input = "\n\n" + input;
        String[] subtitleRaw = input.split("(\\n\\n)(\\d+)(\\n)");
        String[] subtitleAttributes1;
        String[] subtitleAttributes2;

        for (int i = 1; i < subtitleRaw.length; i++){
            Subtitle sub = new Subtitle();
            sub.setSubtitleNumber(i);
            subtitleAttributes1 = subtitleRaw[i].split(" --> ");
            if(subtitleAttributes1.length > 1){
                sub.calculateStartTime(subtitleAttributes1[0]);
                subtitleAttributes2 = subtitleAttributes1[1].split("\\n");
                if (subtitleAttributes2.length > 1){
                    sub.calculateEndTime(subtitleAttributes2[0]);
                    if (subtitleAttributes2[1].contains("<b>")){
                        sub.setBold(true);
                        subtitleAttributes2[1] = subtitleAttributes2[1].replace("<b>", "");
                    }
                    if (subtitleAttributes2[1].contains("<u>")){
                        sub.setUnderline(true);
                        subtitleAttributes2[1] = subtitleAttributes2[1].replace("<u>", "");
                    }
                    if (subtitleAttributes2[1].contains("<i>")){
                        sub.setItalics(true);
                        subtitleAttributes2[1] = subtitleAttributes2[1].replace("<i>", "");
                    }
                    for (int j = 1; j < subtitleAttributes2.length -1; j++){
                        sub.addContent(subtitleAttributes2[j]+"|");
                    }
                    subtitleAttributes2[subtitleAttributes2.length-1] =
                            subtitleAttributes2[subtitleAttributes2.length-1].replace("</b>", "");
                    subtitleAttributes2[subtitleAttributes2.length-1] =
                            subtitleAttributes2[subtitleAttributes2.length-1].replace("</u>", "");
                    subtitleAttributes2[subtitleAttributes2.length-1] =
                            subtitleAttributes2[subtitleAttributes2.length-1].replace("</i>", "");
                    sub.addContent(subtitleAttributes2[subtitleAttributes2.length-1]);
                    subList.add(i-1, sub);
                }
            }
        }

    }

    public void inputSUB(String input){
        String[] subtitleRaw = input.split("\\n");
        boolean hasFramerate = (subtitleRaw.length > 0);
        int start = 0;
        if (hasFramerate){
            if(subtitleRaw[0].startsWith("﻿{1}{1}")){
                start = 1;
                framesPerSecond = Double.parseDouble(subtitleRaw[0].replace("﻿{1}{1}", ""));
            }
            if (subtitleRaw[0].startsWith("{1}{1}")){
                start = 1;
            framesPerSecond = Double.parseDouble(subtitleRaw[0].replace("{1}{1}", ""));
            }
        }

        int count = 0;
        String[] subtitleAttributes;
        for (int i = start; i < subtitleRaw.length; i++){
            Subtitle sub = new Subtitle();
            count++;
            sub.setSubtitleNumber(count);
            subtitleAttributes = subtitleRaw[i].split("[{}]");//proveri dali raboti!!!
            if (subtitleAttributes.length > 1){
                sub.setStartFrame(subtitleAttributes[1]);
            }
            if (subtitleAttributes.length > 3){
                sub.setEndFrame(subtitleAttributes[3]);
            }
            for (int j = 4; j < subtitleAttributes.length; j++){
                if (subtitleAttributes[j].equals("y:b")){
                    sub.setBold(true);
                    subtitleAttributes[j] = "";
                }
                if (subtitleAttributes[j].equals("y:u")){
                    sub.setUnderline(true);
                    subtitleAttributes[j] = "";
                }
                if (subtitleAttributes[j].equals("y:i")){
                    sub.setItalics(true);
                    subtitleAttributes[j] = "";
                }
                sub.addContent(subtitleAttributes[j]);
            }
            subList.add(sub);
        }
    }

}
