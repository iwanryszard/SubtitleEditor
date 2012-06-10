package com.ii.subtitle.editor;

import javax.swing.table.AbstractTableModel;

public class SubsTableModel extends AbstractTableModel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7871409214981720369L;
	
	private static final String[] columns = new String[] {"Number", "Start", "End", "Content" };
	
	private Subtitles subsList;
	
	public SubsTableModel(Subtitles subsList)
	{
		this.subsList = subsList;
	}
	
	@Override
	public String getColumnName(int index)
	{
		return columns[index];
	}
	
	@Override
	public int getColumnCount()
	{
		return columns.length;
	}

	@Override
	public int getRowCount()
	{
		return subsList.getCount();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex)
	{
		
		switch(columnIndex)
		{
			case 0: 
				return rowIndex + 1;
			case 1:
				return subsList.getStart(rowIndex);
			case 2:
				return subsList.getEnd(rowIndex);
			case 3:
				return subsList.getSubtitleTextSingleLine(rowIndex);
			default:
				return null;
		}
	}
	
	public void setSubtitleList(Subtitles in)
	{
		this.subsList = in;
		fireTableDataChanged();
	}
	
}
