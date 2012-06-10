package com.ii.subtitle.editor;

import java.io.IOException;
import java.util.ArrayList;

import com.ii.subtitle.editor.SubtitleText.Type;

public class SubtitlesWriteDirector implements SubtitleTextVisitor
{

	private ArrayList<SubtitleItem> subtitles;
	private AbstractSubtitlesWriter writer;

	public SubtitlesWriteDirector(ArrayList<SubtitleItem> subtitles, AbstractSubtitlesWriter writer)
	{
		this.subtitles = subtitles;
		this.writer = writer;
	}

	public void write()
	{
		try
		{
			writer.onGenerationStart();
			for (int i = 0; i < subtitles.size(); i++)
			{
				SubtitleItem item = subtitles.get(i);
				writer.onSubtitleStarted(i);
				writer.onSubtitleStartDuration(item.getStart());
				writer.onSubtitleEndDuration(item.getEnd());
				writeSubtitleText(item.getText());
				writer.onSubtitleEnded(i);
			}
			writer.onGenerationEnd();

		}
		catch (IOException e)
		{
			try
			{
				writer.onGenerationEnd();
			}
			catch (Exception e1)
			{
				
			}
			e.printStackTrace();
		}

	}

	private void writeSubtitleText(SubtitleText text) throws IOException
	{
		writer.onSubtitleStartText();
		text.acceptVisitor(this);
	}

	@Override
	public void visitTextLeaf(TextLeaf leaf)
	{
		try
		{
			writer.onTextAdded(leaf.getText());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

	}

	@Override
	public void visitTextGroup(TextGroup group)
	{
		try
		{
			Type type = group.getType();
			switch (type)
			{

			case ROOT:
				visitChildren(group);
				break;
				
			case BOLD:
				writer.onBoldTextBegin();
				visitChildren(group);
				writer.onBoldTextEnd();
				break;

			case ITALICS:
				writer.onItalicsTextBegin();
				visitChildren(group);
				writer.onItalicsTextEnd();
				break;

			case UNDERLINE:
				writer.onUnderlineTextBegin();
				visitChildren(group);
				writer.onUnderlineTextEnd();
				break;

			case TYPEFACE:
				// TODO implementation --> new class for typeface text part
				break;

			case LINE:
				visitChildren(group);
				writer.onTextNewLine();
				break;
				
			default:
				break;
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	private void visitChildren(TextGroup group)
	{
		int childCount = group.getChildern() == null ? 0 : group.getChildern().size();
		for (int i = 0; i < childCount; i++)
		{
			group.getChildern().get(i).acceptVisitor(this);
		}

	}

}
