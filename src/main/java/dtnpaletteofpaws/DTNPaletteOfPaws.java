package dtnpaletteofpaws;

import dtnpaletteofpaws.client.ClientSetup;
import dtnpaletteofpaws.client.data.DTNItemModelProvider;
import dtnpaletteofpaws.common.data.DTNDataRegistriesProvider;
import dtnpaletteofpaws.common.event.EventHandler;
import dtnpaletteofpaws.common.lib.Constants;
import dtnpaletteofpaws.common.network.PacketHandler;
import dtnpaletteofpaws.common.particle.DTNParticleProviders;
import dtnpaletteofpaws.common.spawn.DTNWolfSpawnPlacements;
import dtnpaletteofpaws.common.spawn.DTNWolffSpawnEventHandler;
import dtnpaletteofpaws.dtn_support.DTNSupportEntry;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.data.event.GatherDataEvent;
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

        mod_event_bus.addListener(DTNRegistries::onNewRegistry);
        mod_event_bus.addListener(DTNRegistries::onNewDataRegistry);
        WolfVariants.DTN_WOLF_VARIANT.register(mod_event_bus);
        DTNEntityTypes.ENTITIES.register(mod_event_bus);
        DTNSerializers.SERIALIZERS.register(mod_event_bus);
        DTNParticles.PARTICLES.register(mod_event_bus);
        DTNItems.ITEM.register(mod_event_bus);
        mod_event_bus.addListener(DTNEntityTypes::addEntityAttributes);
        mod_event_bus.addListener(DTNWolfSpawnPlacements::onRegisterSpawnPlacements);
        mod_event_bus.addListener(this::commonSetup);
        mod_event_bus.addListener(this::onGatherData);

        var forge_event_bus = MinecraftForge.EVENT_BUS;
        forge_event_bus.register(new EventHandler());
        forge_event_bus.register(new DTNWolffSpawnEventHandler());

        if (FMLEnvironment.dist == Dist.CLIENT) {
            mod_event_bus.addListener(ClientSetup::setupEntityRenderers);
            mod_event_bus.addListener(ClientSetup::registerLayerDefinitions);
            mod_event_bus.addListener(DTNParticleProviders::onRegisterProv);
        }

        DTNPConfig.init(mod_event_bus);

        DTNSupportEntry.start(mod_event_bus, forge_event_bus);
    }

    public void commonSetup(final FMLCommonSetupEvent event) {
        PacketHandler.init();
        DTNSupportEntry.startCommonSetup();
    }

    public void onGatherData(final GatherDataEvent event) {
        DTNDataRegistriesProvider.start(event);
        if (event.includeClient()) {
            var gen = event.getGenerator();
            var file_helper = event.getExistingFileHelper();
            var pack_output = gen.getPackOutput();
            gen.addProvider(true, new DTNItemModelProvider(pack_output, file_helper));
        }
    }

}
