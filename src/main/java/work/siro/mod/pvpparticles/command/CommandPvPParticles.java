package work.siro.mod.pvpparticles.command;

import java.util.Arrays;
import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import work.siro.mod.pvpparticles.gui.GuiPvPParticlesSetting;

public class CommandPvPParticles extends CommandBase{
	@Override
	public int getRequiredPermissionLevel() {
		return 0;
	}

	@Override
	public String getCommandName() {
		return "pvpparticles";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/pvpparticles";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) throws CommandException {
		new GuiPvPParticlesSetting().display();
	}

	@Override
	public List<String> getCommandAliases() {
		return Arrays.asList("pvpp");
	}


}
