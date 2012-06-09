package com.ii.subtitle.editor;


public class DeleteCommand extends AbstractSubtitlesCommand
{
	private int startSubtitle;
	private int count;
	
	public DeleteCommand(Interface interf, SubsTableModel model, int startSubtitle, int count)
	{
		super(interf, model);
		
		this.startSubtitle = startSubtitle;
		this.count = count;
	}

	@Override
	protected boolean internalExecute()
	{
		model.removeSubtitles(startSubtitle, count);
		return true;
	}

	@Override
	protected boolean internalUndo()
	{
		return true;
	}
}
