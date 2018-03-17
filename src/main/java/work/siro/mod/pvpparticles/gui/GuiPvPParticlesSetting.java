package work.siro.mod.pvpparticles.gui;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import work.siro.mod.pvpparticles.PvPParticles;
import work.siro.mod.pvpparticles.classes.AttackEffect;
import work.siro.mod.pvpparticles.classes.KillEffect;
import work.siro.mod.pvpparticles.classes.ServerMode;
import work.siro.mod.pvpparticles.classes.TrailEffect;

public class GuiPvPParticlesSetting extends GuiScreen{
	private GuiButton buttonKillParticle;
	private GuiButton buttonAttackParticle;
	private GuiButton buttonTrailParticle;
	private GuiButton buttonModeToggle;
	private static GuiTextField nickNameField;

	@Override
	public void initGui() {
		buttonKillParticle = new GuiButton(0, this.width / 2 - 75, this.height / 2 - 44, 150, 20, "");
		switch(PvPParticles.killEffect) {
			case KillEffect.NONE:
				buttonKillParticle.displayString = "Kill: §7None";
				break;
			case KillEffect.REDSTONE:
				buttonKillParticle.displayString = "Kill: §cRedstone";
				break;
			case KillEffect.LAPIS:
				buttonKillParticle.displayString = "Kill: §1Lapis";
				break;
			case KillEffect.DIAMOND:
				buttonKillParticle.displayString = "Kill: §bDiamond";
				break;
			case KillEffect.EMERALD:
				buttonKillParticle.displayString = "Kill: §aEmerald";
				break;
			case KillEffect.ICE:
				buttonKillParticle.displayString = "Kill: §bIce";
				break;
			case KillEffect.SLIME:
				buttonKillParticle.displayString = "Kill: §aSlime";
				break;
		}
		buttonAttackParticle = new GuiButton(1, this.width / 2 - 75, this.height / 2 - 22, 150 , 20,"");
		switch(PvPParticles.attackEffect) {
			case AttackEffect.NONE:
				buttonAttackParticle.displayString = "Attack: §7None";
				break;
			case AttackEffect.SHARPNESS:
				buttonAttackParticle.displayString = "Attack: §bSharpness";
				break;
			case AttackEffect.CRITICAL:
				buttonAttackParticle.displayString = "Attack: §6Critical";
				break;
			case AttackEffect.SLIME:
				buttonAttackParticle.displayString = "Attack: §aSlime";
				break;
			case AttackEffect.FLAME:
				buttonAttackParticle.displayString = "Attack: §6Flame";
				break;
			case AttackEffect.PORTAL:
				buttonAttackParticle.displayString = "Attack: §5Portal";
				break;
			case AttackEffect.ENCHANT:
				buttonAttackParticle.displayString = "Attack: §bEnchant";
				break;
		}
		buttonTrailParticle = new GuiButton(2, this.width / 2 - 75, this.height / 2, 150, 20, "");
		switch(PvPParticles.trailEffect) {
			case TrailEffect.NONE:
				buttonTrailParticle.displayString = "Trail: §7None";
				break;
			case TrailEffect.HEART:
				buttonTrailParticle.displayString = "Trail: §dHeart";
				break;
			case TrailEffect.NOTE:
				buttonTrailParticle.displayString = "Trail: §bNote";
				break;
			case TrailEffect.GREENSTAR:
				buttonTrailParticle.displayString = "Trail: §aGreenStar";
				break;
		}
		nickNameField = new GuiTextField(4, this.fontRendererObj, this.width / 2 - 75, this.height / 2 + 44,150, 20);
		nickNameField.setText(PvPParticles.nickName);
		nickNameField.setFocused(true);
		nickNameField.setVisible(false);
		buttonModeToggle = new GuiButton(3, this.width / 2 - 75, this.height / 2 + 22, 150 , 20,"");
		switch(PvPParticles.serverMode) {
			case ServerMode.NORMAL:
				buttonModeToggle.displayString = "Mode: §7Normal";
				break;
			case ServerMode.HYPIXEL:
				buttonModeToggle.displayString = "Mode: §fHypixel";
				break;
			case ServerMode.HYPIXELNICK:
				buttonModeToggle.displayString = "Mode: §fHypixel Nick";
				nickNameField.setVisible(true);
				break;
		}
		addButtons();
	}

	public void addButtons() {
		buttonList.add(buttonKillParticle);
		buttonList.add(buttonAttackParticle);
		buttonList.add(buttonTrailParticle);
		buttonList.add(buttonModeToggle);
	}

	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		if(nickNameField.getVisible()) {
			nickNameField.textboxKeyTyped(typedChar, keyCode);
			PvPParticles.nickName = nickNameField.getText();
		}
		super.keyTyped(typedChar, keyCode);
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawDefaultBackground();
		nickNameField.drawTextBox();
		this.fontRendererObj.drawString("PvP Particle "+PvPParticles.VERSION+" by @SiroQ_", this.width/2-mc.fontRendererObj.getStringWidth("PvP Particle "+PvPParticles.VERSION+" by @SiroQ_")/2, this.height/2-66, 16777215);
		this.fontRendererObj.drawString("Contributors: @SimplyRin_, @Rom_0017", this.width/2-mc.fontRendererObj.getStringWidth("Contributors: @SimplyRin_, @Rom_0017")/2, this.height/2-55, 16777215);
		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	@SuppressWarnings("deprecation")
	public void display(){
	    FMLCommonHandler.instance().bus().register(this);
	    initGui();
	}

	@SuppressWarnings("deprecation")
	@SubscribeEvent
	public void onClientTick(TickEvent.ClientTickEvent event){
		FMLCommonHandler.instance().bus().unregister(this);
		Minecraft.getMinecraft().displayGuiScreen(this);
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}



	@Override
	public void onGuiClosed() {
		saveConfig();
		super.onGuiClosed();
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		if(button.id == 0) {
			switch(PvPParticles.killEffect) {
				case KillEffect.NONE:
					buttonKillParticle.displayString = "Kill: §cRedstone";
					PvPParticles.killEffect = KillEffect.REDSTONE;
					break;
				case KillEffect.REDSTONE:
					buttonKillParticle.displayString = "Kill: §1Lapis";
					PvPParticles.killEffect = KillEffect.LAPIS;
					break;
				case KillEffect.LAPIS:
					buttonKillParticle.displayString = "Kill: §bDiamond";
					PvPParticles.killEffect = KillEffect.DIAMOND;
					break;
				case KillEffect.DIAMOND:
					buttonKillParticle.displayString = "Kill: §aEmerald";
					PvPParticles.killEffect = KillEffect.EMERALD;
					break;
				case KillEffect.EMERALD:
					buttonKillParticle.displayString = "Kill: §bIce";
					PvPParticles.killEffect = KillEffect.ICE;
					break;
				case KillEffect.ICE:
					buttonKillParticle.displayString = "Kill: §aSlime";
					PvPParticles.killEffect = KillEffect.SLIME;
					break;
				case KillEffect.SLIME:
					buttonKillParticle.displayString = "Kill: §7None";
					PvPParticles.killEffect = KillEffect.NONE;
					break;
			}
		}else if(button.id == 1) {
			switch(PvPParticles.attackEffect) {
				case AttackEffect.NONE:
					buttonAttackParticle.displayString = "Attack: §bSharpness";
					PvPParticles.attackEffect = AttackEffect.SHARPNESS;
					break;
				case AttackEffect.SHARPNESS:
					buttonAttackParticle.displayString = "Attack: §6Critical";
					PvPParticles.attackEffect = AttackEffect.CRITICAL;
					break;
				case AttackEffect.CRITICAL:
					buttonAttackParticle.displayString = "Attack: §aSlime";
					PvPParticles.attackEffect = AttackEffect.SLIME;
					break;
				case AttackEffect.SLIME:
					buttonAttackParticle.displayString = "Attack: §6Flame";
					PvPParticles.attackEffect = AttackEffect.FLAME;
					break;
				case AttackEffect.FLAME:
					buttonAttackParticle.displayString = "Attack: §5Portal";
					PvPParticles.attackEffect = AttackEffect.PORTAL;
					break;
				case AttackEffect.PORTAL:
					buttonAttackParticle.displayString = "Attack: §bEnchant";
					PvPParticles.attackEffect = AttackEffect.ENCHANT;
					break;
				case AttackEffect.ENCHANT:
					buttonAttackParticle.displayString = "Attack: §7None";
					PvPParticles.attackEffect = AttackEffect.NONE;
					break;
			}
		}else if(button.id == 2) {
			switch(PvPParticles.trailEffect) {
				case TrailEffect.NONE:
					buttonTrailParticle.displayString = "Trail: §dHeart";
					PvPParticles.trailEffect = TrailEffect.HEART;
					break;
				case TrailEffect.HEART:
					buttonTrailParticle.displayString = "Trail: §bNote";
					PvPParticles.trailEffect = TrailEffect.NOTE;
					break;
				case TrailEffect.NOTE:
					buttonTrailParticle.displayString = "Trail: §aGreenStar";
					PvPParticles.trailEffect = TrailEffect.GREENSTAR;
					break;
				case TrailEffect.GREENSTAR:
					buttonTrailParticle.displayString = "Trail: §7None";
					PvPParticles.trailEffect = TrailEffect.NONE;
					break;
			}
		}else if(button.id == 3) {
			switch(PvPParticles.serverMode) {
				case ServerMode.NORMAL:
					buttonModeToggle.displayString = "Mode: §fHypixel";
					PvPParticles.serverMode = ServerMode.HYPIXEL;
					break;
				case ServerMode.HYPIXEL:
					buttonModeToggle.displayString = "Mode: §fHypixel Nick";
					PvPParticles.serverMode = ServerMode.HYPIXELNICK;
					nickNameField.setVisible(true);
					break;
				case ServerMode.HYPIXELNICK:
					buttonModeToggle.displayString = "Mode: §7Normal";
					PvPParticles.serverMode = ServerMode.NORMAL;
					nickNameField.setVisible(false);
					break;
			}
		}
		saveConfig();
	}

	@Override
	public void updateScreen() {
		nickNameField.updateCursorCounter();
		super.updateScreen();
	}

	private static void saveConfig() {
		try {
			PvPParticles.properties.load(new FileInputStream(PvPParticles.propertiesFile));
			PvPParticles.properties.setProperty("killeffect", String.valueOf(PvPParticles.killEffect));
			PvPParticles.properties.setProperty("attackeffect", String.valueOf(PvPParticles.attackEffect));
			PvPParticles.properties.setProperty("traileffect", String.valueOf(PvPParticles.trailEffect));
			PvPParticles.properties.setProperty("servermode", String.valueOf(PvPParticles.serverMode));
			PvPParticles.properties.setProperty("nickname", PvPParticles.nickName);
			PvPParticles.properties.store(new FileOutputStream(PvPParticles.propertiesFile), "Dont change it!");
		}catch(IOException e) {
			e.printStackTrace();
		}
	}















}
