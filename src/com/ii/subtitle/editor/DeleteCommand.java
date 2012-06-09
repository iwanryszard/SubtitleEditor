package com.ii.subtitle.editor;

import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class DeleteCommand extends BaseCommand
{
	private Interface interf;
	private boolean saved;
	private int currentSubtitle;
	private String startFieldText;
	private String endFieldText;
	private String durationFieldText;
	private String textAreaText;
	private boolean boldSelected;
	private boolean italicsSelected;
	private boolean underlineSelected;
	private ArrayList<Subtitle> subList;

	public DeleteCommand(Interface interf)
	{
		this.interf = interf;
	}
	
	@SuppressWarnings("unchecked")
	private void saveOldValuesForUndo()
	{
		currentSubtitle = interf.getCurrentSubtitle();
		saved = interf.isSaved();
		startFieldText = interf.getStartField().getText();
		endFieldText = interf.getEndField().getText();
		durationFieldText = interf.getDurationField().getText();
		textAreaText = interf.getTextArea().getText();
		boldSelected = interf.getBoldCheckBox().isSelected();
		italicsSelected = interf.getItalicsCheckBox().isSelected();
		underlineSelected = interf.getUnderlineCheckBox().isSelected();
		subList = (ArrayList<Subtitle>)interf.getIn().subList.clone();
		//add deep copy
	}

	@Override
	public boolean execute()
	{
		saveOldValuesForUndo();
		
		interf.setCurrentSubtitle(interf.getjTable2().getSelectedRow());
		if (interf.getCurrentSubtitle() >= 0)
		{
			interf.setSaved(false);
			int length = interf.getjTable2().getSelectedRowCount();
			for (int i = interf.getCurrentSubtitle() + length - 1; i >= interf.getCurrentSubtitle(); i--)
			{
				((DefaultTableModel)interf.getjTable2().getModel()).removeRow(i);
			}
			for (int i = interf.getCurrentSubtitle() + length - 1; i >= interf.getCurrentSubtitle(); i--)
			{
				interf.getIn().subList.remove(i);
			}
			for (int i = interf.getCurrentSubtitle() + interf.getjTable2().getSelectedRowCount(); i < interf.getIn().subList.size(); i++)
			{
				interf.getIn().subList.get(i).setSubtitleNumber(i + 1);
			}
			for (int i = interf.getCurrentSubtitle() + interf.getjTable2().getSelectedRowCount(); i < interf.getjTable2().getRowCount(); i++)
			{
				interf.getjTable2().getModel().setValueAt(interf.getIn().subList.get(i).subtitleNumber, i, 0);
			}
			interf.getStartField().setText("");
			interf.getEndField().setText("");
			interf.getDurationField().setText("");
			interf.getTextArea().setText("");
			interf.getBoldCheckBox().setSelected(false);
			interf.getItalicsCheckBox().setSelected(false);
			interf.getUnderlineCheckBox().setSelected(false);
		}

		return true;
	}

	@Override
	public boolean undo()
	{
		interf.setSaved(saved);
		interf.getStartField().setText(startFieldText);
		interf.getEndField().setText(endFieldText);
		interf.getDurationField().setText(durationFieldText);
		interf.getTextArea().setText(textAreaText);
		interf.setCurrentSubtitle(currentSubtitle);
		interf.getBoldCheckBox().setSelected(boldSelected);
		interf.getItalicsCheckBox().setSelected(italicsSelected);
		interf.getUnderlineCheckBox().setSelected(underlineSelected);
		interf.getIn().subList = subList;
		
		
		return true;
	}
}
