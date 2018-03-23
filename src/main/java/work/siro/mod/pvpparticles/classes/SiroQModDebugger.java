package work.siro.mod.pvpparticles.classes;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

public class SiroQModDebugger {

	private int status = 0;
	private static boolean debugging = true;
	private String debug = "§c§l[DEBUG] ";
	private String error = "§c§l[ERROR] ";

	public void error(String line) {
		if(debugging) {
			if(Minecraft.getMinecraft().thePlayer != null) {
				if(status == 0) {
					Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(error+"§f"+line));
					status = 1;
				} else {
					Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(error+"§7"+line));
					status = 0;
				}
			}
		}
	}

	public void debug(String line) {
		if(debugging) {
			if(Minecraft.getMinecraft().thePlayer != null) {
				if(status == 0) {
					Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(debug+"§f"+line));
					status = 1;
				} else {
					Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(debug+"§7"+line));
					status = 0;
				}
			}
		}
	}

}
