package dtnpaletteofpaws;

import dtnpaletteofpaws.client.ClientSetup;
import dtnpaletteofpaws.common.lib.Constants;
import dtnpaletteofpaws.common.network.PacketHandler;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

@Mod(Constants.MOD_ID)
public class DTNPaletteOfPaws {
    
    public static final SimpleChannel HANDLER = NetworkRegistry.ChannelBuilder.named(Constants.CHANNEL_NAME)
            .clientAcceptedVersions(Constants.PROTOCOL_VERSION::equals)
            .serverAcceptedVersions(Constants.PROTOCOL_VERSION::equals)
            .networkProtocolVersion(Constants.PROTOCOL_VERSION::toString)
            .simpleChannel();

    public DTNPaletteOfPaws() {
        var mod_event_bus = FMLJavaModLoadingContext.get().getModEventBus();

        mod_event_bus.addListener(PawsRegistries::onNewRegistry);
        WolfVariants.DTN_WOLF_VARIANT.register(mod_event_bus);
        PawsEntityTypes.ENTITIES.register(mod_event_bus);
        PawsSerializers.SERIALIZERS.register(mod_event_bus);
        mod_event_bus.addListener(PawsEntityTypes::addEntityAttributes);
        mod_event_bus.addListener(this::commonSetup);

        var forge_event_bus = MinecraftForge.EVENT_BUS;

        if (FMLEnvironment.dist == Dist.CLIENT) {
            mod_event_bus.addListener(ClientSetup::setupEntityRenderers);
            mod_event_bus.addListener(ClientSetup::registerLayerDefinitions);
        }
    }

    public void commonSetup(final FMLCommonSetupEvent event) {
        PacketHandler.init();
    }

}
