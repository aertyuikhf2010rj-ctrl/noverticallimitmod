package com.exemplo.nolimitpitch.mixin;

import com.exemplo.nolimitpitch.NoLimitPitchState;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Em net.minecraft.world.entity.Entity, o método:
 *
 *   public void turn(double yRot, double xRot) {
 *       float f = (float) xRot * 0.15F;
 *       float g = (float) yRot * 0.15F;
 *       this.setXRot(this.getXRot() + f);
 *       this.setYRot(this.getYRot() + g);
 *       this.setXRot(Mth.clamp(this.getXRot(), -90.0F, 90.0F)); // <- é isto que trava o pitch
 *       ...
 *   }
 *
 * é chamado pelo MouseHandler (turnPlayer) a cada movimento do mouse para
 * girar a câmera/jogador. O @Redirect abaixo intercepta a chamada a
 * Mth.clamp(float,float,float) feita dentro desse método específico e
 * simplesmente devolve o valor sem cortar, deixando o pitch (xRot) livre
 * para passar de 90/-90 e continuar girando indefinidamente (>360/<-360)
 * em qualquer direção.
 *
 * A matemática de direção do olhar (seno/cosseno sobre xRot) do Minecraft
 * já suporta ângulos fora de [-90, 90] de forma contínua — por isso a
 * câmera gira suavemente sem "travar" nem inverter de forma abrupta.
 */
@Mixin(Entity.class)
public abstract class EntityTurnMixin {

	@Redirect(
		method = "turn(DD)V",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/util/Mth;clamp(FFF)F"
		)
	)
	private float nolimitpitch$semLimiteVertical(float valor, float minimo, float maximo) {
		if (NoLimitPitchState.enabled) {
			return valor;
		}
		// Tecla desativou o recurso: mantém o comportamento vanilla original.
		return Mth.clamp(valor, minimo, maximo);
	}
}
