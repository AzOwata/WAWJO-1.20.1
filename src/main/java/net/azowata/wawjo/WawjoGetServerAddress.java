package net.azowata.wawjo;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.logging.LogUtils;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;

public class WawjoGetServerAddress {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final String CATEGORY = "key.category.wawjo";
    public static final KeyMapping KEY = new KeyMapping("key.wawjo.get_server_address", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_G, CATEGORY);

    @Mod.EventBusSubscriber(modid = Wawjo.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public class Events {
        @SubscribeEvent
        public static void onRegisterKeyMappings(RegisterKeyMappingsEvent event) {
            LOGGER.info("Registering key mapping: " + KEY);
            event.register(KEY);
        }

    }

    @Mod.EventBusSubscriber(modid = Wawjo.MODID, value = Dist.CLIENT)
    public class ClientEvents {
        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event) {
            if (KEY.consumeClick()) {
                LOGGER.info("Key consumed!");
                Minecraft minecraft = Minecraft.getInstance();
                ServerData serverData = minecraft.getCurrentServer();
                if (minecraft.player != null) {
                    if (serverData == null) {
                        minecraft.player.sendSystemMessage(Component.literal("You're not in a server!"));
                    } else {
                        minecraft.player.sendSystemMessage(Component.literal("Server address: " + serverData.ip));
                    }
                }
            }
        }
    }

}
