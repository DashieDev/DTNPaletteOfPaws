package dtnpaletteofpaws;

import dtnpaletteofpaws.client.ClientSetup;
import dtnpaletteofpaws.client.data.DTNItemModelProvider;
import dtnpaletteofpaws.common.data.DTNDataRegistriesProvider;
import dtnpaletteofpaws.common.event.EventHandler;
import dtnpaletteofpaws.common.lib.Constants;
import dtnpaletteofpaws.common.network.DTNNetworkHandler;
import dtnpaletteofpaws.common.network.PacketHandler;
import dtnpaletteofpaws.common.spawn.DTNWolfSpawnPlacements;
import dtnpaletteofpaws.dtn_support.DTNSupportEntry;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.network.registration.NetworkRegistry;

@Mod(Constants.MOD_ID)
public class DTNPaletteOfPaws {
    
    // public static final SimpleChannel HANDLER = NetworkRegistry.ChannelBuilder.named(Constants.CHANNEL_NAME)
    //         .clientAcceptedVersions(Constants.PROTOCOL_VERSION::equals)
    //         .serverAcceptedVersions(Constants.PROTOCOL_VERSION::equals)
    //         .networkProtocolVersion(Constants.PROTOCOL_VERSION::toString)
    //         .simpleChannel();

    public DTNPaletteOfPaws() {
        var mod_event_bus = ModLoadingContext.get().getActiveContainer().getEventBus();

        mod_event_bus.addListener(DTNRegistries::onNewRegistry);
        mod_event_bus.addListener(DTNRegistries::onNewDataRegistry);
        WolfVariants.DTN_WOLF_VARIANT.register(mod_event_bus);
        DTNEntityTypes.ENTITIES.register(mod_event_bus);
        DTNSerializers.SERIALIZERS.register(mod_event_bus);
        DTNItems.ITEM.register(mod_event_bus);
        mod_event_bus.addListener(DTNEntityTypes::addEntityAttributes);
        mod_event_bus.addListener(DTNWolfSpawnPlacements::onRegisterSpawnPlacements);
        mod_event_bus.addListener(DTNNetworkHandler::onRegisterPayloadEvent);
        mod_event_bus.addListener(this::commonSetup);
        mod_event_bus.addListener(this::onGatherData);

        var forge_event_bus = NeoForge.EVENT_BUS;
        forge_event_bus.register(new EventHandler());

        if (FMLEnvironment.dist == Dist.CLIENT) {
            mod_event_bus.addListener(ClientSetup::setupEntityRenderers);
            mod_event_bus.addListener(ClientSetup::registerLayerDefinitions);
        }

        DTNSupportEntry.start(mod_event_bus, forge_event_bus);
    }

    public void commonSetup(final FMLCommonSetupEvent event) {
        DTNNetworkHandler.init();
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
