package com.exemplo.nolimitpitch;

/**
 * Guarda se a remoção do limite de pitch está ativa no momento.
 * Alternado pela tecla configurada em Op\u00e7\u00f5es > Controles > No Limit Pitch.
 */
public final class NoLimitPitchState {

	private NoLimitPitchState() {
	}

	public static volatile boolean enabled = true;
}
