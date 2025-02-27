package net.jrdemiurge.difficultychaosintegration;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;

@Mod(DifficultyChaosIntegration.MOD_ID)
public class DifficultyChaosIntegration
{
    public static final String MOD_ID = "difficultychaosintegration";
    private static final Logger LOGGER = LogUtils.getLogger();

    public DifficultyChaosIntegration()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);

        modEventBus.addListener(this::addCreative);
    }

    private static final String FLAG_FILE_NAME = "ProgressiveDifficultyChaosIntegration/ConfigChanged.txt";

    private void commonSetup(final FMLCommonSetupEvent event) {
        if (!isFlagPresent()) {
            MajruszCompatibility.applyCompatibility(); // Изменения в конфиге сохранятся и на сервере
            createFlagFile();
        }
    }

    private boolean isFlagPresent() {
        File flagFile = new File(FMLPaths.CONFIGDIR.get().toFile(), FLAG_FILE_NAME);
        return flagFile.exists();
    }

    private void createFlagFile() {
        File flagFolder = new File(FMLPaths.CONFIGDIR.get().toFile(), "ProgressiveDifficultyChaosIntegration");
        if (!flagFolder.exists()) {
            flagFolder.mkdirs();  // Создаем папку
        }

        File flagFile = new File(flagFolder, "ConfigChanged.txt");
        if (!flagFile.exists()) {
            try {
                flagFile.createNewFile();  // Создаем файл флага
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
        }
    }
}
