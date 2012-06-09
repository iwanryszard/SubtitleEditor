package com.ii.subtitle.editor;

import javax.swing.ListSelectionModel;

public class MoveUpCommand extends AbstractSubtitlesCommand
{
	private int startSubtitle;
	private int selectedCount;
	private ListSelectionModel selModel;
	private int firstSelIndex;
	private int secondSelIndex;
	
	public MoveUpCommand(Interface interf, SubsTableModel model, int startSubtitle, int selectedCount, 
			ListSelectionModel selModel)
	{
		super(interf, model);
		
		this.startSubtitle = startSubtitle;
		this.selectedCount = selectedCount;
		this.selModel = selModel;
	}
	
	@Override
	protected void executeAfterFireTableDataChanged()
	{
		selModel.setSelectionInterval(startSubtitle - 1, startSubtitle + selectedCount - 2);
	}
	
	@Override
	protected void undoAfterFireTableDataChanged()
	{
		selModel.setSelectionInterval(firstSelIndex, secondSelIndex);
	}
	
	@Override
	protected boolean internalExecute()
	{
		model.moveUpByOne(startSubtitle, selectedCount);
		
		firstSelIndex = selModel.getAnchorSelectionIndex();
		secondSelIndex = selModel.getLeadSelectionIndex();
		
		return true;
	}

	@Override
	protected boolean internalUndo()
	{
		return true;
	}

}
