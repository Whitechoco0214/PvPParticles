package work.siro.mod.pvpparticles;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import work.siro.mod.pvpparticles.classes.AttackEffect;
import work.siro.mod.pvpparticles.classes.KillEffect;
import work.siro.mod.pvpparticles.classes.Location;
import work.siro.mod.pvpparticles.classes.ServerMode;
import work.siro.mod.pvpparticles.classes.SiroQModDebugger;
import work.siro.mod.pvpparticles.classes.SiroQModUtils;
import work.siro.mod.pvpparticles.classes.TrailEffect;
import work.siro.mod.pvpparticles.command.CommandPvPParticles;

@Mod(modid = PvPParticles.MODID, version = PvPParticles.VERSION)
public class PvPParticles
{
	public static Minecraft mc = Minecraft.getMinecraft();
    public static final String MODID = "pvpparticles";
    public static final String VERSION = "1.3";
    public static int attackEffect;
    public static int killEffect;
    public static int killBlockID;
    public static int trailEffect;
    public static int serverMode;
    public static boolean shotBow;
    public static boolean sentUpdateInfo = false;
    public static String nickName;
    public static SiroQModDebugger debugger = new SiroQModDebugger();
    public static Properties properties= new Properties();
    public static File propertiesFile = new File("./PvPParticles.properties");
    public static List<Entity> projectileEntities = new ArrayList<Entity>();
    public static List<String> skyWarsKillMessages = Arrays.asList("was killed by PLAYER.","was thrown into the void by PLAYER.","was Bomberman'd by PLAYER.","was shot by PLAYER.","was toasted by PLAYER.","was struck down by PLAYER.","was turned to dust by PLAYER.","was turned to ash by PLAYER.","was melted by PLAYER.","was filled full of lead by PLAYER.","met their end by PLAYER.","lost a drink contest with PLAYER.","lost the draw to PLAYER.","was given the cold shoulder by PLAYER.","was out of the league of PLAYER.","'s heart was broken by PLAYER.","was struck with Cupid's arrow by PLAYER.","be sent to Davy Jones' locker by PLAYER.","be cannonballed to death by PLAYER.","be voodooed by PLAYER.","be shot and killed by PLAYER.","was turned into space dust by PLAYER.","was sent into orbit by PLAYER.","was hit by an asteroid from PLAYER.","was deleted by PLAYER.","was ALT+F4'd by PLAYER.","was crashed by PLAYER.","was rm -fr by PLAYER.","was glazed in BBQ sauce by PLAYER.","slipped in BBQ sauce off the edge spilled by PLAYER.","was not spicy enough for PLAYER.","was thrown chili powder at by PLAYER.","was exterminated by PLAYER.","was scared off an edge by PLAYER.","was squashed by PLAYER.","was tranquilized by PLAYER.","was mushed by PLAYER.","was peeled by PLAYER.","slipped on PLAYER's banana peel off a cliff.","got banana pistol'd by PLAYER.","was crusaded by the knight PLAYER.","was jousted by PLAYER.","was catapulted by PLAYER.","was shot to the knee by PLAYER.","was bit by PLAYER.","got WOOF'D by PLAYER into the void.","was growled off an edge by PLAYER.","was thrown a frisbee by PLAYER.","got rekt by PLAYER.","took the L to PLAYER.","got dabbed on by PLAYER.","got bamboozled by PLAYER.");
    public static List<String> bedWarsKillMessages = Arrays.asList("was struck down by PLAYER.","was turned to dust by PLAYER.","was turned to ash by PLAYER.","was melted by PLAYER.","was filled full of lead by PLAYER.","met their end by PLAYER.","lost a drinking contest with PLAYER.","was killed with dynamite by PLAYER.","died in close combat to PLAYER.","fought to the edge with PLAYER.","stumbled off a ledge with help by PLAYER.","fell to the great marksmanship of PLAYER.","was given the cold shoulder by PLAYER.","was hit off by a love bomb from PLAYER.","was out of the league of PLAYER.","was struck with Cupid's arrow by PLAYER.","was glazed in BBQ sauce by PLAYER.","slipped in BBQ sauce off the edge spilled by PLAYER.","was not spicy enough for PLAYER.","was thrown chilli powder at by PLAYER.","was wrapped into a gift by PLAYER.","hit the hard-wood floor because of PLAYER.","was pushed down a slope by PLAYER.","was put on the naughty list by PLAYER.","was bitten by PLAYER.","howled into void for PLAYER.","was distracted by a puppy placed by PLAYER.","caught the ball thrown by PLAYER.","be sent to Davy Jones' locker by PLAYER.","be cannonballed to death by PLAYER.","be killed with magic by PLAYER.","be shot and killed by PLAYER.","was spooked by PLAYER.","was spooked off the map by PLAYER.","was totally spooked by PLAYER.","was remotely spooked by PLAYER.","got rekt by PLAYER.","took the L to PLAYER.","got roasted by PLAYER.","got smacked by PLAYER.");
    public static HashMap<String,Location> watchingPlayer = new HashMap<String,Location>();
    public static Location lastAttackLocation;

	@EventHandler
    public void init(FMLInitializationEvent event) {
	    MinecraftForge.EVENT_BUS.register(this);
	    ClientCommandHandler.instance.registerCommand(new CommandPvPParticles());
	    if(!propertiesFile.exists()) {
	    	try {
	    		propertiesFile.createNewFile();
	    		properties.setProperty("killeffect", String.valueOf(KillEffect.NONE));
	    		properties.setProperty("killblock", "");
	    		properties.setProperty("attackeffect", String.valueOf(AttackEffect.SHARPNESS));
	    		properties.setProperty("traileffect", String.valueOf(TrailEffect.HEART));
	    		properties.setProperty("servermode", String.valueOf(ServerMode.NORMAL));
	    		properties.setProperty("nickname", "");
	    		properties.store(new FileOutputStream(propertiesFile), "Dont change it!");
	    	}catch(Exception e) {
	    		e.printStackTrace();
	    	}
	    }
	    try {
	    	properties.load(new FileInputStream(propertiesFile));
	    	killEffect = Integer.valueOf(properties.getProperty("killeffect","0"));
	    	if(killEffect > 1) {
		    	killEffect = 0;
		    }
	    	try {
		    	killBlockID = Integer.valueOf(properties.getProperty("killblock","0"));
		    }catch(NumberFormatException e) {
		    	killBlockID = 0;
		    }
	    	attackEffect = Integer.valueOf(properties.getProperty("attackeffect","0"));
	    	trailEffect = Integer.valueOf(properties.getProperty("traileffect","0"));
	    	serverMode = Integer.valueOf(properties.getProperty("servermode","0"));
	    	nickName = properties.getProperty("nickname","");
	    }catch(Exception e) {
	    	e.printStackTrace();
	    }
    }

    @SubscribeEvent
    public void onLivingDeath(LivingDeathEvent event) {
    	if(event.source.getEntity() != null) {
	    	if(event.source.getEntity().equals(mc.thePlayer)){
	    		EffectManager.playKillEffect(event.entity);
	    	}
    	}
    }

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
    	if(serverMode == ServerMode.HYPIXEL) {
    		String message = SiroQModUtils.removeColorCode(event.message.getUnformattedText());
    		for(String killMessage : skyWarsKillMessages) {
    			if(message.contains(killMessage.replace("PLAYER", mc.thePlayer.getName()))) {
    				String player = message.replace(killMessage.replace("PLAYER", mc.thePlayer.getName()), "").replace(" ", "");
    				if(watchingPlayer.containsKey(player)) {
    					Location loc = watchingPlayer.get(player);
    					EffectManager.playKillEffect(loc.x, loc.y, loc.z, loc.eyeHeight);
    					watchingPlayer.remove(player);
    					return;
    				}
    			}
    		}
    		for(String killMessage : bedWarsKillMessages) {
    			if(message.contains(killMessage.replace("PLAYER", mc.thePlayer.getName()))) {
    				if(!message.contains("FINAL KILL!")) {
	    				String player = message.replace(killMessage.replace("PLAYER", mc.thePlayer.getName()), "").replace(" ", "");
	    				if(watchingPlayer.containsKey(player)) {
	    					Location loc = watchingPlayer.get(player);
	    					EffectManager.playKillEffect(loc.x, loc.y, loc.z, loc.eyeHeight);
	    					watchingPlayer.remove(player);
	    					return;
	    				}
    				}else {
    					String player = message.replace(killMessage.replace("PLAYER", mc.thePlayer.getName()), "").replace("FINAL KILL!", "").replace(" ", "");
	    				if(watchingPlayer.containsKey(player)) {
	    					Location loc = watchingPlayer.get(player);
	    					EffectManager.playKillEffect(loc.x, loc.y, loc.z, loc.eyeHeight);
	    					watchingPlayer.remove(player);
	    					return;
	    				}
    				}
    			}
    		}
    		if(message.contains("coins! Kill")) {
    			if(lastAttackLocation != null) {
    				EffectManager.playKillEffect(lastAttackLocation.x, lastAttackLocation.y, lastAttackLocation.z, lastAttackLocation.eyeHeight);
    			}
    		}
    		if(event.type == 2) {
    			if(message.contains("KILL!")) {
    				String player = message.replaceAll(" KILL!", "");
    				if(watchingPlayer.containsKey(player)) {
    					Location loc = watchingPlayer.get(player);
    					EffectManager.playKillEffect(loc.x, loc.y, loc.z, loc.eyeHeight);
    					watchingPlayer.remove(player);
    					return;
    				}
    			}
    		}
    	}else if(serverMode == ServerMode.HYPIXELNICK) {
    		String message = event.message.getUnformattedText();
    		for(String killMessage : skyWarsKillMessages) {
    			if(message.contains(killMessage.replace("PLAYER", nickName))) {
    				String player = message.replace(killMessage.replace("PLAYER", nickName), "").replace(" ", "");
    				if(watchingPlayer.containsKey(player)) {
    					Location loc = watchingPlayer.get(player);
    					EffectManager.playKillEffect(loc.x, loc.y, loc.z, loc.eyeHeight);
    					watchingPlayer.remove(player);
    					return;
    				}
    			}
    		}
    		for(String killMessage : bedWarsKillMessages) {
    			if(message.contains(killMessage.replace("PLAYER", nickName))) {
    				if(!message.contains("FINAL KILL!")) {
	    				String player = message.replace(killMessage.replace("PLAYER", nickName), "").replace(" ", "");
	    				if(watchingPlayer.containsKey(player)) {
	    					Location loc = watchingPlayer.get(player);
	    					EffectManager.playKillEffect(loc.x, loc.y, loc.z, loc.eyeHeight);
	    					watchingPlayer.remove(player);
	    					return;
	    				}
    				}else {
    					String player = message.replace(killMessage.replace("PLAYER", nickName), "").replace("FINAL KILL!", "").replace(" ", "");
	    				if(watchingPlayer.containsKey(player)) {
	    					Location loc = watchingPlayer.get(player);
	    					EffectManager.playKillEffect(loc.x, loc.y, loc.z, loc.eyeHeight);
	    					watchingPlayer.remove(player);
	    					return;
	    				}
    				}
    			}
    		}
    		if(message.contains("coins! Kill")) {
    			if(lastAttackLocation != null) {
    				EffectManager.playKillEffect(lastAttackLocation.x, lastAttackLocation.y, lastAttackLocation.z, lastAttackLocation.eyeHeight);
    			}
    		}
    		if(event.type == 2) {
    			if(message.contains("KILL!")) {
    				String player = message.replaceAll(" KILL!", "");
    				if(watchingPlayer.containsKey(player)) {
    					Location loc = watchingPlayer.get(player);
    					EffectManager.playKillEffect(loc.x, loc.y, loc.z, loc.eyeHeight);
    					watchingPlayer.remove(player);
    					return;
    				}
    			}
    		}
    	}
    }

    @SubscribeEvent
    public void onRenderPlayer(RenderPlayerEvent.Post event) {
    	watchingPlayer.remove(event.entityPlayer.getName());
    	watchingPlayer.put(event.entityPlayer.getName(), new Location(event.entityPlayer.posX,event.entityPlayer.posY,event.entityPlayer.posZ,event.entityPlayer.getEyeHeight()));
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
    	if(!projectileEntities.isEmpty()) {
	    	for (int i=0; i < projectileEntities.size (); i++) {
	    		EffectManager.playTrailEffect(projectileEntities.get(i));
	    		if(!projectileEntities.get(i).isEntityAlive() || projectileEntities.get(i).onGround) {
	    			projectileEntities.remove(i);
	    		}
	    	}
    	}
    }
    @SubscribeEvent
    public void onEntityJoin(EntityJoinWorldEvent event) {
    	Entity entity = event.entity;
    	if (((entity instanceof EntityEgg)) || ((entity instanceof EntityFireball))|| ((entity instanceof EntityLargeFireball)) || ((entity instanceof EntityPotion)) || ((entity instanceof EntitySmallFireball)) || ((entity instanceof EntitySnowball)) || ((entity instanceof EntityWitherSkull)) || ((entity instanceof EntityThrowable))){
    		if(mc.thePlayer != null) {
    			if(entity.getDistanceSqToEntity(mc.thePlayer) <= 5) {
		    		projectileEntities.add(entity);
		    	}
    		}
        }
    	if(entity instanceof EntityArrow && shotBow) {
    		projectileEntities.add(entity);
    		shotBow = false;
    	}
    	if(entity instanceof EntityFishHook) {
    		if(((EntityFishHook) entity).angler.equals(mc.thePlayer)) {
    			projectileEntities.add(entity);
    		}
    	}
    }
    @SubscribeEvent
    public void onShot(ArrowLooseEvent event) {
    	shotBow = true;
    	new Timer().schedule(new TimerTask() {

			@Override
			public void run() {
				shotBow = false;
			}

    	}, 750);
    }

    @SubscribeEvent
    public void onAttack(AttackEntityEvent event) {
    	lastAttackLocation = new Location(event.entity.posX, event.entity.posY, event.entity.posZ, event.entity.getEyeHeight());
    	new Timer().schedule(new TimerTask() {

			@Override
			public void run() {
				lastAttackLocation = null;
			}

    	}, 750);
    	EffectManager.playAttackEffect(event.target);
    }

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event) {
    	watchingPlayer.clear();
    	if(!sentUpdateInfo) {
    		sentUpdateInfo = true;
    		SiroQModUtils.noticeInfo(MODID);
	    	if(SiroQModUtils.hasUpdate(MODID, VERSION)){
	    		new Timer().schedule(new TimerTask() {
					@Override
					public void run() {
						if(mc.thePlayer != null) {
							mc.thePlayer.addChatMessage(new ChatComponentText("§e[§cPvPParticles§e] §aPvP Particle has new version!"));
						}
					}
	    		},5000);
	    	}
    	}
    }

}
