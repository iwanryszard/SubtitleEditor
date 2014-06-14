/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Interface.java
 *
 * Created on 2010-12-23, 14:46:43
 */

package com.ii.subtitle.editor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.LayoutStyle.ComponentPlacement;

import com.ii.subtitle.editor.commands.AbstractSubtitlesCommand.SelectionModel;
import com.ii.subtitle.editor.commands.AddNewCommand;
import com.ii.subtitle.editor.commands.Command;
import com.ii.subtitle.editor.commands.CommandController;
import com.ii.subtitle.editor.commands.DeleteCommand;
import com.ii.subtitle.editor.commands.InterpolateCommand;
import com.ii.subtitle.editor.commands.MoveCommand;
import com.ii.subtitle.editor.commands.SwitchModeCommand;
import com.ii.subtitle.editor.commands.TranslateCommand;
import com.ii.subtitle.editor.commands.UpdateCommand;
import com.ii.subtitle.editor.commands.CommandController.CommandActionsHandler;
import com.ii.subtitle.input.SrtParser;
import com.ii.subtitle.input.SubtitleParserFactory;
import com.ii.subtitle.input.SubtitlesParser;
import com.ii.subtitle.input.SubtitlesParser.WrongFormatException;
import com.ii.subtitle.model.SubtitleFormat;
import com.ii.subtitle.model.SubtitleItem;
import com.ii.subtitle.model.Subtitles;
import com.ii.subtitle.output.AbstractSubtitlesWriter;
import com.ii.subtitle.output.SrtWriter;
import com.ii.subtitle.output.SubWriter;
import com.ii.subtitle.output.WriteDirector;

import javax.swing.JTextField;

public class Interface extends javax.swing.JFrame implements CommandActionsHandler, SelectionModel, ActionListener
{

	private static final long serialVersionUID = -6987211339001684349L;

	public static void main(String args[])
	{
		java.awt.EventQueue.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				new Interface().setVisible(true);
			}
		});

	}

	private javax.swing.JComboBox<String> FPSComboBox;

	private javax.swing.JLabel FPSLabel;

	private javax.swing.JTextPane TextArea;

	private javax.swing.JButton deleteButton;

	private javax.swing.JLabel durationLabel;

	private javax.swing.JButton editButton;
	private javax.swing.JLabel endLabel;
	private javax.swing.JRadioButton framesRadioButton;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JScrollPane jScrollPane2;
	private javax.swing.JTable jTable2;
	private javax.swing.JTextField startField;
	private javax.swing.JTextField endField;
	private javax.swing.JTextField durationField;
	private javax.swing.JButton moveDownButton;
	private javax.swing.JButton moveUpButton;
	private javax.swing.JButton newSubAfterButton;
	private javax.swing.JButton newSubBeforeButton;
	private javax.swing.JButton openButton;
	private javax.swing.JButton saveAsButton;
	private javax.swing.JButton saveButton;
	private javax.swing.JLabel startLabel;
	private javax.swing.JLabel switchLabel;
	private javax.swing.JLabel textLabel;
	private javax.swing.JRadioButton timeRadioButton;
	private JButton btnInterpolate;
	private JButton btnTranslate;
	private Subtitles in = new Subtitles(new ArrayList<SubtitleItem>(), SubtitleFormat.SUBRIP);
	private File currentFile;

	private boolean isSaved = false;
	private JTextField translateTextField;
	private JTextField interpolateStartInterval;
	private JTextField interpolateEndInterval;
	
	private JButton btnUndo;
	private JButton btnRedo;

	public Interface()
	{
		initComponents();
	}

	private void initComponents()
	{

		jScrollPane2 = new javax.swing.JScrollPane();
		jTable2 = new javax.swing.JTable();
		newSubBeforeButton = new javax.swing.JButton();
		newSubAfterButton = new javax.swing.JButton();
		deleteButton = new javax.swing.JButton();
		moveUpButton = new javax.swing.JButton();
		moveDownButton = new javax.swing.JButton();
		openButton = new javax.swing.JButton();
		saveButton = new javax.swing.JButton();
		jPanel1 = new javax.swing.JPanel();
		startLabel = new javax.swing.JLabel();
		endLabel = new javax.swing.JLabel();
		durationLabel = new javax.swing.JLabel();
		startField = new javax.swing.JTextField();
		endField = new javax.swing.JTextField();
		durationField = new javax.swing.JTextField();
		jScrollPane1 = new javax.swing.JScrollPane();
		TextArea = new javax.swing.JTextPane();
		editButton = new javax.swing.JButton();
		textLabel = new javax.swing.JLabel();
		timeRadioButton = new javax.swing.JRadioButton();
		framesRadioButton = new javax.swing.JRadioButton();
		switchLabel = new javax.swing.JLabel();
		FPSLabel = new javax.swing.JLabel();
		FPSComboBox = new javax.swing.JComboBox<String>();
		saveAsButton = new javax.swing.JButton();
		btnTranslate = new JButton();
		btnInterpolate = new JButton();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		setTitle("Subtitle Editor");

		TextArea.setContentType("text/html");

		TextArea.addMouseListener(new MouseListener()
		{

			@Override
			public void mouseClicked(MouseEvent e)
			{
				if (TextArea.getContentType().equals("text/html"))
					setTextAreaMode(true);
			}

			@Override
			public void mouseEntered(MouseEvent e)
			{

			}

			@Override
			public void mouseExited(MouseEvent e)
			{

			}

			@Override
			public void mousePressed(MouseEvent e)
			{

			}

			@Override
			public void mouseReleased(MouseEvent e)
			{

			}
		});

		FPSComboBox
				.setModel(new javax.swing.DefaultComboBoxModel<String>(new String[] { "15", "20", "23,976", "23,978", "24", "25", "29,97", "30" }));
		FPSComboBox.setSelectedIndex(2);
		FPSComboBox.addActionListener(new java.awt.event.ActionListener()
		{
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				setFPS();
			}
		});

		final SubsTableModel model = new SubsTableModel(in);
		jTable2.setModel(model);
		jTable2.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);

		jTable2.getColumnModel().getColumn(0).setPreferredWidth(50);
		jTable2.getColumnModel().getColumn(1).setPreferredWidth(100);
		jTable2.getColumnModel().getColumn(2).setPreferredWidth(100);
		jTable2.getColumnModel().getColumn(3).setPreferredWidth(530);
		jTable2.addMouseListener(new java.awt.event.MouseAdapter()
		{
			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt)
			{
				onRowSelected();
			}
		});
		jScrollPane2.setViewportView(jTable2);

		newSubBeforeButton.setText("Add New Before");
		newSubBeforeButton.addActionListener(this);

		newSubAfterButton.setText("Add New After");
		newSubAfterButton.addActionListener(this);

		deleteButton.setText("Delete");
		deleteButton.addActionListener(this);

		moveUpButton.setText("Move Up");
		moveUpButton.addActionListener(this);

		moveDownButton.setText("Move Down");
		moveDownButton.addActionListener(this);

		openButton.setText("Open");
		openButton.addActionListener(this);

		saveButton.setText("Save");
		saveButton.addActionListener(this);

		jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

		startLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		startLabel.setText("Start:");

		endLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		endLabel.setText("End:");

		durationLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		durationLabel.setText("Duration:");

		startField.setText("");

		endField.setText("");

		durationField.setText("");
		durationField.addMouseListener(new java.awt.event.MouseAdapter()
		{
			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt)
			{
				endField.setText("");
			}
		});

		jScrollPane1.setViewportView(TextArea);

		editButton.setText("Edit");
		editButton.addActionListener(this);

		textLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
		textLabel.setText("Text:");

		javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
		jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING).addGroup(
				jPanel1Layout
						.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								jPanel1Layout.createParallelGroup(Alignment.TRAILING)
										.addComponent(startLabel, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
										.addComponent(endLabel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
										.addComponent(durationLabel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE))
						.addGap(18)
						.addGroup(
								jPanel1Layout
										.createParallelGroup(Alignment.LEADING)
										.addGroup(
												jPanel1Layout.createSequentialGroup().addGap(291)
														.addComponent(editButton, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE))
										.addGroup(
												jPanel1Layout
														.createSequentialGroup()
														.addGroup(
																jPanel1Layout
																		.createParallelGroup(Alignment.TRAILING, false)
																		.addComponent(durationField, Alignment.LEADING)
																		.addComponent(endField, Alignment.LEADING)
																		.addComponent(startField, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 80,
																				GroupLayout.PREFERRED_SIZE))
														.addGap(18)
														.addGroup(
																jPanel1Layout
																		.createParallelGroup(Alignment.LEADING)
																		.addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 464,
																				GroupLayout.PREFERRED_SIZE)
																		.addComponent(textLabel, GroupLayout.PREFERRED_SIZE, 48,
																				GroupLayout.PREFERRED_SIZE)))).addContainerGap(19, Short.MAX_VALUE)));
		jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING).addGroup(
				jPanel1Layout
						.createSequentialGroup()
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(textLabel)
						.addGap(8)
						.addGroup(
								jPanel1Layout
										.createParallelGroup(Alignment.LEADING)
										.addGroup(
												jPanel1Layout
														.createSequentialGroup()
														.addGroup(
																jPanel1Layout
																		.createParallelGroup(Alignment.BASELINE)
																		.addComponent(startLabel)
																		.addComponent(startField, GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
														.addPreferredGap(ComponentPlacement.UNRELATED)
														.addGroup(
																jPanel1Layout
																		.createParallelGroup(Alignment.BASELINE)
																		.addComponent(endLabel)
																		.addComponent(endField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
																				GroupLayout.PREFERRED_SIZE))
														.addPreferredGap(ComponentPlacement.UNRELATED)
														.addGroup(
																jPanel1Layout
																		.createParallelGroup(Alignment.BASELINE)
																		.addComponent(durationLabel)
																		.addComponent(durationField, GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
										.addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(editButton).addContainerGap()));
		jPanel1.setLayout(jPanel1Layout);

		timeRadioButton.setText("Time");
		timeRadioButton.addActionListener(this);

		framesRadioButton.setText("Frames");
		framesRadioButton.addActionListener(this);

		switchLabel.setText("Switch:");

		FPSLabel.setText("FPS:");

		saveAsButton.setText("Save as");
		saveAsButton.addActionListener(this);

		this.btnUndo = new JButton("Undo");
		this.btnUndo.addActionListener(this);

		this.btnRedo = new JButton("Redo");
		this.btnRedo.addActionListener(this);
		
		CommandController.getInstance().registerCommandActionsHandler(this);

		translateTextField = new JTextField();
		translateTextField.setText("");

		btnTranslate.setText("Translate");
		btnTranslate.addActionListener(this);

		btnInterpolate.setText("Interpolate");
		btnInterpolate.addActionListener(this);

		interpolateStartInterval = new JTextField();
		interpolateStartInterval.setText("");

		interpolateEndInterval = new JTextField();
		interpolateEndInterval.setText("");
		
		this.updateControls();

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		layout.setHorizontalGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(
				layout.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								layout.createParallelGroup(Alignment.LEADING)
										.addGroup(
												layout.createSequentialGroup()
														.addGroup(
																layout.createParallelGroup(Alignment.LEADING)
																		.addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, 801,
																				GroupLayout.PREFERRED_SIZE)
																		.addGroup(
																				layout.createSequentialGroup()
																						.addComponent(openButton, GroupLayout.PREFERRED_SIZE, 72,
																								GroupLayout.PREFERRED_SIZE)
																						.addPreferredGap(ComponentPlacement.RELATED)
																						.addComponent(saveButton, GroupLayout.PREFERRED_SIZE, 69,
																								GroupLayout.PREFERRED_SIZE)
																						.addPreferredGap(ComponentPlacement.RELATED)
																						.addComponent(saveAsButton).addGap(160).addComponent(btnUndo)
																						.addPreferredGap(ComponentPlacement.RELATED)
																						.addComponent(btnRedo)))
														.addPreferredGap(ComponentPlacement.RELATED)
														.addGroup(
																layout.createParallelGroup(Alignment.LEADING)
																		.addGroup(
																				layout.createSequentialGroup()
																						.addGap(8)
																						.addGroup(
																								layout.createParallelGroup(Alignment.TRAILING)
																										.addGroup(
																												layout.createSequentialGroup()
																														.addComponent(FPSLabel)
																														.addGap(31))
																										.addGroup(
																												layout.createSequentialGroup()
																														.addComponent(switchLabel)
																														.addGap(18)))
																						.addGroup(
																								layout.createParallelGroup(Alignment.LEADING)
																										.addComponent(framesRadioButton)
																										.addComponent(timeRadioButton,
																												GroupLayout.DEFAULT_SIZE, 118,
																												Short.MAX_VALUE)
																										.addComponent(FPSComboBox,
																												GroupLayout.PREFERRED_SIZE,
																												GroupLayout.DEFAULT_SIZE,
																												GroupLayout.PREFERRED_SIZE)))
																		.addGroup(
																				layout.createParallelGroup(Alignment.TRAILING, false)
																						.addComponent(newSubAfterButton, Alignment.LEADING,
																								GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
																								Short.MAX_VALUE)
																						.addComponent(newSubBeforeButton, Alignment.LEADING,
																								GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
																								Short.MAX_VALUE))
																		.addGroup(
																				layout.createParallelGroup(Alignment.TRAILING, false)
																						.addComponent(deleteButton, Alignment.LEADING,
																								GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
																								Short.MAX_VALUE)
																						.addComponent(moveUpButton, Alignment.LEADING,
																								GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
																								Short.MAX_VALUE)
																						.addComponent(moveDownButton, Alignment.LEADING,
																								GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
																								Short.MAX_VALUE))
																		))
										.addGroup(
												layout.createSequentialGroup()
														.addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addPreferredGap(ComponentPlacement.RELATED)
														.addGroup(
																layout.createParallelGroup(Alignment.LEADING, false)
																		.addComponent(btnTranslate, GroupLayout.DEFAULT_SIZE,
																				GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
																		.addComponent(btnInterpolate, GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE))
														.addPreferredGap(ComponentPlacement.UNRELATED)
														.addGroup(
																layout.createParallelGroup(Alignment.LEADING)
																		.addGroup(
																				layout.createSequentialGroup()
																						.addComponent(interpolateStartInterval,
																								GroupLayout.PREFERRED_SIZE, 80,
																								GroupLayout.PREFERRED_SIZE)
																						.addPreferredGap(ComponentPlacement.RELATED)
																						.addComponent(interpolateEndInterval,
																								GroupLayout.PREFERRED_SIZE, 80,
																								GroupLayout.PREFERRED_SIZE))
																		.addComponent(translateTextField, GroupLayout.PREFERRED_SIZE, 80,
																				GroupLayout.PREFERRED_SIZE)))).addContainerGap()));
		layout.setVerticalGroup(layout
				.createParallelGroup(Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										layout.createParallelGroup(Alignment.TRAILING)
												.addGroup(
														layout.createSequentialGroup()
																.addGroup(
																		layout.createParallelGroup(Alignment.TRAILING)
																				.addGroup(
																						layout.createSequentialGroup().addComponent(timeRadioButton)
																								.addPreferredGap(ComponentPlacement.RELATED)
																								.addComponent(framesRadioButton).addGap(39))
																				.addGroup(
																						layout.createSequentialGroup().addComponent(switchLabel)
																								.addGap(56)))
																.addGroup(
																		layout.createParallelGroup(Alignment.BASELINE)
																				.addComponent(FPSComboBox, GroupLayout.PREFERRED_SIZE,
																						GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
																				.addComponent(FPSLabel)).addGap(45).addComponent(newSubBeforeButton)
																.addPreferredGap(ComponentPlacement.RELATED).addComponent(newSubAfterButton)
																.addGap(36).addComponent(deleteButton).addGap(34).addComponent(moveUpButton)
																.addPreferredGap(ComponentPlacement.RELATED).addComponent(moveDownButton).addGap(40))
												.addGroup(
														layout.createSequentialGroup()
																.addGroup(
																		layout.createParallelGroup(Alignment.BASELINE).addComponent(openButton)
																				.addComponent(saveButton).addComponent(saveAsButton)
																				.addComponent(btnUndo).addComponent(btnRedo))
																.addGap(9)
																.addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
																		GroupLayout.PREFERRED_SIZE)))
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addGroup(
										layout.createParallelGroup(Alignment.LEADING)
												.addGroup(
														layout.createSequentialGroup()
																.addGroup(
																		layout.createParallelGroup(Alignment.BASELINE)
																				.addComponent(translateTextField, GroupLayout.PREFERRED_SIZE,
																						GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
																				.addComponent(btnTranslate))
																.addGap(35)
																.addGroup(
																		layout.createParallelGroup(Alignment.BASELINE)
																				.addComponent(btnInterpolate)
																				.addComponent(interpolateStartInterval, GroupLayout.PREFERRED_SIZE,
																						GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
																				.addComponent(interpolateEndInterval, GroupLayout.PREFERRED_SIZE,
																						GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
																.addPreferredGap(ComponentPlacement.RELATED, 55, Short.MAX_VALUE)
																)
												.addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
														GroupLayout.PREFERRED_SIZE)).addContainerGap()));
		getContentPane().setLayout(layout);

		pack();
	}

	private void onRowSelected()
	{
		this.updateControls();
	}

	public void open()
	{
		String extension = currentFile.getName().substring(currentFile.getName().lastIndexOf('.') + 1);
		SubtitlesParser parser = null;
		try
		{
			parser = SubtitleParserFactory.getSubtitlesParser(new FileInputStream(currentFile), extension);
			parser.parse();
		}
		catch (WrongFormatException e)
		{
			e.printStackTrace();
		}
		catch (FileNotFoundException e1)
		{
			e1.printStackTrace();
		}
		in = parser == null ? null : parser.getSubtitles();
		in = in == null ? new Subtitles(new ArrayList<SubtitleItem>(), SubtitleFormat.SUBRIP) : in;
		if (in.isInFrames())
		{
			double fps = in.getFrameRatePerSecond();
			if (fps == 15)
				FPSComboBox.setSelectedIndex(0);
			if (fps == 20)
				FPSComboBox.setSelectedIndex(1);
			if (fps == 23.976)
				FPSComboBox.setSelectedIndex(2);
			if (fps == 23.978)
				FPSComboBox.setSelectedIndex(3);
			if (fps == 24)
				FPSComboBox.setSelectedIndex(4);
			if (fps == 25)
				FPSComboBox.setSelectedIndex(5);
			if (fps == 29.97)
				FPSComboBox.setSelectedIndex(6);
			if (fps == 30)
				FPSComboBox.setSelectedIndex(7);
		}
		if (in.getFrameRatePerSecond() == -1)
		{
			setFPS();
		}
		isSaved = true;
		this.updateControls();
	}

	private void save()
	{
		if (isSaved){
			return;
		}
		if (currentFile != null)
		{
			AbstractSubtitlesWriter writer = currentFile.getPath().endsWith(".sub") ? new SubWriter(currentFile, in.getFrameRatePerSecond())
					: new SrtWriter(currentFile);
			WriteDirector direcotr = new WriteDirector(in, writer);
			direcotr.write();
		}
		else
		{
			saveAs();
		}
		isSaved = true;
	}

	private boolean saveAs()
	{
		JFileChooser fileChooser = this.getFileChooser(new String[] {"SubRip (*.srt)", "MicroDVD (*.sub)"} , new String[][] { {".srt"}, {".sub"} });

		int returnVal = fileChooser.showSaveDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION)
		{
			currentFile = fileChooser.getSelectedFile();
			boolean isSRT = currentFile.getPath().endsWith(".srt");
			if (isSRT == in.isInFrames())
			{
				SwitchModeCommand switchMode = new SwitchModeCommand(this, in, in.getFrameRatePerSecond(), !isSRT);
				CommandController controller = CommandController.getInstance();
				controller.executeCommand(switchMode);
			}
			if (fileChooser.getFileFilter().getDescription().equals("MicroDVD (*.sub)") && isSRT)
			{
				currentFile = new File(currentFile.getAbsolutePath() + ".sub");
			}
			if (fileChooser.getFileFilter().getDescription().equals("SubRip (*.srt)") && !isSRT)
			{
				currentFile = new File(currentFile.getAbsolutePath() + ".srt");
			}
			save();
			setTitle("Subtitle Editor: " + currentFile.getName());
		}
		return returnVal == JFileChooser.APPROVE_OPTION;
	}

	private void setFPS()
	{
		float[] framesPerSecondChoices = {15, 20, 23.976f, 23.978f, 24, 25, 29.97f, 30};
		if (FPSComboBox.getSelectedIndex() > 0)
		{
			in.setFrameRatePerSecond(framesPerSecondChoices[FPSComboBox.getSelectedIndex()]);
		}
	}

	private void setTextAreaMode(boolean edit)
	{
		if (getStartSelectionIndex() >= 0)
		{
			TextArea.setContentType(edit ? "text/plain" : "text/html");
			TextArea.setText(SubtitleFormatUtils.getSubtitleHTMLText(in, getStartSelectionIndex(), edit));
		}
	}

	private int getSubtitleOffsetFromString(String offsetString, boolean isFrames) {
		offsetString = offsetString.trim();
		int result;
		if(isFrames)
		{
			result = Integer.parseInt(offsetString);
		}
		else
		{
			boolean negative = offsetString.startsWith("-");
			offsetString = negative ? offsetString.substring(1) : offsetString;
			result = SrtParser.getTime(offsetString);
			result = !negative ? result : -result;
		}
		return result;
	}

	@Override
	public void updateActionsEnabledState(boolean undoEnabled, boolean redoEnabled)
	{
		this.btnUndo.setEnabled(undoEnabled);
		this.btnRedo.setEnabled(redoEnabled);
	}

	@Override
	public void notifyChanged()
	{
		this.isSaved = false;
		this.saveButton.setEnabled(!this.isSaved);
		int start = this.getStartSelectionIndex();
		int end = this.getEndSelectionIndex();
		((SubsTableModel) this.jTable2.getModel()).fireTableDataChanged();
		this.setSelection(start, end);
		this.updateControls();
	}
	
	private void updateControls()
	{
		int selectedIndex = this.getStartSelectionIndex();
		int endIndex = this.getEndSelectionIndex();

		startField.setText(selectedIndex > 0 ?  SubtitleFormatUtils.getStart(in, selectedIndex) : (in.isInFrames() ? "0" : "00:00:00,000"));
		endField.setText(selectedIndex > 0 ? SubtitleFormatUtils.getEnd(in, selectedIndex) : (in.isInFrames() ? "0" : "00:00:00,000"));
		durationField.setText(selectedIndex > 0 ? SubtitleFormatUtils.getDuration(in, selectedIndex) : (in.isInFrames() ? "0" : "00:00:00,000"));
		setTextAreaMode(false);

		interpolateStartInterval.setText(selectedIndex > 0 ? SubtitleFormatUtils.getStart(in, selectedIndex) : (in.isInFrames() ? "0" : "00:00:00,000"));
		interpolateEndInterval.setText(endIndex > 0 ? SubtitleFormatUtils.getStart(in, endIndex) : (in.isInFrames() ? "0" : "00:00:00,000"));

		this.translateTextField.setText((in.isInFrames() ? "0" : "00:00:00,000"));

		timeRadioButton.setSelected(!in.isInFrames());
		framesRadioButton.setSelected(in.isInFrames());

		JComponent[] componentsToEnable = new JComponent[]
		{
		        this.btnInterpolate, this.btnTranslate, this.deleteButton, this.startField, this.endField,
		        this.durationField, this.TextArea, this.editButton, this.interpolateStartInterval, this.interpolateEndInterval, this.translateTextField
		};
		for (JComponent component : componentsToEnable)
		{
			component.setEnabled(selectedIndex >= 0);
		}
		this.moveDownButton.setEnabled(selectedIndex >= 0 && endIndex < in.getItems().size() - 1);
		this.moveUpButton.setEnabled(selectedIndex > 0);
	}

	@Override
    public int getStartSelectionIndex()
    {
		return this.jTable2.getSelectionModel().getMinSelectionIndex();
    }

	@Override
    public int getEndSelectionIndex()
    {
		return this.jTable2.getSelectionModel().getMaxSelectionIndex();
    }

	@Override
	public void setSelection(int start, int end)
	{
		if (start == -1)
		{
			this.jTable2.getSelectionModel().clearSelection();
		}
		else
		{
			this.jTable2.getSelectionModel().setSelectionInterval(start, end);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		Command command = null;
		if (e.getSource().equals(newSubBeforeButton))
		{
			int index = getStartSelectionIndex() != -1 ? getStartSelectionIndex() : 0;
			command = new AddNewCommand(this, in, index);
		}
		else if (e.getSource().equals(newSubAfterButton))
		{
			int index = getEndSelectionIndex();
			index = index != -1 ? index + 1 : in.getItems().size();
			command = new AddNewCommand(this, in, index);
		}
		else if (e.getSource().equals(deleteButton))
		{
			command = new DeleteCommand(this, in);
		}
		else if (e.getSource().equals(moveUpButton) || e.getSource().equals(moveDownButton))
		{
			command = new MoveCommand(this, in, e.getSource().equals(moveUpButton) ? -1 : 1);
		}
		else if (e.getSource().equals(openButton))
		{
			this.openFile();
		}
		else if (e.getSource().equals(saveButton))
		{
			this.save();
		}
		else if (e.getSource().equals(editButton))
		{
			String contentType = TextArea.getContentType();
			String text = contentType.equals("text/html") ? SubtitleFormatUtils.getSubtitleHTMLText(in, getStartSelectionIndex(), false) : TextArea.getText();
			TextArea.setContentType("text/html");

			command = new UpdateCommand(this, in, text, startField.getText(), endField.getText(), durationField.getText());
		}
		else if (e.getSource().equals(timeRadioButton) || e.getSource().equals(framesRadioButton))
		{
			command = new SwitchModeCommand(this, in, in.getFrameRatePerSecond(), !in.isInFrames());
		}
		else if (e.getSource().equals(saveAsButton))
		{
			if (this.saveAs()){
				CommandController.getInstance().clearCommandHistory();
			}
		}
		else if (e.getSource().equals(btnUndo))
		{
			CommandController.getInstance().undoLastCommand();
		}
		else if (e.getSource().equals(btnRedo))
		{
			CommandController.getInstance().redoLastCommand();
		}
		else if (e.getSource().equals(btnTranslate))
		{
			int translateDelta = getSubtitleOffsetFromString(translateTextField.getText(), in.isInFrames());
			command = new TranslateCommand(this, in, translateDelta);
		}
		else if (e.getSource().equals(btnInterpolate))
		{
			command = new InterpolateCommand(this, in, getSubtitleOffsetFromString(interpolateStartInterval.getText(), in.isInFrames()),
			        getSubtitleOffsetFromString(interpolateEndInterval.getText(), in.isInFrames()));
		}

		if (command != null)
		{
			CommandController controller = CommandController.getInstance();
			controller.executeCommand(command);
		}
	}

	private JFileChooser getFileChooser(final String[] descriptions, final String[][] filterExtensions)
	{
		JFileChooser result = new javax.swing.JFileChooser();
		for (int i = 0; i < descriptions.length; i++){
			final String description = descriptions[i];
			final String[] extensions = filterExtensions[i];
			result.addChoosableFileFilter(new javax.swing.filechooser.FileFilter()
			{

				@Override
				public boolean accept(File f)
				{
					if (f.isDirectory())
					{
						return true;
					}
					for (String extension : extensions)
					{
						if (f.getAbsolutePath().endsWith(extension))
						{
							return true;
						}
					}
					return false;
				}

				@Override
				public String getDescription()
				{
					return description;
				}
			});
		}
		return result;
	}

	private void openFile()
	{
		JFileChooser fileChooser = this.getFileChooser(new String[] {"Subtitle Files (*.srt, *.sub)"}, new String[][] {{ ".srt", ".sub" }});
		int returnVal = fileChooser.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION)
		{
			currentFile = fileChooser.getSelectedFile();
			setTitle("Subtitle Editor: " + currentFile.getName());
			open();

			((SubsTableModel) jTable2.getModel()).setSubtitleList(in);
			CommandController.getInstance().clearCommandHistory();
		}
	}
}
