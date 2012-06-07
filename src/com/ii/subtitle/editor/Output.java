package com.ii.subtitle.editor;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



/**
 *
 * @author Ivo
 */
import java.util.ArrayList;
import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Output {

    public void writeSRT(ArrayList<Subtitle> subList, String path){
        try {
            File f = new File(path);
            if (f.exists() && f.canWrite()){
                f.delete();
            }
 
            BufferedWriter writer = new BufferedWriter(new FileWriter(path, true));

            for (Subtitle s: subList){
                String number = s.subtitleNumber + "";
                writer.write(number);
                writer.newLine();
                writer.write(s.startTimeToString() + " --> " + s.endTimeToString());
                writer.newLine();
                writer.write(s.SRTBoldTagBeginning() + s.SRTItalicsTagBeginning()
                        + s.SRTUnderlineTagBeginning());
                String newContent = s.content + s.SRTUnderlineTagEnd() + s.SRTItalicsTagEnd()
                        + s.SRTBoldTagEnd();
                for (String str: newContent.replace("|", "\n").split("\\n")){
                    writer.write(str);
                    writer.newLine();
                }
                writer.newLine();
            }

            writer.close();

            }catch(IOException e){
                System.out.println("There was a problem:" + e);        
        }
    }

    public void writeSUB(ArrayList<Subtitle> subList, String path, double FPS){

        try {
            File f = new File(path);
            if (f.exists() && f.canWrite()) {
                f.delete();
            }

            BufferedWriter writer = new BufferedWriter(new FileWriter(path, true));
            writer.write("{1}{1}" + FPS);
            writer.newLine();
            for (Subtitle s: subList){
                writer.write("{" + s.startFrameToString() + "}{" + s.endFrameToString() + "}" +
                        s.SUBBoldTag() + s.SUBItalicsTag() + s.SUBUnderlineTag() + s.content);
                writer.newLine();
            }

            writer.close();

        }catch(IOException e){
            System.out.println("There was a problem:" + e);
        }
    }

    public void writeQuote(Subtitle s, String path, String title){
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(path, true));
            writer.write("Title: " + title.replace(".sub", "").replace(".srt", ""));
            writer.newLine();
            writer.write("From: " + s.startTimeToString());
            writer.newLine();
            writer.write("To:   " + s.endTimeToString());
            writer.newLine();
            for (String str : s.content.replace("|", "\n").split("\\n")) {
                writer.write(str);
                writer.newLine();
            }
            writer.newLine();
            writer.newLine();

            writer.close();
        } catch (IOException e) {
            System.out.println("There was a problem:" + e);
        }

    }

}
