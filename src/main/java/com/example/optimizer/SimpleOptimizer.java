package com.example.optimizer;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.CloudRenderMode;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.GraphicsMode;
import net.minecraft.client.option.ParticleMode;

/**
 * SimpleOptimizer — Single-file client-side Fabric mod for Minecraft 1.21.4.
 * Optimized for PojavLauncher / low-end Android devices.
 * Disables clouds, smooth lighting, fog, and reduces particles heavily.
 */
public class SimpleOptimizer implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        // Force settings on every client tick so they don't get reset
        ClientTickEvents.END_CLIENT_TICK.register(client -> {

            if (client == null || client.options == null) return;

            GameOptions options = client.options;

            // 1. Disable clouds entirely (saves GPU on mobile)
            if (options.getCloudRenderMode().getValue() != CloudRenderMode.OFF) {
                options.getCloudRenderMode().setValue(CloudRenderMode.OFF);
            }

            // 2. Disable smooth lighting (ambient occlusion) — heavy on mobile GPUs
            if (options.getAo().getValue()) {
                options.getAo().setValue(false);
            }

            // 3. Set graphics to FAST — removes fancy water, leaves, etc.
            if (options.getGraphicsMode().getValue() != GraphicsMode.FAST) {
                options.getGraphicsMode().setValue(GraphicsMode.FAST);
            }

            // 4. Minimal particles — critical for PojavLauncher FPS
            //    Cuts potions, crits, water, sand, leaves, everything
            if (options.getParticles().getValue() != ParticleMode.MINIMAL) {
                options.getParticles().setValue(ParticleMode.MINIMAL);
            }
        });
    }
}
