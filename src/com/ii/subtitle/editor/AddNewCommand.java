package com.ii.subtitle.editor;

import javax.swing.ListSelectionModel;

public class AddNewCommand extends AbstractSubtitlesCommand
{
	private int subtitleIndex;
	private ListSelectionModel selModel;
	private int firstSelIndex;
	private int secondSelIndex;
	
	public AddNewCommand(Interface interf, SubsTableModel model, int subtitleIndex, ListSelectionModel selModel)
	{
		super(interf, model);
		
		this.subtitleIndex = subtitleIndex;
		this.selModel = selModel;
	}
	
	@Override
	protected void executeAfterFireTableDataChanged()
	{
		selModel.setSelectionInterval(subtitleIndex, subtitleIndex);
	}
	
	@Override
	protected void undoAfterFireTableDataChanged()
	{
		selModel.setSelectionInterval(firstSelIndex, secondSelIndex);
	}
	
	@Override
	protected boolean internalExecute()
	{
		interf.hasFrames = false;
		interf.hasTimes = false;
		
		model.addNewSubtitle(subtitleIndex);
		
		firstSelIndex = selModel.getAnchorSelectionIndex();
		secondSelIndex = selModel.getLeadSelectionIndex();
		
		Subtitle s = model.getSubtitleList().getSubtitleFromIndex(subtitleIndex);

		interf.manipulateEditPanelValues(s.startTimeToString(), s.endTimeToString(), s.durationTimeToString(), s.content, 
				s.bold, s.italics, s.underline);
		return true;
	}

	@Override
	protected boolean internalUndo()
	{
		return true;
	}

}
