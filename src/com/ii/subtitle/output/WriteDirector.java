package com.ii.subtitle.output;

import java.io.IOException;

import com.ii.subtitle.model.SubtitleText;
import com.ii.subtitle.model.SubtitleTextVisitor;
import com.ii.subtitle.model.Subtitles;
import com.ii.subtitle.model.TextGroup;
import com.ii.subtitle.model.TextLeaf;
import com.ii.subtitle.model.SubtitleText.Type;

public class WriteDirector implements SubtitleTextVisitor
{

	protected Subtitles subtitles;
	protected AbstractSubtitlesWriter writer;

	public WriteDirector(Subtitles subtitles, AbstractSubtitlesWriter writer)
	{
		this.subtitles = subtitles;
		this.writer = writer;
	}

	public void write()
	{
		try
		{
			writer.onGenerationStart();
			for (int i = 0; i < subtitles.getItems().size(); i++)
			{
				writeItem(i);
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

	protected void writeItem(int i) throws IOException
    {
	    writer.onSubtitleStarted(i);
	    writer.onSubtitleStartDuration(subtitles.getStart(i));
	    writer.onSubtitleEndDuration(subtitles.getEnd(i));
	    writeSubtitleText(subtitles.getText(i));
	    writer.onSubtitleEnded(i);
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
