package com.ii.subtitle.editor;

public class UpdateCommand extends AbstractSubtitlesCommand
{
	private int subtitleIndex;
	private String text;
	private String start;
	private String end;
	private String duration;

	public UpdateCommand(Interface interf, Subtitles subtitles, int index, String text, String start, String end, String duration)
	{
		super(interf, subtitles);
		this.subtitleIndex = index;
		this.text = text;
		this.start = start;
		this.end = end;
		this.duration = duration;
	}

	@Override
	protected boolean internalExecute()
	{
		subtitles.update(subtitleIndex, start, end, duration, text);
		interf.manipulateEditPanelValues(subtitles.getStart(subtitleIndex), subtitles.getEnd(subtitleIndex), subtitles.getDuration(subtitleIndex), subtitles.getSubtitleHTMLFormattedText(subtitleIndex, false));
		return true;
	}

	@Override
	protected boolean internalUndo()
	{
		return true;
	}

}
