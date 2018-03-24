package work.siro.mod.pvpparticles.gui;

import java.awt.Desktop;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import work.siro.mod.pvpparticles.PvPParticles;
import work.siro.mod.pvpparticles.classes.AttackEffect;
import work.siro.mod.pvpparticles.classes.KillEffect;
import work.siro.mod.pvpparticles.classes.ServerMode;
import work.siro.mod.pvpparticles.classes.TrailEffect;

public class GuiPvPParticlesSetting extends GuiScreen {

	private static GuiTextField blockIdField;
	private GuiButton buttonKillParticle;
	private GuiButton buttonAttackParticle;
	private GuiButton buttonTrailParticle;
	private GuiButton buttonModeToggle;
	private static GuiTextField nickNameField;
	private static GuiTextField trailParticleField;

	@Override
	public void initGui() {
		blockIdField = new GuiTextField(5,this.fontRendererObj, this.width / 2 - 75, this.height / 2 - 22, 150, 20);
		if(!(PvPParticles.killBlockID == 0)) {
			blockIdField.setText(String.valueOf(PvPParticles.killBlockID));
		}else {
			blockIdField.setText("§7Block ID (Number)");
		}
		blockIdField.setFocused(false);
		blockIdField.setVisible(false);
		buttonKillParticle = new GuiButton(0, this.width / 2 - 75, this.height / 2 - 44, 150, 20, "");
		switch(PvPParticles.killEffect) {
			case KillEffect.NONE:
				buttonKillParticle.displayString = "Kill: §7None";
				break;
			case KillEffect.BLOCKBREAK:
				buttonKillParticle.displayString = "Kill: §fBlock Break";
				blockIdField.setVisible(true);
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
		trailParticleField = new GuiTextField(6, this.fontRendererObj, this.width / 2 - 75, this.height / 2 + 22 , 150, 20);
		if(!PvPParticles.trailParticle.isEmpty()) {
			trailParticleField.setText(PvPParticles.trailParticle);
		}else {
			trailParticleField.setText("§7Particle Name(See settings)");
		}
		trailParticleField.setFocused(false);
		trailParticleField.setVisible(false);
		buttonTrailParticle = new GuiButton(2, this.width / 2 - 75, this.height / 2, 150, 20, "");
		switch(PvPParticles.trailEffect) {
			case TrailEffect.NONE:
				buttonTrailParticle.displayString = "Trail: §7None";
				break;
			case TrailEffect.PARTICLETRAIL:
				buttonTrailParticle.displayString = "Trail: §fParticle Trail";
				trailParticleField.setVisible(true);
				break;
		}
		nickNameField = new GuiTextField(4, this.fontRendererObj, this.width / 2 - 75, this.height / 2 + 44,150, 20);
		if(!PvPParticles.nickName.isEmpty()) {
			nickNameField.setText(PvPParticles.nickName);
		}else {
			nickNameField.setText("§7Nick Name");
		}
		nickNameField.setFocused(false);
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
			if(nickNameField.isFocused()) {
				nickNameField.textboxKeyTyped(typedChar, keyCode);
				PvPParticles.nickName = nickNameField.getText();
			}
		}
		if(blockIdField.getVisible()) {
			if(blockIdField.isFocused()) {
				try {
					blockIdField.textboxKeyTyped(typedChar, keyCode);
					PvPParticles.killBlockID = Integer.valueOf(blockIdField.getText());
				}catch(Exception e) {
					if(blockIdField.getText() != null && blockIdField.getText().length() > 0){
					    blockIdField.setText(blockIdField.getText().substring(0, blockIdField.getText().length()-1));
					}
				}
			}
		}

		if(trailParticleField.getVisible()) {
			if(trailParticleField.isFocused()) {
				trailParticleField.textboxKeyTyped(typedChar, keyCode);
				PvPParticles.trailParticle = trailParticleField.getText().toUpperCase();
			}
		}
		super.keyTyped(typedChar, keyCode);
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawDefaultBackground();
		nickNameField.drawTextBox();
		blockIdField.drawTextBox();
		trailParticleField.drawTextBox();
		if(blockIdField.getVisible()) {
			buttonAttackParticle.yPosition = this.height / 2;
			buttonTrailParticle.yPosition = this.height / 2 + 22;
			buttonModeToggle.yPosition = this.height / 2 + 44;
			nickNameField.yPosition = this.height / 2 + 66;
		}else {
			buttonAttackParticle.yPosition = this.height / 2 - 22;
			buttonTrailParticle.yPosition = this.height / 2;
			buttonModeToggle.yPosition = this.height /2 + 22;
			nickNameField.yPosition = this.height / 2 + 44;
		}
		if(trailParticleField.getVisible()) {
			if(blockIdField.getVisible()) {
				buttonAttackParticle.yPosition = this.height / 2;
				buttonTrailParticle.yPosition = this.height / 2 + 22;
				trailParticleField.yPosition = this.height / 2 + 44;
				buttonModeToggle.yPosition = this.height / 2 + 66;
				nickNameField.yPosition = this.height / 2 + 88;
			}else {
				buttonAttackParticle.yPosition = this.height / 2 - 22;
				buttonTrailParticle.yPosition = this.height / 2;
				trailParticleField.yPosition = this.height / 2 + 22;
				buttonModeToggle.yPosition = this.height /2 + 44;
				nickNameField.yPosition = this.height / 2 + 66;
			}
		}else {
			if(blockIdField.getVisible()) {
				buttonAttackParticle.yPosition = this.height / 2;
				buttonTrailParticle.yPosition = this.height / 2 + 22;
				buttonModeToggle.yPosition = this.height / 2 + 44;
				nickNameField.yPosition = this.height / 2 + 66;
			}else {
				buttonAttackParticle.yPosition = this.height / 2 - 22;
				buttonTrailParticle.yPosition = this.height / 2;
				buttonModeToggle.yPosition = this.height /2 + 22;
				nickNameField.yPosition = this.height / 2 + 44;
			}
		}
		this.fontRendererObj.drawString("PvP Particle "+PvPParticles.VERSION+" by @SiroQ_", this.width/2-mc.fontRendererObj.getStringWidth("PvP Particle "+PvPParticles.VERSION+" by @SiroQ_")/2, this.height/2-77, 16777215);
		this.fontRendererObj.drawString("Contributors: @SimplyRin_, @Rom_0017", this.width/2-mc.fontRendererObj.getStringWidth("Contributors: @SimplyRin_, @Rom_0017")/2, this.height/2-66, 16777215);
		this.fontRendererObj.drawString("Setting: https://siro.work/mods/pvpparticles/setting/", this.width/2-mc.fontRendererObj.getStringWidth("Setting: https://siro.work/mods/pvpparticles/setting/")/2, this.height/2-55, 16777215);
		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	public void display(){
		MinecraftForge.EVENT_BUS.register(this);
	    initGui();
	}

	@SubscribeEvent
	public void onClientTick(TickEvent.ClientTickEvent event){
		MinecraftForge.EVENT_BUS.unregister(this);
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
					buttonKillParticle.displayString = "Kill: §fBlock Break";
					blockIdField.setVisible(true);
					PvPParticles.killEffect = KillEffect.BLOCKBREAK;
					break;
				case KillEffect.BLOCKBREAK:
					buttonKillParticle.displayString = "Kill: §7None";
					blockIdField.setVisible(false);
					PvPParticles.killEffect = KillEffect.NONE;
					break;
			}
		} else if(button.id == 1) {
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
		} else if(button.id == 2) {
			switch(PvPParticles.trailEffect) {
				case TrailEffect.NONE:
					buttonTrailParticle.displayString = "Trail: §fParticle Trail";
					trailParticleField.setVisible(true);
					PvPParticles.trailEffect = TrailEffect.PARTICLETRAIL;
					break;
				case TrailEffect.PARTICLETRAIL:
					buttonTrailParticle.displayString = "Trail: §7None";
					trailParticleField.setVisible(false);
					PvPParticles.trailEffect = TrailEffect.NONE;
					break;
			}
		} else if(button.id == 3) {
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
		blockIdField.updateCursorCounter();
		trailParticleField.updateCursorCounter();
		super.updateScreen();
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		if(mouseY >= this.height/2-55 && mouseY <= this.height/2-45) {
			if(mouseX >= this.width/2-mc.fontRendererObj.getStringWidth("Setting: https://siro.work/mods/pvpparticles/setting/")/2+mc.fontRendererObj.getStringWidth("Setting: ") && mouseX <= this.width/2-mc.fontRendererObj.getStringWidth("Setting: https://siro.work/mods/pvpparticles/setting/") * 1.5){
				try {
					Desktop.getDesktop().browse(new URI("https://siro.work/mods/pvpparticles/setting/"));
				} catch (URISyntaxException e) {e.printStackTrace();}
			}
			/*
			 * this.fontRendererObj.drawString("PvP Particle "+PvPParticles.VERSION+" by @SiroQ_", this.width/2-mc.fontRendererObj.getStringWidth("PvP Particle "+PvPParticles.VERSION+" by @SiroQ_")/2, this.height/2-77, 16777215);
			 */
		}else if(mouseY >= this.height/2-66 && mouseY <= this.height/2-56) {
			if(mouseX >= this.width/2-mc.fontRendererObj.getStringWidth("Contributors: @SimplyRin_, @Rom_0017")/2+mc.fontRendererObj.getStringWidth("Contributors: ") && mouseX <= this.width/2-mc.fontRendererObj.getStringWidth("Contributors: @SimplyRin_, @Rom_0017")/2 + mc.fontRendererObj.getStringWidth("Contributors: @SimplyRin_")){
				try {
					Desktop.getDesktop().browse(new URI("https://twitter.com/SimplyRin_"));
				} catch (URISyntaxException e) {e.printStackTrace();}
			}else if(mouseX >= this.width/2-mc.fontRendererObj.getStringWidth("Contributors: @SimplyRin_, @Rom_0017")/2+mc.fontRendererObj.getStringWidth("Contributors: @SimplyRin_, ") && mouseX <= this.width/2-mc.fontRendererObj.getStringWidth("Contributors: @SimplyRin_, @Rom_0017")/2 + mc.fontRendererObj.getStringWidth("Contributors: @SimplyRin_, @Rom_0017")) {
				try {
					Desktop.getDesktop().browse(new URI("https://twitter.com/Rom_0017"));
				} catch (URISyntaxException e) {e.printStackTrace();}
			}
		}else if(mouseY >= this.height/2-77 && mouseY <= this.height/2-67) {
			if(mouseX >= this.width/2-mc.fontRendererObj.getStringWidth("PvP Particle "+PvPParticles.VERSION+" by @SiroQ_")/2+mc.fontRendererObj.getStringWidth("PvP Particle "+PvPParticles.VERSION+" by ") && mouseX <= this.width/2-mc.fontRendererObj.getStringWidth("PvP Particle "+PvPParticles.VERSION+" by @SiroQ_")/2 + mc.fontRendererObj.getStringWidth("PvP Particle "+PvPParticles.VERSION+" by @SiroQ_")){
				try {
					Desktop.getDesktop().browse(new URI("https://twitter.com/SiroQ_"));
				} catch (URISyntaxException e) {e.printStackTrace();}
			}
		}
		nickNameField.mouseClicked(mouseX, mouseY, mouseButton);
		blockIdField.mouseClicked(mouseX, mouseY, mouseButton);
		trailParticleField.mouseClicked(mouseX, mouseY, mouseButton);
		if(nickNameField.isFocused()) {
			if(nickNameField.getText().equals("§7Nick Name")) {
				nickNameField.setText("");
			}
		} else {
			if(PvPParticles.nickName.isEmpty()) {
				nickNameField.setText("§7Nick Name");
			}
		}

		if(blockIdField.isFocused()) {
			if(blockIdField.getText().equals("§7Block ID (Number)")) {
				blockIdField.setText("");
			}
		} else {
			if(PvPParticles.nickName.isEmpty()) {
				blockIdField.setText("§7Block ID (Number)");
			}
		}

		if(trailParticleField.isFocused()) {
			if(trailParticleField.getText().equals("§7Particle Name(See settings)")) {
				trailParticleField.setText("");
			}
		}else {
			if(PvPParticles.trailParticle.isEmpty()) {
				trailParticleField.setText("§7Particle Name(See settings)");
			}
		}
		super.mouseClicked(mouseX, mouseY, mouseButton);
	}

	private static void saveConfig() {
		try {
			PvPParticles.properties.load(new FileInputStream(PvPParticles.propertiesFile));
			PvPParticles.properties.setProperty("killeffect", String.valueOf(PvPParticles.killEffect));
			PvPParticles.properties.setProperty("killblock", String.valueOf(blockIdField.getText()));
			PvPParticles.properties.setProperty("attackeffect", String.valueOf(PvPParticles.attackEffect));
			PvPParticles.properties.setProperty("traileffect", String.valueOf(PvPParticles.trailEffect));
			PvPParticles.properties.setProperty("trailparticle", PvPParticles.trailParticle);
			PvPParticles.properties.setProperty("servermode", String.valueOf(PvPParticles.serverMode));
			PvPParticles.properties.setProperty("nickname", PvPParticles.nickName.toUpperCase());
			PvPParticles.properties.store(new FileOutputStream(PvPParticles.propertiesFile), "Dont change it!");
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

}
