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
import javax.swing.JFileChooser;
import javax.swing.LayoutStyle.ComponentPlacement;

import com.ii.subtitle.editor.SubtitlesParser.WrongFormatException;

import javax.swing.JTextField;

public class Interface extends javax.swing.JFrame
{

	public static class Memento
	{
		private int currentSubtitle;
		private String startFieldText;
		private String endFieldText;
		private String durationFieldText;
		private String textAreaText;
		private boolean isSaved;
		private boolean isFrames;
		private String textAreaContentType;

		public Memento(int currentSubtitle, String startFieldText, String endFieldText, String durationFieldText, String textAreaText,
				boolean isSaved, boolean isFrames, String textAreaContentType)
		{
			this.currentSubtitle = currentSubtitle;
			this.startFieldText = startFieldText;
			this.endFieldText = endFieldText;
			this.durationFieldText = durationFieldText;
			this.textAreaText = textAreaText;
			this.isSaved = isSaved;
			this.isFrames = isFrames;
			this.textAreaContentType = textAreaContentType;
		}
	}

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
	private javax.swing.JButton exitButton;
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
	private Subtitles in = new Subtitles(new ArrayList<SubtitleItem>());
	File currentFile;
	File quoteFile;
	boolean quoteHasPath = false;
	int currentSubtitle = 0;

	boolean isFrames = false;
	boolean hasPath = false;
	boolean isSaved = false;
	private JTextField translateTextField;
	private JTextField interpolateStartInterval;
	private JTextField interpolateEndInterval;

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
		exitButton = new javax.swing.JButton();
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
		newSubBeforeButton.addActionListener(new java.awt.event.ActionListener()
		{
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				currentSubtitle = jTable2.getSelectedRow();
				if (currentSubtitle == -1)
				{
					currentSubtitle = 0;
				}

				AddNewCommand addNew = new AddNewCommand(Interface.this, in, currentSubtitle, jTable2.getSelectionModel());
				CommandController controller = CommandController.getCommandController();
				controller.executeCommand(addNew);
			}
		});

		newSubAfterButton.setText("Add New After");
		newSubAfterButton.addActionListener(new java.awt.event.ActionListener()
		{
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				currentSubtitle = jTable2.getSelectedRow() + jTable2.getSelectedRowCount() - 1;
				if (currentSubtitle == -2)
				{
					currentSubtitle = jTable2.getRowCount() - 1;
				}

				AddNewCommand addNew = new AddNewCommand(Interface.this, in, currentSubtitle + 1, jTable2.getSelectionModel());
				CommandController controller = CommandController.getCommandController();
				controller.executeCommand(addNew);
			}
		});

		deleteButton.setText("Delete");
		deleteButton.addActionListener(new java.awt.event.ActionListener()
		{
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				currentSubtitle = jTable2.getSelectedRow();
				if (currentSubtitle >= 0)
				{
					int length = jTable2.getSelectedRowCount();

					DeleteCommand delete = new DeleteCommand(Interface.this, in, currentSubtitle, length);
					CommandController controller = CommandController.getCommandController();
					controller.executeCommand(delete);
				}
			}
		});

		moveUpButton.setText("Move Up");
		moveUpButton.addActionListener(new java.awt.event.ActionListener()
		{
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				currentSubtitle = jTable2.getSelectedRow();
				if (currentSubtitle > 0)
				{

					int length = jTable2.getSelectedRowCount();

					MoveCommand moveUp = new MoveCommand(Interface.this, in, currentSubtitle, length, -1, jTable2.getSelectionModel());
					CommandController controller = CommandController.getCommandController();
					controller.executeCommand(moveUp);
				}
			}
		});

		moveDownButton.setText("Move Down");
		moveDownButton.addActionListener(new java.awt.event.ActionListener()
		{
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				currentSubtitle = jTable2.getSelectedRow();
				int length = jTable2.getSelectedRowCount();
				if (currentSubtitle >= 0 && currentSubtitle + length < jTable2.getRowCount())
				{

					MoveCommand moveDown = new MoveCommand(Interface.this, in, currentSubtitle, length, 1, jTable2.getSelectionModel());
					CommandController controller = CommandController.getCommandController();
					controller.executeCommand(moveDown);
				}
			}
		});

		openButton.setText("Open");
		openButton.addActionListener(new java.awt.event.ActionListener()
		{
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{

				javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
				fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter()
				{

					@Override
					public boolean accept(File f)
					{
						return f.isDirectory() || f.getAbsolutePath().endsWith(".srt") || f.getAbsolutePath().endsWith(".sub");
					}

					@Override
					public String getDescription()
					{
						return "Subtitle Files (*.srt, *.sub)";
					}
				});

				int returnVal = fileChooser.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION)
				{
					currentFile = fileChooser.getSelectedFile();
					hasPath = true;
					setTitle("Subtitle Editor: " + currentFile.getName());

					in = new Subtitles(new ArrayList<SubtitleItem>());
					open();

					((SubsTableModel) jTable2.getModel()).setSubtitleList(in);
					CommandController.getCommandController().clearCommandHistory();
				}
			}
		});

		saveButton.setText("Save");
		saveButton.addActionListener(new java.awt.event.ActionListener()
		{
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				if (!isSaved)
				{
					save();
				}
			}
		});

		exitButton.setText("Exit");
		exitButton.addActionListener(new java.awt.event.ActionListener()
		{
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				System.exit(0);
			}
		});

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
		editButton.addActionListener(new java.awt.event.ActionListener()
		{
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				String contentType = TextArea.getContentType();
				if (currentSubtitle <= 0 || currentSubtitle >= in.size())
				{
					return;
				}
				String text = TextArea.getText();
				if (contentType.equals("text/html"))
				{
					text = in.getSubtitleHTMLFormattedText(currentSubtitle, false);
				}
				TextArea.setContentType("text/html");

				UpdateCommand update = new UpdateCommand(Interface.this, in, currentSubtitle, text, startField.getText(), endField.getText(),
						durationField.getText());
				CommandController controller = CommandController.getCommandController();
				controller.executeCommand(update);

			}
		});

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
		timeRadioButton.addActionListener(new java.awt.event.ActionListener()
		{
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				if (isFrames)
				{

					SwitchModeCommand switchToTime = new SwitchModeCommand(Interface.this, in, currentSubtitle, in.getFrameRatePerSecond(), false);
					CommandController controller = CommandController.getCommandController();
					controller.executeCommand(switchToTime);
				}
			}
		});

		framesRadioButton.setText("Frames");
		framesRadioButton.addActionListener(new java.awt.event.ActionListener()
		{
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				if (!isFrames)
				{

					SwitchModeCommand switchToFrames = new SwitchModeCommand(Interface.this, in, currentSubtitle, in.getFrameRatePerSecond(), true);
					CommandController controller = CommandController.getCommandController();
					controller.executeCommand(switchToFrames);
				}
			}
		});

		switchLabel.setText("Switch:");

		FPSLabel.setText("FPS:");

		saveAsButton.setText("Save as");
		saveAsButton.addActionListener(new java.awt.event.ActionListener()
		{
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				saveAs();
				CommandController.getCommandController().clearCommandHistory();
			}
		});

		JButton btnUndo = new JButton("Undo");
		CommandController.getCommandController().registerUndoButton(btnUndo);
		btnUndo.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{

				CommandController controller = CommandController.getCommandController();
				controller.undoLastCommand();
			}
		});

		JButton btnRedo = new JButton("Redo");
		CommandController.getCommandController().registerRedoButton(btnRedo);
		btnRedo.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{

				CommandController controller = CommandController.getCommandController();
				controller.redoLastCommand();
			}
		});

		translateTextField = new JTextField();
		translateTextField.setText("");

		btnTranslate.setText("Translate");
		btnTranslate.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				if (currentSubtitle >= 0 && in.size() > 0)
				{
					int translateDelta = getSubtitleOffsetFromString(translateTextField.getText());
					TranslateCommand translate = new TranslateCommand(Interface.this, in, currentSubtitle, jTable2.getSelectionModel(), translateDelta);
					CommandController controller = CommandController.getCommandController();
					controller.executeCommand(translate);
				}
			}
		});

		btnInterpolate.setText("Interpolate");
		btnInterpolate.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				if (currentSubtitle >= 0 && in.size() > 0)
				{
					InterpolateCommand interpolate = new InterpolateCommand(Interface.this, in, currentSubtitle, jTable2.getSelectionModel(),
							getSubtitleOffsetFromString(interpolateStartInterval.getText()), getSubtitleOffsetFromString(interpolateEndInterval.getText()));
					CommandController controller = CommandController.getCommandController();
					controller.executeCommand(interpolate);
				}
			}
		});

		interpolateStartInterval = new JTextField();
		interpolateStartInterval.setText("");

		interpolateEndInterval = new JTextField();
		interpolateEndInterval.setText("");

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
																		.addComponent(exitButton, GroupLayout.PREFERRED_SIZE, 69,
																				GroupLayout.PREFERRED_SIZE)))
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
																.addComponent(exitButton, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE))
												.addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
														GroupLayout.PREFERRED_SIZE)).addContainerGap()));
		getContentPane().setLayout(layout);

		pack();
	}

	public void manipulateEditPanelValues(String startFieldText, String endFieldText, String durationFieldText, String textAreaText)
	{
		startField.setText(startFieldText);
		endField.setText(endFieldText);
		durationField.setText(durationFieldText);
		setTextAreaMode(false);
	}

	public void manipulateInterpolateValues(String start, String end)
	{
		interpolateStartInterval.setText(start);
		interpolateEndInterval.setText(end);
	}

	public void manipulateRadioButtonsValues(boolean isTimeSelected, boolean isFramesSelected)
	{
		timeRadioButton.setSelected(isTimeSelected);
		framesRadioButton.setSelected(isFramesSelected);
	}

	public void manipulateTranslateValues(String translate)
	{
		translateTextField.setText(translate);
	}

	public void notifyJtableDataChanged()
	{
		((SubsTableModel) this.jTable2.getModel()).fireTableDataChanged();
	}

	private void onRowSelected()
	{
		if (jTable2.getSelectedRow() >= 0)
		{
			currentSubtitle = jTable2.getSelectedRow();
			startField.setText(in.getStart(currentSubtitle));
			endField.setText(in.getEnd(currentSubtitle));
			durationField.setText(in.getDuration(currentSubtitle));
			setTextAreaMode(false);
		}
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
		in = in == null ? new Subtitles(new ArrayList<SubtitleItem>()) : in;
		if (!in.isInFrames())
		{
			timeRadioButton.setSelected(true);
			framesRadioButton.setSelected(false);
			isFrames = false;
		}
		else
		{
			framesRadioButton.setSelected(true);
			timeRadioButton.setSelected(false);
			isFrames = true;
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
		setDefaultValues();
		isSaved = true;
	}

	public void restoreFromMemento(Memento memento)
	{
		currentSubtitle = memento.currentSubtitle;
		startField.setText(memento.startFieldText);
		endField.setText(memento.endFieldText);
		durationField.setText(memento.durationFieldText);
		TextArea.setContentType(memento.textAreaContentType);
		TextArea.setText(memento.textAreaText);
		isSaved = memento.isSaved;
		isFrames = memento.isFrames;
		timeRadioButton.setSelected(!isFrames);
		framesRadioButton.setSelected(isFrames);
	}

	private void save()
	{
		if (hasPath)
		{
			AbstractSubtitlesWriter writer = currentFile.getPath().endsWith(".sub") ? new SubWriter(currentFile, in.getFrameRatePerSecond())
					: new SrtWriter(currentFile);
			SubtitlesWriteDirector direcotr = new SubtitlesWriteDirector(in, writer);
			direcotr.write();
		}
		else
		{
			saveAs();
		}
		isSaved = true;
	}

	private void saveAs()
	{
		javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();

		javax.swing.filechooser.FileFilter filterSRT = new javax.swing.filechooser.FileFilter()
		{

			@Override
			public boolean accept(File f)
			{
				return f.isDirectory() || f.getAbsolutePath().endsWith(".srt");
			}

			@Override
			public String getDescription()
			{
				return "SubRip (*.srt)";
			}
		};

		javax.swing.filechooser.FileFilter filterSub = new javax.swing.filechooser.FileFilter()
		{

			@Override
			public boolean accept(File f)
			{
				return f.isDirectory() || f.getAbsolutePath().endsWith(".sub");
			}

			@Override
			public String getDescription()
			{
				return "MicroDVD (*.sub)";
			}
		};

		fileChooser.addChoosableFileFilter(filterSub);
		fileChooser.addChoosableFileFilter(filterSRT);

		int returnVal = fileChooser.showSaveDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION)
		{
			currentFile = fileChooser.getSelectedFile();
			hasPath = true;
			boolean isSRT = currentFile.getPath().endsWith(".srt");
			if (isSRT == in.isInFrames())
			{
				SwitchModeCommand switchMode = new SwitchModeCommand(Interface.this, in, currentSubtitle, in.getFrameRatePerSecond(), !isSRT);
				CommandController controller = CommandController.getCommandController();
				controller.executeCommand(switchMode);
			}
			if (fileChooser.getFileFilter() == filterSub && isSRT)
			{
				currentFile = new File(currentFile.getAbsolutePath() + ".sub");
			}
			if (fileChooser.getFileFilter() == filterSRT && !isSRT)
			{
				currentFile = new File(currentFile.getAbsolutePath() + ".srt");
			}
			save();
			setTitle("Subtitle Editor: " + currentFile.getName());
		}
	}

	public Memento saveToMemento()
	{
		return new Memento(currentSubtitle, startField.getText(), endField.getText(), durationField.getText(), TextArea.getText(), isSaved, isFrames,
				TextArea.getContentType());
	}

	private void setDefaultValues()
	{
		if (in.size() > 0)
		{
			manipulateInterpolateValues(in.getStart(0), in.getStart(in.size() - 1));
		}
		else
		{
			manipulateInterpolateValues(in.isInFrames() ? "0" : "00:00:00,000", in.isInFrames() ? "0" : "00:00:00,000");
		}
		manipulateTranslateValues(in.isInFrames() ? "0" : "00:00:00,000");
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
		TextArea.setContentType(edit ? "text/plain" : "text/html");
		if (currentSubtitle >= 0 && currentSubtitle < in.size())
		{
			TextArea.setText(in.getSubtitleHTMLFormattedText(currentSubtitle, edit));
		}
	}

	private int getSubtitleOffsetFromString(String offsetString) {
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
}
