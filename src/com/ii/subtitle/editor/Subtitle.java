package com.ii.subtitle.editor;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Ivo
 */
//import java.util.List;
//import java.util.ArrayList;
//import java.lang.Math;
public class Subtitle {

    public String content;
    public boolean bold;
    public boolean italics;
    public boolean underline;
    public int subtitleNumber;
    private int startHours;
    private int startMinutes;
    private int startSeconds;
    private int startMiliseconds;
    private int endHours;
    private int endMinutes;
    private int endSeconds;
    private int endMiliseconds;
    public int startFrame;
    public int endFrame;

    public Subtitle(String content, boolean bold, boolean italics, boolean underline,
            int subtitleNumber, int startHours, int startMinutes, int startSeconds,
            int startMiliseconds, int endHours, int endMinutes, int endSeconds,
            int endMiliseconds) {
        this.content = content;
        this.bold = bold;
        this.italics = italics;
        this.underline = underline;
        this.subtitleNumber = subtitleNumber;
        this.startHours = startHours;
        this.startMinutes = startMinutes;
        this.startSeconds = startSeconds;
        this.startMiliseconds = startMiliseconds;
        this.endHours = endHours;
        this.endMinutes = endMinutes;
        this.endSeconds = endSeconds;
        this.endMiliseconds = endMiliseconds;
    }

    public Subtitle(String content, boolean bold, boolean italics, boolean underline,
            int subtitleNumber, int startFrame, int endFrame) {
        this.content = content;
        this.bold = bold;
        this.italics = italics;
        this.underline = underline;
        this.subtitleNumber = subtitleNumber;
        this.startFrame = startFrame;
        this.endFrame = endFrame;
    }

    public Subtitle() {
        this.content = "";
        this.bold = false;
        this.italics = false;
        this.underline = false;
        this.subtitleNumber = 0;
        this.startHours = 0;
        this.startMinutes = 0;
        this.startSeconds = 0;
        this.startMiliseconds = 0;
        this.endHours = 0;
        this.endMinutes = 0;
        this.endSeconds = 0;
        this.endMiliseconds = 0;
        this.startFrame = 0;
        this.endFrame = 0;
    }

    public String startTimeToString() {
        String hoursString;
        if (startHours / 10 == 0) {
            hoursString = "0" + startHours;
        } else {
            hoursString = "" + startHours;
        }
        String minutesString;
        if (startMinutes / 10 == 0) {
            minutesString = "0" + startMinutes;
        } else {
            minutesString = "" + startMinutes;
        }
        String secondsString;
        if (startSeconds / 10 == 0) {
            secondsString = "0" + startSeconds;
        } else {
            secondsString = "" + startSeconds;
        }
        String milisecondsString;
        if (startMiliseconds / 10 == 0) {
            milisecondsString = "00" + startMiliseconds;
        } else {
            if (startMiliseconds / 100 == 0) {
                milisecondsString = "0" + startMiliseconds;
            } else {
                milisecondsString = "" + startMiliseconds;
            }
        }
        return hoursString + ":" + minutesString + ":" + secondsString + ","
                + milisecondsString;
    }

    public String endTimeToString() {
        String hoursString;
        if (endHours / 10 == 0) {
            hoursString = "0" + endHours;
        } else {
            hoursString = "" + endHours;
        }
        String minutesString;
        if (endMinutes / 10 == 0) {
            minutesString = "0" + endMinutes;
        } else {
            minutesString = "" + endMinutes;
        }
        String secondsString;
        if (endSeconds / 10 == 0) {
            secondsString = "0" + endSeconds;
        } else {
            secondsString = "" + endSeconds;
        }
        String milisecondsString;
        if (endMiliseconds / 10 == 0) {
            milisecondsString = "00" + endMiliseconds;
        } else {
            if (endMiliseconds / 100 == 0) {
                milisecondsString = "0" + endMiliseconds;
            } else {
                milisecondsString = "" + endMiliseconds;
            }
        }
        return hoursString + ":" + minutesString + ":" + secondsString + ","
                + milisecondsString;
    }

    public String startFrameToString(){
        return startFrame + "";
    }

    public String endFrameToString(){
        return endFrame + "";
    }

    public void calculateStartTime(String startTimeString){
        String startTimeArray[] = startTimeString.split("[:,]");
        if (startTimeArray.length > 3){
            startMiliseconds = Integer.parseInt(startTimeArray[3]);
            startSeconds = Integer.parseInt(startTimeArray[2]) % 60;
            startMinutes = (Integer.parseInt(startTimeArray[1]) +
                    Integer.parseInt(startTimeArray[2]) / 60) % 60;
            startHours = Integer.parseInt(startTimeArray[0]) +
                    (Integer.parseInt(startTimeArray[1]) +
                    Integer.parseInt(startTimeArray[2]) / 60) / 60;
        }
    }

    public void calculateEndTime(String endTimeString) {
        String endTimeArray[] = endTimeString.split("[:,]");
        if (endTimeArray.length > 3){
            endMiliseconds = Integer.parseInt(endTimeArray[3]);
            endSeconds = Integer.parseInt(endTimeArray[2]) % 60;
            endMinutes = (Integer.parseInt(endTimeArray[1]) +
                    Integer.parseInt(endTimeArray[2]) / 60) % 60;
            endHours = Integer.parseInt(endTimeArray[0]) +
                    (Integer.parseInt(endTimeArray[1]) +
                    Integer.parseInt(endTimeArray[2]) / 60) / 60;
        }
    }

    public void calculateEndFrameUsingDuraton(String durationFrameString){
        if (durationFrameString.matches("\\d+")){
            int end = startFrame + Integer.parseInt(durationFrameString);
            if (end < 0)
                end = 0;
            endFrame = end;
        }  
    }

    public void calculateEndTimeUsingDuration(String durationTimeString) {
    String durationTimeArray[] = durationTimeString.split("[:,]");
    if (durationTimeArray.length > 3){
        endMiliseconds = (startMiliseconds + Integer.parseInt(durationTimeArray[3]))%1000;
        endSeconds = (startSeconds + Integer.parseInt(durationTimeArray[2]) +
                (startMiliseconds + Integer.parseInt(durationTimeArray[3]))/1000) % 60;
        endMinutes = (startMinutes + Integer.parseInt(durationTimeArray[1]) +
                (startSeconds + Integer.parseInt(durationTimeArray[2]) +
                (startMiliseconds + Integer.parseInt(durationTimeArray[3]))/1000) / 60) % 60;
        endHours = Integer.parseInt(durationTimeArray[0]) +
                (startMinutes + Integer.parseInt(durationTimeArray[1]) +
                (startSeconds + Integer.parseInt(durationTimeArray[2]) +
                (startMiliseconds + Integer.parseInt(durationTimeArray[3]))/1000) / 60) / 60;
        }
    }

    public String durationFramesToString(){
        int duration  = endFrame - startFrame;
            if (duration < 0)
                duration = 0;
        return duration + "";
    }

    public String durationTimeToString(){
        int duration = (endHours * 3600 + endMinutes * 60 + endSeconds) * 1000 + endMiliseconds -
                (startHours * 3600 + startMinutes * 60 + startSeconds) * 1000 - startMiliseconds;
        if (duration <= 0){
            return "00:00:00,000";
        }
        else {
            int ms = duration % 1000;
            duration = duration / 1000;
            int s = duration % 60;
            duration = duration / 60;
            int m = duration % 60;
            duration = duration / 60;
            int h = duration;
            String hoursString;
            if (h / 10 == 0) {
                hoursString = "0" + h;
            } else {
                hoursString = "" + h;
            }
            String minutesString;
            if (m / 10 == 0) {
                minutesString = "0" + m;
            } else {
                minutesString = "" + m;
            }
            String secondsString;
            if (s / 10 == 0) {
                secondsString = "0" + s;
            } else {
                secondsString = "" + s;
            }
            String milisecondsString;
            if (ms / 10 == 0) {
                milisecondsString = "00" + ms;
            } else {
                if (ms / 100 == 0) {
                    milisecondsString = "0" + ms;
                } else {
                    milisecondsString = "" + ms;
                }
            }
            return hoursString + ":" + minutesString + ":" + secondsString + ","
                    + milisecondsString;
        }
    }

    public void timeToFrames(double framesPerSecond){
        startFrame = (int)Math.round((startHours*3600 + startMinutes*60 + startSeconds +
                ((double)startMiliseconds) / 1000)*framesPerSecond);
        endFrame = (int)Math.round((endHours*3600 + endMinutes*60 + endSeconds +
                ((double)endMiliseconds) / 1000)*framesPerSecond);
    }

    public void framesToTime(double framesPerSecond){
        double seconds = (double)startFrame / framesPerSecond;
        startHours = (int)(seconds / 3600);
        seconds = seconds - startHours * 3600;
        startMinutes = (int)(seconds / 60);
        seconds = seconds - startMinutes * 60;
        startSeconds = (int)seconds;
        startMiliseconds = (int)(Math.round(seconds * 1000) % 1000);
        seconds = (double)endFrame / framesPerSecond;
        endHours = (int)(seconds / 3600);
        seconds = seconds - endHours * 3600;
        endMinutes = (int)(seconds / 60);
        seconds = seconds - endMinutes * 60;
        endSeconds = (int)seconds;
        endMiliseconds = (int)(Math.round(seconds * 1000) % 1000);
    }

    public void setSubtitleNumber(int subtitleNumber) {
        this.subtitleNumber = subtitleNumber;
    }

    public void addContent(String content) {
        this.content = this.content + content;
    }

    public void setBold(boolean bold) {
        this.bold = bold;
    }

    public void setItalics(boolean italics) {
        this.italics = italics;
    }

    public void setUnderline(boolean underline) {
        this.underline = underline;
    }

    public void setEndFrame(String endFrame) {
        this.endFrame = Integer.parseInt(endFrame);
    }

    public void setStartFrame(String startFrame) {
        this.startFrame = Integer.parseInt(startFrame);
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String SRTBoldTagBeginning(){
        if (bold){
            return "<b>";
        }
        else{
            return "";
        }
    }

    public String SRTBoldTagEnd(){
        if (bold){
            return "</b>";
        }
        else{
            return "";
        }
    }

    public String SRTItalicsTagBeginning(){
        if (italics){
            return "<i>";
        }
        else{
            return "";
        }
    }

    public String SRTItalicsTagEnd(){
        if (italics){
            return "</i>";
        }
        else{
            return "";
        }
    }

    public String SRTUnderlineTagBeginning(){
        if (underline){
            return "<u>";
        }
        else{
            return "";
        }
    }
    
    public String SRTUnderlineTagEnd(){
        if (underline){
            return "</u>";
        }
        else{
            return "";
        }
    }

    public String SUBBoldTag(){
        if (bold){
            return "{y:b}";
        }
        else{
            return "";
        }
    }

    public String SUBItalicsTag(){
        if (italics){
            return "{y:i}";
        }
        else{
            return "";
        }
    }

    public String SUBUnderlineTag(){
        if (underline){
            return "{y:u}";
        }
        else{
            return "";
        }
    }

    public String SubtitleNumberToString() {
        return subtitleNumber + "";
    }



}
