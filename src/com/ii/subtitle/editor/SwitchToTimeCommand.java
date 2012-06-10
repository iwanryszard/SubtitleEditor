package com.ii.subtitle.editor;

public class SwitchToTimeCommand extends AbstractSubtitlesCommand
{
	private int subtitleIndex;
	private double framesPerSec;
	
	public SwitchToTimeCommand(Interface interf, Subtitles subtitles, int subtitleIndex, double framesPerSec)
	{
		super(interf, subtitles);
		
		this.subtitleIndex = subtitleIndex;
		this.framesPerSec = framesPerSec;
	}
	
	@Override
	protected boolean internalExecute()
	{
		interf.isFrames = false;
		
		subtitles.setFrameRatePerSecond(framesPerSec);
		subtitles.switchToTime();
		
		interf.manipulateRadioButtonsValues(true, false);
		if(subtitleIndex >= 0 && subtitleIndex < subtitles.getCount())
		{
			interf.manipulateEditPanelValues(subtitles.getStart(subtitleIndex), subtitles.getEnd(subtitleIndex), subtitles.getDuration(subtitleIndex), subtitles.getSubtitleHTMLFormattedText(subtitleIndex, false));
		}
		
		return true;
	}

	@Override
	protected boolean internalUndo()
	{
		return true;
	}

}
