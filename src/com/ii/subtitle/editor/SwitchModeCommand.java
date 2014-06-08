package com.ii.subtitle.editor;

public class SwitchModeCommand extends AbstractSubtitlesCommand
{
	private int subtitleIndex;
	private double framesPerSec;
	private boolean isInFrames;
	private Subtitles.SavedState savedState;
	
	private void switchMode(){
		this.subtitles.setInFrames(this.isInFrames);
		this.subtitles.setFrameRatePerSecond(framesPerSec);
		double factor = this.isInFrames ? (framesPerSec / 1000) : (1000 / framesPerSec);
		for (SubtitleItem item : this.subtitles)
		{
			int start = (int) Math.round(item.getStart() * factor);
			int end = (int) Math.round(item.getEnd() * factor);
			item.setStart(start);
			item.setEnd(end);
		}
	}

	public SwitchModeCommand(Interface interf, Subtitles subtitles, int subtitleIndex, double framesPerSec, boolean isFrames)
	{
		super(interf, subtitles);

		this.subtitleIndex = subtitleIndex;
		this.framesPerSec = framesPerSec;
		this.isInFrames = isFrames;
	}

	@Override
	protected boolean internalExecute()
	{
		if (this.subtitles.size() == 0 || this.subtitles.isInFrames() == this.isInFrames){
			return false;
		}
		
		this.savedState = this.subtitles.createSavedState();
		this.switchMode();
		interf.isFrames = this.isInFrames;

		interf.manipulateRadioButtonsValues(false, true);
		if (subtitleIndex >= 0 && subtitleIndex < subtitles.size())
		{
			interf.manipulateEditPanelValues(subtitles.getStart(subtitleIndex), subtitles.getEnd(subtitleIndex),
					subtitles.getDuration(subtitleIndex), subtitles.getSubtitleHTMLFormattedText(subtitleIndex, false));
		}
		return true;
	}

	@Override
	protected boolean internalUndo()
	{
		this.subtitles.restoreToState(this.savedState);
		return true;
	}

}
