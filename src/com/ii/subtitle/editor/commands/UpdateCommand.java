package com.ii.subtitle.editor.commands;

import com.ii.subtitle.input.UserInputSubtitleParser;
import com.ii.subtitle.input.SubtitlesParser.WrongFormatException;
import com.ii.subtitle.model.SubtitleItem;
import com.ii.subtitle.model.Subtitles;

public class UpdateCommand extends AbstractSubtitlesCommand
{
	private int subtitleIndex;
	private String text;
	private String start;
	private String end;
	private String duration;
	private SubtitleItem oldSubtitle;

	public UpdateCommand(SelectionModel model, Subtitles subtitles, String text, String start, String end, String duration)
	{
		super(model, subtitles);
		this.text = text;
		this.start = start;
		this.end = end;
		this.duration = duration;
		this.oldSubtitle = null;
		this.subtitleIndex = this.model.getStartSelectionIndex();
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
