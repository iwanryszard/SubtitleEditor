package com.ii.subtitle.model;

public enum SubtitleFormat
{
	MICRODVD("sub", "MicroDVD", true), SUBRIP("srt", "SubRip", false), SUBSTATIONALPHA("ssa", "SubStation Alpha", false);
	
	private String extension;
	private String description;
	private boolean isInFrames;
	
	private SubtitleFormat(String extension, String description, boolean isInFrames)
    {
	    this.extension = extension;
	    this.description = description;
	    this.isInFrames = isInFrames;
    }
	public String getExtension()
	{
		return extension;
	}
	public String getDescription()
	{
		return description;
	}
	public boolean isInFrames()
	{
		return isInFrames;
	}
	
	
}
