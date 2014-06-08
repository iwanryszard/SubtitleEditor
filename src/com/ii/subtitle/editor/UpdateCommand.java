package com.ii.subtitle.editor;

import com.ii.subtitle.editor.SubtitlesParser.WrongFormatException;

public class UpdateCommand extends AbstractSubtitlesCommand
{
	private int subtitleIndex;
	private String text;
	private String start;
	private String end;
	private String duration;
	private SubtitleItem oldSubtitle;

	public UpdateCommand(Interface interf, Subtitles subtitles, int index, String text, String start, String end, String duration)
	{
		super(interf, subtitles);
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
				interf.manipulateEditPanelValues(subtitles.getStart(subtitleIndex), subtitles.getEnd(subtitleIndex), subtitles.getDuration(subtitleIndex), subtitles.getSubtitleHTMLFormattedText(subtitleIndex, false));
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
