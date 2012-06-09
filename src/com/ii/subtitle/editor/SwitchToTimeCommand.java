package com.ii.subtitle.editor;

public class SwitchToTimeCommand extends AbstractSubtitlesCommand
{
	private int subtitleIndex;
	private double framesPerSec;
	
	public SwitchToTimeCommand(Interface interf, SubsTableModel model, int subtitleIndex, double framesPerSec)
	{
		super(interf, model);
		
		this.subtitleIndex = subtitleIndex;
		this.framesPerSec = framesPerSec;
	}
	
	@Override
	protected boolean internalExecute()
	{
		interf.isFrames = false;
		interf.hasTimes = true;
		
		model.convertFromFramesToTime(framesPerSec);
		
		interf.manipulateRadioButtonsValues(true, false);
		if(subtitleIndex >= 0)
		{
			Subtitle s = model.getSubtitleList().getSubtitleFromIndex(subtitleIndex);
			interf.manipulateEditPanelValues(s.startTimeToString(), s.endTimeToString(), s.durationTimeToString(), s.content, 
					s.bold, s.italics, s.underline);
		}
		
		return true;
	}

	@Override
	protected boolean internalUndo()
	{
		return true;
	}

}
