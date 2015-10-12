/*
 * This software copyright by various authors including the RPTools.net
 * development team, and licensed under the LGPL Version 3 or, at your option,
 * any later version.
 *
 * Portions of this software were originally covered under the Apache Software
 * License, Version 1.1 or Version 2.0.
 *
 * See the file LICENSE elsewhere in this distribution for license details.
 */

package net.rptools.maptool.client.macro.impl;

import net.rptools.lib.MD5Key;
import net.rptools.maptool.client.AppPreferences;
import net.rptools.maptool.client.MapTool;
import net.rptools.maptool.client.MapToolMacroContext;
import net.rptools.maptool.client.macro.MacroContext;
import net.rptools.maptool.client.macro.MacroDefinition;
import net.rptools.maptool.model.TextMessage;
import net.rptools.maptool.model.GUID;
import net.rptools.maptool.model.Token;

@MacroDefinition(
		name = "emit",
		aliases = { "e" },
		description = "emit.description")
public class EmitMacro extends AbstractMacro {
	public void execute(MacroContext context, String macro, MapToolMacroContext executionContext) {
		macro = processText(macro);
		StringBuilder sb = new StringBuilder();
		sb.append("<table cellpadding=0><tr>");
		if (executionContext == null && !MapTool.getPlayer().isGM()) {
			String identity = MapTool.getFrame().getCommandPanel().getIdentity();
			if (MapTool.getFrame().getCommandPanel().isImpersonating() && AppPreferences.getShowAvatarInChat()) {
				Token token;
				GUID guid = MapTool.getFrame().getCommandPanel().getIdentityGUID();
				if (guid != null)
					token = MapTool.getFrame().getCurrentZoneRenderer().getZone().getToken(guid);
				else
					token = MapTool.getFrame().getCurrentZoneRenderer().getZone().getTokenByName(identity);
				if (token != null) {
					MD5Key imageId = token.getPortraitImage();
					if (imageId == null) {
						imageId = token.getImageAssetId();
					}
					sb.append("<td valign='top' width='40' style=\"padding-right:5px\"><img src=\"asset://").append(imageId).append("-40\" ></td>");
				}
			}
		}
		sb.append("<td valign=top style=\"margin-right: 5px\">");
		sb.append(macro);
		MapTool.addMessage(TextMessage.say(context.getTransformationHistory(), sb.toString()));
	}
}
