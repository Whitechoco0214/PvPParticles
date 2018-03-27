package work.siro.mod.pvpparticles;

import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ReportedException;
import work.siro.mod.pvpparticles.classes.AttackEffect;
import work.siro.mod.pvpparticles.classes.KillEffect;
import work.siro.mod.pvpparticles.classes.TrailEffect;

public class EffectManager {

	private static double noteColor = 0;

	public static void playKillEffect(Entity entity) {
		switch(PvPParticles.killEffect) {
			case KillEffect.NONE:
				break;
			case KillEffect.BLOCKBREAK:
				playBlockBreak(entity,PvPParticles.killBlockID);
				break;
		}
	}

	public static void playKillEffect(double x,double y,double z,double eyeHeight) {
		switch(PvPParticles.killEffect) {
			case KillEffect.NONE:
				break;
			case KillEffect.BLOCKBREAK:
				playBlockBreak(x,y,z,eyeHeight,PvPParticles.killBlockID);
                                playBlockBreak(x,y,z,eyeHeight,PvPParticles.killBlockID);
				break;
		}
	}

	public static void playTrailEffect(Entity entity) {
		switch(PvPParticles.trailEffect) {
			case TrailEffect.NONE:
				break;
			case TrailEffect.PARTICLETRAIL:
				try {
					EnumParticleTypes particle = EnumParticleTypes.valueOf(PvPParticles.trailParticle);
					if(particle.equals(EnumParticleTypes.NOTE)) {
						entity.getEntityWorld().spawnParticle(EnumParticleTypes.NOTE, entity.posX, entity.posY, entity.posZ,noteColor/24, 0, 0, new int[0]);
						noteColor++;
						if(noteColor >= 25) {
							noteColor = 0;
						}
					}else {
						spawnParticle(entity, EnumParticleTypes.valueOf(PvPParticles.trailParticle));
					}
				}catch(IllegalArgumentException e) {}catch(ReportedException e) {}
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
			case AttackEffect.FLAME:
				emitParticleAtEntity(entity, EnumParticleTypes.FLAME);
				break;
			case AttackEffect.PORTAL:
				emitParticleAtEntity(entity, EnumParticleTypes.PORTAL);
				break;
			case AttackEffect.ENCHANT:
				emitParticleAtEntity(entity, EnumParticleTypes.ENCHANTMENT_TABLE);
				break;
		}
	}

	private static void playBlockBreak(Entity entity,int blockId) {
		PvPParticles.mc.renderGlobal.playAuxSFX(null, 2001, new BlockPos(entity.posX,entity.posY+entity.getEyeHeight(),entity.posZ), blockId);
	}

	private static void playBlockBreak(double x , double y , double z ,double eyeHeight,int blockId) {
		PvPParticles.mc.renderGlobal.playAuxSFX(null, 2001, new BlockPos(x,y+eyeHeight,z), blockId);
	}

	private static void spawnParticle(Entity entity,EnumParticleTypes particleType) {
		entity.getEntityWorld().spawnParticle(particleType, entity.posX, entity.posY, entity.posZ, 0, 0, 0, new int[0]);
	}

	private static void emitParticleAtEntity(Entity entity,EnumParticleTypes particleType) {
		PvPParticles.mc.effectRenderer.emitParticleAtEntity(entity, particleType);
	}

}
