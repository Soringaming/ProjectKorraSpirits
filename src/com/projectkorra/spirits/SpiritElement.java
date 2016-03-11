package com.projectkorra.spirits;

import com.projectkorra.projectkorra.Element;
import com.projectkorra.projectkorra.Element.SubElement;

public class SpiritElement {

	public static final Element SPIRIT = new Element("Spirit", null, ProjectKorraSpirits.plugin);
	public static final SubElement DARK = new SubElement("Dark", SpiritElement.SPIRIT, null, ProjectKorraSpirits.plugin);
	public static final SubElement LIGHT = new SubElement("Light", SpiritElement.SPIRIT, null, ProjectKorraSpirits.plugin);
}
