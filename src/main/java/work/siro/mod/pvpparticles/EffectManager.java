package work.siro.mod.pvpparticles;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import work.siro.mod.pvpparticles.classes.AttackEffect;
import work.siro.mod.pvpparticles.classes.KillEffect;
import work.siro.mod.pvpparticles.classes.TrailEffect;

public class EffectManager {
	private static double noteColor = 0;

	public static void playKillEffect(Entity entity) {
		switch(PvPParticles.killEffect) {
			case KillEffect.NONE:
				break;
			case KillEffect.REDSTONE:
				playAuxSFX(entity, Blocks.redstone_block);
				break;
			case KillEffect.DIAMOND:
				playAuxSFX(entity, Blocks.diamond_block);
				break;
			case KillEffect.EMERALD:
				playAuxSFX(entity, Blocks.emerald_block);
				break;
			case KillEffect.ICE:
				playAuxSFX(entity, Blocks.ice);
				break;
			case KillEffect.SLIME:
				playAuxSFX(entity,Blocks.slime_block);
				break;
		}
	}

	public static void playKillEffect(double x,double y,double z,double eyeHeight) {
		switch(PvPParticles.killEffect) {
			case KillEffect.NONE:
				break;
			case KillEffect.REDSTONE:
				playAuxSFX(x,y,z,eyeHeight,Blocks.redstone_block);
				break;
			case KillEffect.DIAMOND:
				playAuxSFX(x,y,z,eyeHeight,Blocks.diamond_block);
				break;
			case KillEffect.EMERALD:
				playAuxSFX(x,y,z,eyeHeight,Blocks.emerald_block);
				break;
			case KillEffect.ICE:
				playAuxSFX(x,y,z,eyeHeight,Blocks.ice);
				break;
			case KillEffect.SLIME:
				playAuxSFX(x,y,z,eyeHeight,Blocks.slime_block);
				break;
		}
	}

	public static void playTrailEffect(Entity entity) {
		switch(PvPParticles.trailEffect) {
			case TrailEffect.NONE:
				break;
			case TrailEffect.HEART:
				spawnParticle(entity, EnumParticleTypes.HEART);
				break;
			case TrailEffect.NOTE:
				entity.getEntityWorld().spawnParticle(EnumParticleTypes.NOTE, entity.posX, entity.posY, entity.posZ,noteColor/24, 0, 0, new int[0]);
				noteColor++;
				if(noteColor >= 25) {
					noteColor = 0;
				}
				break;
			case TrailEffect.GREENSTAR:
				spawnParticle(entity, EnumParticleTypes.VILLAGER_HAPPY);
				break;
		}
	}

	public static void playAttackEffect(Entity entity) {
		switch(PvPParticles.attackEffect) {
			case AttackEffect.NONE:
				break;
			case AttackEffect.SHARPNESS:
				emitParticleAtEntity(entity, EnumParticleTypes.CRIT_MAGIC);
				break;
			case AttackEffect.CRITICAL:
				emitParticleAtEntity(entity, EnumParticleTypes.CRIT);
				break;
			case AttackEffect.SLIME:
				emitParticleAtEntity(entity, EnumParticleTypes.SLIME);
				break;
		}
	}

	private static void playAuxSFX(Entity entity,Block blockType) {
		PvPParticles.mc.renderGlobal.playAuxSFX(null, 2001, new BlockPos(entity.posX,entity.posY+entity.getEyeHeight(),entity.posZ), Block.getStateId(blockType.getDefaultState()));
	}

	private static void playAuxSFX(double x , double y , double z ,double eyeHeight,Block blockType) {
		PvPParticles.mc.renderGlobal.playAuxSFX(null, 2001, new BlockPos(x,y+eyeHeight,z), Block.getStateId(blockType.getDefaultState()));
	}

	private static void spawnParticle(Entity entity,EnumParticleTypes particleType) {
		entity.getEntityWorld().spawnParticle(particleType, entity.posX, entity.posY, entity.posZ, 0, 0, 0, new int[0]);
	}


	private static void emitParticleAtEntity(Entity entity,EnumParticleTypes particleType) {
		PvPParticles.mc.effectRenderer.emitParticleAtEntity(entity, particleType);
	}

}
