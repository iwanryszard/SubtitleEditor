package com.ii.subtitle.input;

import com.ii.subtitle.model.SubtitleItem;
import com.ii.subtitle.model.SubtitleText;

public class UserInputSubtitleParser extends SrtParser
{

	private boolean isFrames;
	private String start;
	private String end;
	private String duration;
	private SubtitleItem sub;
	
	
	public UserInputSubtitleParser(String text, String start, String end, String duration, boolean isFrames)
	{
		super(text);
		this.start = start;
		this.end = end;
		this.duration = duration;
		this.isFrames = isFrames;
	}
	
	
	@Override
	public void parse() throws WrongFormatException
	{
		String text = this.getContent();
		text = text.replaceAll("<br\\s*/?>", "\n");
		text = text.trim();
		text = text.replaceAll("<\\n++", "\n");
		if(this.isFrames)
		{
			int startFrame = -1;
			int endFrame = -1;
			if (start.matches("\\d+") && end.matches("\\d+"))
			{
				startFrame = Integer.parseInt(start);
				endFrame = Integer.parseInt(end);
			}
			else if (start.matches("\\d+") && duration.matches("\\d+"))
			{
				startFrame = Integer.parseInt(start);
				endFrame = startFrame + Integer.parseInt(end);
			}
			if(startFrame != -1)
			{
				SubtitleText parsedText = getText(text);
				this.sub = new SubtitleItem(startFrame, endFrame, parsedText);
			}
		}
		else
		{
			int startTime = -1;
			int endTime = -1;
			if (start.matches("\\d\\d:\\d\\d:\\d\\d,\\d\\d\\d") && end.matches("\\d\\d:\\d\\d:\\d\\d,\\d\\d\\d"))
			{
				startTime = getTime(start);
				endTime = getTime(end);
			}
			else if (start.matches("\\d\\d:\\d\\d:\\d\\d,\\d\\d\\d") && duration.matches("\\d\\d:\\d\\d:\\d\\d,\\d\\d\\d"))
			{
				startTime = getTime(start);
				endTime = startTime + getTime(duration);
			}
			if(startTime != -1)
			{
				SubtitleText parsedText = getText(text);
				this.sub = new SubtitleItem(startTime, endTime, parsedText);
			}
		}
	}
	
	public SubtitleItem getSubtitle()
	{
		return this.sub;
	}
	

}
