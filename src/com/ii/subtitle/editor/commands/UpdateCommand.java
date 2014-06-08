package com.ii.subtitle.editor.commands;

import com.ii.subtitle.editor.SubtitleItem;
import com.ii.subtitle.editor.Subtitles;
import com.ii.subtitle.editor.UserInputSubtitleParser;
import com.ii.subtitle.editor.SubtitlesParser.WrongFormatException;

public class UpdateCommand extends AbstractSubtitlesCommand
{
	private int subtitleIndex;
	private String text;
	private String start;
	private String end;
	private String duration;
	private SubtitleItem oldSubtitle;

	public UpdateCommand(Subtitles subtitles, int index, String text, String start, String end, String duration)
	{
		super(subtitles);
		this.subtitleIndex = index;
		this.text = text;
		this.start = start;
		this.end = end;
		this.duration = duration;
		this.oldSubtitle = null;
	}

	@Override
	protected boolean internalExecute()
	{
		UserInputSubtitleParser parser = new UserInputSubtitleParser(text, start, end, duration, subtitles.isInFrames());
		try
		{
			parser.parse();
			if(parser.getSubtitle() != null)
			{
				this.oldSubtitle = subtitles.set(subtitleIndex, parser.getSubtitle());
				return true;
			}
		}
		catch (WrongFormatException e)
		{
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	protected boolean internalUndo()
	{
		if (this.oldSubtitle != null)
		{
			subtitles.set(subtitleIndex, this.oldSubtitle);
			return true;
		}
		return false;
	}

}
