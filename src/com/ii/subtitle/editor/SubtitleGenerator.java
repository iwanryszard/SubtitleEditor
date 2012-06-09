package com.ii.subtitle.editor;

import java.io.IOException;

public interface SubtitleGenerator
{
	
	public void onGenerationStart() throws IOException;
	
	public void onSubtitleStarted(int index) throws IOException;
	
	public void onSubtitleStartDuration(int duration) throws IOException;
	
	public void onSubtitleEndDuration(int duration) throws IOException;
	
	public void onSubtitleStartText() throws IOException;
	
	public void onTextNewLine() throws IOException;
	
	public void onBoldTextBegin() throws IOException;
	
	public void onBoldTextEnd() throws IOException;
	
	public void onItalicsTextBegin() throws IOException;
	
	public void onItalicsTextEnd() throws IOException;
	
	public void onUnderlineTextBegin() throws IOException;
	
	public void onUnderlineTextEnd() throws IOException;
	
	public void onTypefaceBegin(String typeFaceName) throws IOException;
	
	public void onTypefaceEnd(String typeFaceName) throws IOException;
	
	public void onTextAdded(String text) throws IOException;
	
	public void onSubtitleEnded(int index) throws IOException;
	
	public void onGenerationEnd();
	
}
