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
import java.io.File;
import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.LayoutStyle.ComponentPlacement;

/**
 * 
 * @author Ivo
 */
public class Interface extends javax.swing.JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6987211339001684349L;

	/** Creates new form Interface */
	public Interface() {

		initComponents();
	}

	@Override
	public void setTitle(String title) {
		super.setTitle(title);
	}

	
	private void initComponents() {

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
		boldCheckBox = new javax.swing.JCheckBox();
		italicsCheckBox = new javax.swing.JCheckBox();
		underlineCheckBox = new javax.swing.JCheckBox();
		startField = new javax.swing.JTextField();
		endField = new javax.swing.JTextField();
		durationField = new javax.swing.JTextField();
		jScrollPane1 = new javax.swing.JScrollPane();
		TextArea = new javax.swing.JTextArea();
		editButton = new javax.swing.JButton();
		textLabel = new javax.swing.JLabel();
		timeRadioButton = new javax.swing.JRadioButton();
		framesRadioButton = new javax.swing.JRadioButton();
		switchLabel = new javax.swing.JLabel();
		FPSLabel = new javax.swing.JLabel();
		FPSComboBox = new javax.swing.JComboBox<String>();
		saveAsButton = new javax.swing.JButton();
		saveQuoteButton = new javax.swing.JButton();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		setTitle("Subtitle Editor");

		FPSComboBox.setModel(new javax.swing.DefaultComboBoxModel<String>(
				new String[] { "15", "20", "23,976", "23,978", "24", "25",
						"29,97", "30" }));
		FPSComboBox.setSelectedIndex(2);
		FPSComboBox.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				switch (FPSComboBox.getSelectedIndex()) {
				case 0:
					in.framesPerSecond = 15;
					break;
				case 1:
					in.framesPerSecond = 20;
					break;
				case 2:
					in.framesPerSecond = 23.976;
					break;
				case 3:
					in.framesPerSecond = 23.978;
					break;
				case 4:
					in.framesPerSecond = 24;
					break;
				case 5:
					in.framesPerSecond = 25;
					break;
				case 6:
					in.framesPerSecond = 29.97;
					break;
				case 7:
					in.framesPerSecond = 30;
					break;
				}
			}
		});

		final SubsTableModel model = new SubsTableModel(new SubtitleList(new ArrayList<Subtitle>()));
		jTable2.setModel(model);
		jTable2.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);

		jTable2.getColumnModel().getColumn(0).setPreferredWidth(50);
		jTable2.getColumnModel().getColumn(1).setPreferredWidth(100);
		jTable2.getColumnModel().getColumn(2).setPreferredWidth(100);
		jTable2.getColumnModel().getColumn(3).setPreferredWidth(530);
		jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				jTable2MouseClicked(evt);
			}
		});
		jScrollPane2.setViewportView(jTable2);

		newSubBeforeButton.setText("Add New Before");
		newSubBeforeButton
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						currentSubtitle = jTable2.getSelectedRow();
						if (currentSubtitle == -1) {
							currentSubtitle = 0;
						}
						
						AddNewCommand addNew = new AddNewCommand(Interface.this, (SubsTableModel)jTable2.getModel(), 
							currentSubtitle, jTable2.getSelectionModel());
						CommandController controller = CommandController.getCommandController();
						controller.executeCommand(addNew);
					}
				});

		newSubAfterButton.setText("Add New After");
		newSubAfterButton
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						currentSubtitle = jTable2.getSelectedRow()
								+ jTable2.getSelectedRowCount() - 1;
						if (currentSubtitle == -2) {
							currentSubtitle = jTable2.getRowCount() - 1;
						}
						
						AddNewCommand addNew = new AddNewCommand(Interface.this, (SubsTableModel)jTable2.getModel(), 
							currentSubtitle + 1, jTable2.getSelectionModel());
						CommandController controller = CommandController.getCommandController();
						controller.executeCommand(addNew);
					}
				});

		deleteButton.setText("Delete");
		deleteButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				currentSubtitle = jTable2.getSelectedRow();
				if (currentSubtitle >= 0) {
					int length = jTable2.getSelectedRowCount();
					
					DeleteCommand delete = new DeleteCommand(Interface.this, (SubsTableModel)jTable2.getModel(), 
							currentSubtitle, length);
					CommandController controller = CommandController.getCommandController();
					controller.executeCommand(delete);
				}
			}
		});

		moveUpButton.setText("Move Up");
		moveUpButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				currentSubtitle = jTable2.getSelectedRow();
				if (currentSubtitle > 0) {

					int length = jTable2.getSelectedRowCount();
					
					MoveUpCommand moveUp = new MoveUpCommand(Interface.this, (SubsTableModel)jTable2.getModel(), 
							currentSubtitle, length, jTable2.getSelectionModel());
					CommandController controller = CommandController.getCommandController();
					controller.executeCommand(moveUp);
				}
			}
		});

		moveDownButton.setText("Move Down");
		moveDownButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				currentSubtitle = jTable2.getSelectedRow();
				int length = jTable2.getSelectedRowCount();
				if (currentSubtitle >= 0
						&& currentSubtitle + length < jTable2.getRowCount()) {
					
					MoveDownCommand moveDown = new MoveDownCommand(Interface.this, (SubsTableModel)jTable2.getModel(), 
							currentSubtitle, length, jTable2.getSelectionModel());
					CommandController controller = CommandController.getCommandController();
					controller.executeCommand(moveDown);
				}
			}
		});

		openButton.setText("Open");
		openButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {

				javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
				fileChooser
						.setFileFilter(new javax.swing.filechooser.FileFilter() {

							@Override
							public boolean accept(File f) {
								return f.isDirectory()
										|| f.getAbsolutePath().endsWith(".srt")
										|| f.getAbsolutePath().endsWith(".sub");
							}

							@Override
							public String getDescription() {
								return "Subtitle Files (*.srt, *.sub)";
							}
						});

				int returnVal = fileChooser.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					currentFile = fileChooser.getSelectedFile();
					hasPath = true;
					setTitle("Subtitle Editor: " + currentFile.getName());
					
					in.subList.clear();
					fillList();
					
					((SubsTableModel)jTable2.getModel()).setSubtitleList(new SubtitleList(in.subList));
					CommandController.getCommandController().clearCommandHistory();
//					if (currentFile.getPath().endsWith(".srt")) {
//						for (Subtitle s : in.subList) {
//							model.addRow(new Object[] { s.subtitleNumber,
//									s.startTimeToString(), s.endTimeToString(),
//									s.content });
//						}
//					}
//					if (currentFile.getPath().endsWith(".sub")) {
//						for (Subtitle s : in.subList) {
//							model.addRow(new Object[] { s.subtitleNumber,
//									s.startFrame, s.endFrame, s.content });
//						}
//					}
				}
			}
		});

		saveButton.setText("Save");
		saveButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				if (in.subList.size() > 0) {
					if (!isSaved) {
						if (hasPath) {
							if (currentFile.getPath().endsWith(".sub")) {
								out.writeSUB(in.subList, currentFile.getPath(),
										in.framesPerSecond);
							} else {
								out.writeSRT(in.subList, currentFile.getPath());
							}
						} else {
							saveAs();
						}
					}
				}
			}
		});

		exitButton.setText("Exit");
		exitButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
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

		boldCheckBox.setText("Bold");

		italicsCheckBox.setText("Italics");

		underlineCheckBox.setText("Underline");

		startField.setText("");

		endField.setText("");

		durationField.setText("");
		durationField.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				endField.setText("");
			}
		});

		TextArea.setColumns(20);
		TextArea.setRows(5);
		jScrollPane1.setViewportView(TextArea);

		editButton.setText("Edit");
		editButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				currentSubtitle = jTable2.getSelectedRow();
				int length = jTable2.getSelectedRowCount();
				if (currentSubtitle >= 0 && length == 1) {
					isSaved = false;
					if (!isFrames) {
						if (startField.getText().matches(
								"\\d\\d:\\d\\d:\\d\\d,\\d\\d\\d")
								&& endField.getText().matches(
										"\\d\\d:\\d\\d:\\d\\d,\\d\\d\\d")) {
							in.subList.get(currentSubtitle).calculateStartTime(
									startField.getText());
							jTable2.getModel().setValueAt(
									in.subList.get(currentSubtitle)
											.startTimeToString(),
									currentSubtitle, 1);
							in.subList.get(currentSubtitle).calculateEndTime(
									endField.getText());
							jTable2.getModel().setValueAt(
									in.subList.get(currentSubtitle)
											.endTimeToString(),
									currentSubtitle, 2);

							startField.setText(in.subList.get(currentSubtitle)
									.startTimeToString());
							endField.setText(in.subList.get(currentSubtitle)
									.endTimeToString());
							durationField.setText(in.subList.get(
									currentSubtitle).durationTimeToString());
						}
						if (startField.getText().matches(
								"\\d\\d:\\d\\d:\\d\\d,\\d\\d\\d")
								&& !endField.getText().matches(
										"\\d\\d:\\d\\d:\\d\\d,\\d\\d\\d")
								&& durationField.getText().matches(
										"\\d\\d:\\d\\d:\\d\\d,\\d\\d\\d")) {
							in.subList.get(currentSubtitle).calculateStartTime(
									startField.getText());
							jTable2.getModel().setValueAt(
									in.subList.get(currentSubtitle)
											.startTimeToString(),
									currentSubtitle, 1);
							in.subList.get(currentSubtitle)
									.calculateEndTimeUsingDuration(
											durationField.getText());
							jTable2.getModel().setValueAt(
									in.subList.get(currentSubtitle)
											.endTimeToString(),
									currentSubtitle, 2);

							startField.setText(in.subList.get(currentSubtitle)
									.startTimeToString());
							endField.setText(in.subList.get(currentSubtitle)
									.endTimeToString());
							durationField.setText(in.subList.get(
									currentSubtitle).durationTimeToString());
						}
						if (hasFrames) {
							in.subList.get(currentSubtitle).timeToFrames(
									in.framesPerSecond);
						}
					}
					if (isFrames) {
						if (startField.getText().matches("\\d+")
								&& endField.getText().matches("\\d+")) {
							in.subList.get(currentSubtitle).setStartFrame(
									startField.getText());
							jTable2.getModel().setValueAt(
									in.subList.get(currentSubtitle).startFrame,
									currentSubtitle, 1);
							in.subList.get(currentSubtitle).setEndFrame(
									endField.getText());
							jTable2.getModel().setValueAt(
									in.subList.get(currentSubtitle).endFrame,
									currentSubtitle, 2);

							startField.setText(in.subList.get(currentSubtitle)
									.startFrameToString());
							endField.setText(in.subList.get(currentSubtitle)
									.endFrameToString());
							durationField.setText(in.subList.get(
									currentSubtitle).durationFramesToString());
						}
						if (startField.getText().matches("\\d+")
								&& !endField.getText().matches("\\d+")
								&& durationField.getText().matches("\\d+")) {
							in.subList.get(currentSubtitle).setStartFrame(
									startField.getText());
							jTable2.getModel().setValueAt(
									in.subList.get(currentSubtitle).startFrame,
									currentSubtitle, 1);
							in.subList.get(currentSubtitle)
									.calculateEndFrameUsingDuraton(
											durationField.getText());
							jTable2.getModel().setValueAt(
									in.subList.get(currentSubtitle).endFrame,
									currentSubtitle, 2);

							startField.setText(in.subList.get(currentSubtitle)
									.startFrameToString());
							endField.setText(in.subList.get(currentSubtitle)
									.endFrameToString());
							durationField.setText(in.subList.get(
									currentSubtitle).durationFramesToString());
						}
						if (hasTimes) {
							in.subList.get(currentSubtitle).framesToTime(
									in.framesPerSecond);
						}
					}
					in.subList.get(currentSubtitle).setContent(
							TextArea.getText().replace("\n", "|"));
					jTable2.getModel().setValueAt(
							in.subList.get(currentSubtitle).content,
							currentSubtitle, 3);
					in.subList.get(currentSubtitle).setBold(
							boldCheckBox.isSelected());
					in.subList.get(currentSubtitle).setItalics(
							italicsCheckBox.isSelected());
					in.subList.get(currentSubtitle).setUnderline(
							underlineCheckBox.isSelected());
				}
				if (currentSubtitle >= 0 && length > 1) {
					isSaved = false;
					for (int i = currentSubtitle; i < currentSubtitle + length; i++) {
						in.subList.get(i).setBold(boldCheckBox.isSelected());
						in.subList.get(i).setItalics(
								italicsCheckBox.isSelected());
						in.subList.get(i).setUnderline(
								underlineCheckBox.isSelected());
					}
					startField.setText("");
					endField.setText("");
					durationField.setText("");
					TextArea.setText("");
					boldCheckBox.setSelected(in.subList.get(currentSubtitle).bold);
					italicsCheckBox.setSelected(in.subList.get(currentSubtitle).italics);
					underlineCheckBox.setSelected(in.subList
							.get(currentSubtitle).underline);
				}
			}
		});

		textLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
		textLabel.setText("Text:");

		javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(
				jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout
				.setHorizontalGroup(jPanel1Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel1Layout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jPanel1Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.TRAILING)
														.addComponent(
																startLabel,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																48,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																endLabel,
																javax.swing.GroupLayout.Alignment.LEADING,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																60,
																Short.MAX_VALUE)
														.addComponent(
																durationLabel,
																javax.swing.GroupLayout.Alignment.LEADING,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																60,
																Short.MAX_VALUE)
														.addComponent(
																boldCheckBox))
										.addGap(18, 18, 18)
										.addGroup(
												jPanel1Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																jPanel1Layout
																		.createSequentialGroup()
																		.addComponent(
																				italicsCheckBox)
																		.addGap(18,
																				18,
																				18)
																		.addComponent(
																				underlineCheckBox)
																		.addGap(149,
																				149,
																				149)
																		.addComponent(
																				editButton,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				91,
																				javax.swing.GroupLayout.PREFERRED_SIZE))
														.addGroup(
																jPanel1Layout
																		.createSequentialGroup()
																		.addGroup(
																				jPanel1Layout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.TRAILING,
																								false)
																						.addComponent(
																								durationField,
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addComponent(
																								endField,
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addComponent(
																								startField,
																								javax.swing.GroupLayout.Alignment.LEADING,
																								javax.swing.GroupLayout.PREFERRED_SIZE,
																								80,
																								javax.swing.GroupLayout.PREFERRED_SIZE))
																		.addGap(18,
																				18,
																				18)
																		.addGroup(
																				jPanel1Layout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addComponent(
																								jScrollPane1,
																								javax.swing.GroupLayout.PREFERRED_SIZE,
																								464,
																								javax.swing.GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								textLabel,
																								javax.swing.GroupLayout.PREFERRED_SIZE,
																								48,
																								javax.swing.GroupLayout.PREFERRED_SIZE))))
										.addContainerGap(19, Short.MAX_VALUE)));
		jPanel1Layout
				.setVerticalGroup(jPanel1Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel1Layout
										.createSequentialGroup()
										.addContainerGap(
												javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addComponent(textLabel)
										.addGap(8, 8, 8)
										.addGroup(
												jPanel1Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																jPanel1Layout
																		.createSequentialGroup()
																		.addGroup(
																				jPanel1Layout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.BASELINE)
																						.addComponent(
																								startLabel)
																						.addComponent(
																								startField,
																								javax.swing.GroupLayout.PREFERRED_SIZE,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								javax.swing.GroupLayout.PREFERRED_SIZE))
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																		.addGroup(
																				jPanel1Layout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.BASELINE)
																						.addComponent(
																								endLabel)
																						.addComponent(
																								endField,
																								javax.swing.GroupLayout.PREFERRED_SIZE,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								javax.swing.GroupLayout.PREFERRED_SIZE))
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																		.addGroup(
																				jPanel1Layout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.BASELINE)
																						.addComponent(
																								durationLabel)
																						.addComponent(
																								durationField,
																								javax.swing.GroupLayout.PREFERRED_SIZE,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								javax.swing.GroupLayout.PREFERRED_SIZE)))
														.addComponent(
																jScrollPane1,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																89,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addGroup(
												jPanel1Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																jPanel1Layout
																		.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.BASELINE)
																		.addComponent(
																				underlineCheckBox)
																		.addComponent(
																				italicsCheckBox)
																		.addComponent(
																				boldCheckBox))
														.addComponent(
																editButton))
										.addContainerGap()));

		timeRadioButton.setText("Time");
		timeRadioButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				if (isFrames) {
					
					SwitchToTimeCommand switchToTime = new SwitchToTimeCommand(Interface.this, (SubsTableModel)jTable2.getModel(), 
							currentSubtitle, in.framesPerSecond);
					CommandController controller = CommandController.getCommandController();
					controller.executeCommand(switchToTime);
				}
			}
		});

		framesRadioButton.setText("Frames");
		framesRadioButton
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						if (!isFrames) {
							
							SwitchToFramesCommand switchToFrames = new SwitchToFramesCommand(Interface.this, (SubsTableModel)jTable2.getModel(), 
									currentSubtitle, in.framesPerSecond);
							CommandController controller = CommandController.getCommandController();
							controller.executeCommand(switchToFrames);
						}
					}
				});

		switchLabel.setText("Switch:");

		FPSLabel.setText("FPS:");

		saveAsButton.setText("Save as");
		saveAsButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				if (in.subList.size() > 0) {
					saveAs();
					CommandController.getCommandController().clearCommandHistory();
				}
			}
		});

		saveQuoteButton.setText("Save Quote");
		saveQuoteButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				if (!quoteHasPath) {
					javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();

					javax.swing.filechooser.FileFilter FilterTXT = new javax.swing.filechooser.FileFilter() {

						@Override
						public boolean accept(File f) {
							return f.isDirectory()
									|| f.getAbsolutePath().endsWith(".txt");
						}

						@Override
						public String getDescription() {
							return "Text file (*.txt)";
						}
					};

					fileChooser.addChoosableFileFilter(FilterTXT);
					int returnVal = fileChooser.showSaveDialog(null);
					if (returnVal == JFileChooser.APPROVE_OPTION) {
						quoteFile = fileChooser.getSelectedFile();
						quoteHasPath = true;
						if (fileChooser.getFileFilter() == FilterTXT) {
							if (!quoteFile.getPath().endsWith(".txt")) {

								quoteFile = new File(quoteFile.getPath()
										+ ".txt");
							}
						}

						int current = jTable2.getSelectedRow();
						int length = jTable2.getSelectedRowCount();
						if (current >= 0) {
							if (!hasTimes) {
								for (int i = current; i < current + length; i++) {
									in.subList.get(i).framesToTime(
											in.framesPerSecond);
									out.writeQuote(in.subList.get(i),
											quoteFile.getPath(),
											currentFile.getName());
								}
							} else {
								for (int i = current; i < current + length; i++) {
									out.writeQuote(in.subList.get(i),
											quoteFile.getPath(),
											currentFile.getName());
								}
							}
						}
					}

				} else {
					int current = jTable2.getSelectedRow();
					int length = jTable2.getSelectedRowCount();
					if (current >= 0) {
						if (!hasTimes) {
							for (int i = current; i < current + length; i++) {
								in.subList.get(i).framesToTime(
										in.framesPerSecond);
								out.writeQuote(in.subList.get(i),
										quoteFile.getPath(),
										currentFile.getName());
							}
						} else {
							for (int i = current; i < current + length; i++) {
								out.writeQuote(in.subList.get(i),
										quoteFile.getPath(),
										currentFile.getName());
							}
						}
					}

				}

			}
		});
		
		JButton btnUndo = new JButton("Undo");
		CommandController.getCommandController().registerUndoButton(btnUndo);
		btnUndo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				CommandController controller = CommandController.getCommandController();
				controller.undoLastCommand();
			}
		});
		
		JButton btnRedo = new JButton("Redo");
		CommandController.getCommandController().registerRedoButton(btnRedo);
		btnRedo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				CommandController controller = CommandController.getCommandController();
				controller.redoLastCommand();
			}
		});

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		layout.setHorizontalGroup(
			layout.createParallelGroup(Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
					.addContainerGap()
					.addGroup(layout.createParallelGroup(Alignment.LEADING)
						.addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, 801, GroupLayout.PREFERRED_SIZE)
						.addGroup(layout.createSequentialGroup()
							.addComponent(openButton, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(saveButton, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(saveAsButton)
							.addGap(76)
							.addComponent(saveQuoteButton)
							.addGap(51)
							.addComponent(btnUndo)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnRedo))
						.addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(layout.createParallelGroup(Alignment.LEADING)
						.addGroup(layout.createSequentialGroup()
							.addGap(8)
							.addGroup(layout.createParallelGroup(Alignment.TRAILING)
								.addGroup(layout.createSequentialGroup()
									.addComponent(FPSLabel)
									.addGap(31))
								.addGroup(layout.createSequentialGroup()
									.addComponent(switchLabel)
									.addGap(18)))
							.addGroup(layout.createParallelGroup(Alignment.LEADING)
								.addComponent(framesRadioButton)
								.addComponent(timeRadioButton, GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
								.addComponent(FPSComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addGroup(layout.createParallelGroup(Alignment.TRAILING, false)
							.addComponent(newSubAfterButton, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(newSubBeforeButton, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addGroup(layout.createParallelGroup(Alignment.TRAILING, false)
							.addComponent(deleteButton, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(moveUpButton, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(exitButton, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
							.addComponent(moveDownButton, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
					.addContainerGap())
		);
		layout.setVerticalGroup(
			layout.createParallelGroup(Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
					.addContainerGap()
					.addGroup(layout.createParallelGroup(Alignment.TRAILING)
						.addGroup(layout.createSequentialGroup()
							.addGroup(layout.createParallelGroup(Alignment.TRAILING)
								.addGroup(layout.createSequentialGroup()
									.addComponent(timeRadioButton)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(framesRadioButton)
									.addGap(39))
								.addGroup(layout.createSequentialGroup()
									.addComponent(switchLabel)
									.addGap(56)))
							.addGroup(layout.createParallelGroup(Alignment.BASELINE)
								.addComponent(FPSComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(FPSLabel))
							.addGap(45)
							.addComponent(newSubBeforeButton)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(newSubAfterButton)
							.addGap(36)
							.addComponent(deleteButton)
							.addGap(34)
							.addComponent(moveUpButton)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(moveDownButton)
							.addGap(40))
						.addGroup(layout.createSequentialGroup()
							.addGroup(layout.createParallelGroup(Alignment.BASELINE)
								.addComponent(openButton)
								.addComponent(saveButton)
								.addComponent(saveAsButton)
								.addComponent(saveQuoteButton)
								.addComponent(btnUndo)
								.addComponent(btnRedo))
							.addGap(9)
							.addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addGroup(layout.createParallelGroup(Alignment.LEADING)
						.addGroup(layout.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED, 107, Short.MAX_VALUE)
							.addComponent(exitButton, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
							.addGap(51))
						.addGroup(layout.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())))
		);
		getContentPane().setLayout(layout);

		pack();
	}// </editor-fold>

	private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {
		if (jTable2.getSelectedRow() >= 0) {
			currentSubtitle = jTable2.getSelectedRow();
			if (!isFrames) {
				startField.setText(in.subList.get(currentSubtitle)
						.startTimeToString());
				endField.setText(in.subList.get(currentSubtitle)
						.endTimeToString());
				durationField.setText(in.subList.get(currentSubtitle)
						.durationTimeToString());
			} else {
				startField.setText(in.subList.get(currentSubtitle)
						.startFrameToString());
				endField.setText(in.subList.get(currentSubtitle)
						.endFrameToString());
				durationField.setText(in.subList.get(currentSubtitle)
						.durationFramesToString());
			}
			TextArea.setText(in.subList.get(currentSubtitle).content.replace(
					"|", "\n"));
			boldCheckBox.setSelected(in.subList.get(currentSubtitle).bold);
			italicsCheckBox
					.setSelected(in.subList.get(currentSubtitle).italics);
			underlineCheckBox
					.setSelected(in.subList.get(currentSubtitle).underline);
		}
	}

	private void saveAs() {
		javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();

		javax.swing.filechooser.FileFilter filterSRT = new javax.swing.filechooser.FileFilter() {

			@Override
			public boolean accept(File f) {
				return f.isDirectory() || f.getAbsolutePath().endsWith(".srt");
			}

			@Override
			public String getDescription() {
				return "SubRip (*.srt)";
			}
		};

		javax.swing.filechooser.FileFilter filterSub = new javax.swing.filechooser.FileFilter() {

			@Override
			public boolean accept(File f) {
				return f.isDirectory() || f.getAbsolutePath().endsWith(".sub");
			}

			@Override
			public String getDescription() {
				return "MicroDVD (*.sub)";
			}
		};

		fileChooser.addChoosableFileFilter(filterSub);
		fileChooser.addChoosableFileFilter(filterSRT);

		int returnVal = fileChooser.showSaveDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			currentFile = fileChooser.getSelectedFile();
			hasPath = true;
			if (fileChooser.getFileFilter() == filterSub) {
				if (!currentFile.getPath().endsWith(".sub")) {
					currentFile = new File(currentFile.getPath() + ".sub");
				}
				if (hasFrames) {
					out.writeSUB(in.subList, currentFile.getPath(),
							in.framesPerSecond);
				} else {
					for (Subtitle s : in.subList) {
						s.timeToFrames(in.framesPerSecond);
					}
					out.writeSUB(in.subList, currentFile.getPath(),
							in.framesPerSecond);
				}

			} else {
				if (!currentFile.getPath().endsWith(".srt")) {
					currentFile = new File(currentFile.getPath() + ".srt");
				}
				if (hasTimes) {
					out.writeSRT(in.subList, currentFile.getPath());
				} else {
					for (Subtitle s : in.subList) {
						s.framesToTime(in.framesPerSecond);
					}
					out.writeSRT(in.subList, currentFile.getPath());
				}
			}
			setTitle("Subtitle Editor: " + currentFile.getName());
		}
	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String args[]) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new Interface().setVisible(true);
			}
		});

	}

	// Variables declaration - do not modify
	private javax.swing.JComboBox<String> FPSComboBox;
	private javax.swing.JLabel FPSLabel;
	private javax.swing.JTextArea TextArea;
	private javax.swing.JCheckBox boldCheckBox;
	private javax.swing.JButton deleteButton;
	private javax.swing.JLabel durationLabel;
	private javax.swing.JButton editButton;
	private javax.swing.JLabel endLabel;
	private javax.swing.JButton exitButton;
	private javax.swing.JRadioButton framesRadioButton;
	private javax.swing.JCheckBox italicsCheckBox;
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
	private javax.swing.JButton saveQuoteButton;
	private javax.swing.JLabel startLabel;
	private javax.swing.JLabel switchLabel;
	private javax.swing.JLabel textLabel;
	private javax.swing.JRadioButton timeRadioButton;
	private javax.swing.JCheckBox underlineCheckBox;
	// End of variables declaration
	public Input in = new Input();
	Output out = new Output();
	File currentFile;
	File quoteFile;
	boolean quoteHasPath = false;
	int currentSubtitle = 0;
	boolean isFrames = false;
	boolean hasFrames = false;
	boolean hasTimes = false;
	boolean hasPath = false;
	boolean isSaved = false;
	
	public void fillList() {
		if (currentFile.getPath().endsWith(".srt")) {
			String text = in.read(currentFile);
			in.inputSRT(text);
			timeRadioButton.setSelected(true);
			framesRadioButton.setSelected(false);
			isFrames = false;
			hasTimes = true;
			hasFrames = false;
		}
		if (currentFile.getPath().endsWith(".sub")) {
			String text = in.read(currentFile);
			in.inputSUB(text);
			framesRadioButton.setSelected(true);
			timeRadioButton.setSelected(false);
			isFrames = true;
			hasFrames = true;
			hasTimes = false;
			if (in.framesPerSecond == 15)
				FPSComboBox.setSelectedIndex(0);
			if (in.framesPerSecond == 20)
				FPSComboBox.setSelectedIndex(1);
			if (in.framesPerSecond == 23.976)
				FPSComboBox.setSelectedIndex(2);
			if (in.framesPerSecond == 23.978)
				FPSComboBox.setSelectedIndex(3);
			if (in.framesPerSecond == 24)
				FPSComboBox.setSelectedIndex(4);
			if (in.framesPerSecond == 25)
				FPSComboBox.setSelectedIndex(5);
			if (in.framesPerSecond == 29.97)
				FPSComboBox.setSelectedIndex(6);
			if (in.framesPerSecond == 30)
				FPSComboBox.setSelectedIndex(7);
		}
		isSaved = true;
	}
	
	public void manipulateEditPanelValues(String startFieldText, String endFieldText, String durationFieldText, 
			String textAreaText, boolean isBoldSelected, boolean isItalicsSelected, boolean isUnderlineSelected)
	{
		startField.setText(startFieldText);
		endField.setText(endFieldText);
		durationField.setText(durationFieldText);
		TextArea.setText(textAreaText);
		boldCheckBox.setSelected(isBoldSelected);
		italicsCheckBox.setSelected(isItalicsSelected);
		underlineCheckBox.setSelected(isUnderlineSelected);
	}
	
	public void manipulateRadioButtonsValues(boolean isTimeSelected, boolean isFramesSelected)
	{
		timeRadioButton.setSelected(isTimeSelected);
		framesRadioButton.setSelected(isFramesSelected);
	}
	
	public Memento saveToMemento()
	{
		return new Memento(currentSubtitle, startField.getText(), endField.getText(), durationField.getText(),
				TextArea.getText(), boldCheckBox.isSelected(), italicsCheckBox.isSelected(), underlineCheckBox.isSelected(), 
				isSaved, isFrames, hasFrames, hasTimes);
	}
	
	public void restoreFromMemento(Memento memento)
	{
		currentSubtitle = memento.currentSubtitle;
		startField.setText(memento.startFieldText);
		endField.setText(memento.endFieldText);
		durationField.setText(memento.durationFieldText);
		TextArea.setText(memento.textAreaText);
		boldCheckBox.setSelected(memento.isBoldSelected);
		italicsCheckBox.setSelected(memento.isItalicsSelected);
		underlineCheckBox.setSelected(memento.isUnderlineSelected);
		isSaved = memento.isSaved;
		isFrames = memento.isFrames;
		hasFrames = memento.hasFrames;
		hasTimes = memento.hasTimes;
		timeRadioButton.setSelected(!isFrames);
		framesRadioButton.setSelected(isFrames);
	}
	
	public static class Memento
	{
		private int currentSubtitle;
		private String startFieldText;
		private String endFieldText;
		private String durationFieldText;
		private String textAreaText;
		private boolean isBoldSelected;
		private boolean isItalicsSelected;
		private boolean isUnderlineSelected;
		private boolean isSaved;
		private boolean isFrames;
		private boolean hasFrames;
		private boolean hasTimes;
		
		public Memento(int currentSubtitle, String startFieldText, String endFieldText, String durationFieldText, String textAreaText, boolean isBoldSelected, boolean isItalicsSelected,
				boolean isUnderlineSelected, boolean isSaved, boolean isFrames, boolean hasFrames, boolean hasTimes)
		{
			this.currentSubtitle = currentSubtitle;
			this.startFieldText = startFieldText;
			this.endFieldText = endFieldText;
			this.durationFieldText = durationFieldText;
			this.textAreaText = textAreaText;
			this.isBoldSelected = isBoldSelected;
			this.isItalicsSelected = isItalicsSelected;
			this.isUnderlineSelected = isUnderlineSelected;
			this.isSaved = isSaved;
			this.isFrames = isFrames;
			this.hasFrames = hasFrames;
			this.hasTimes = hasTimes;
		}
	}
}
