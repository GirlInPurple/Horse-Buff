package net.F53.HorseBuff.mixin.Client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.tutorial.TutorialManager;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(MinecraftClient.class)
public abstract class InventoryAccessor {
    @Shadow @Final private TutorialManager tutorialManager;

    @Shadow public abstract void setScreen(@Nullable Screen screen);

    @Shadow @Nullable public ClientPlayerEntity player;

    @Redirect(method="net/minecraft/client/MinecraftClient.handleInputEvents()V", at = @At(value = "INVOKE", target = "net/minecraft/client/network/ClientPlayerEntity.openRidingInventory ()V"))
    void teergtoind(ClientPlayerEntity instance){
        assert this.player != null;
        if (MinecraftClient.getInstance().options.sprintKey.isPressed()) {
            tutorialManager.onInventoryOpened();
            setScreen(new InventoryScreen(this.player));
        }
        else {
            instance.openRidingInventory();
        }
    }
}
