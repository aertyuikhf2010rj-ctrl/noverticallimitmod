package com.exemplo.nolimitpitch;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import org.lwjgl.glfw.GLFW;

public class NoLimitPitchClient implements ClientModInitializer {

	// Categoria própria pra tecla aparecer separada em Opções > Controles.
	private static final KeyMapping.Category CATEGORIA =
		KeyMapping.Category.register(Identifier.fromNamespaceAndPath("nolimitpitch", "main"));

	// Sem tecla padrão (GLFW_KEY_UNKNOWN): o jogador escolhe a tecla em
	// Opções > Controles > No Limit Pitch.
	private static final KeyMapping TECLA_ALTERNAR = KeyBindingHelper.registerKeyBinding(
		new KeyMapping(
			"key.nolimitpitch.toggle",
			InputConstants.Type.KEYSYM,
			GLFW.GLFW_KEY_UNKNOWN,
			CATEGORIA
		)
	);

	@Override
	public void onInitializeClient() {
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			while (TECLA_ALTERNAR.consumeClick()) {
				NoLimitPitchState.enabled = !NoLimitPitchState.enabled;
				if (client.player != null) {
					client.player.sendSystemMessage(Component.translatable(
						NoLimitPitchState.enabled
							? "message.nolimitpitch.enabled"
							: "message.nolimitpitch.disabled"
					));
				}
			}
		});
	}
}
