package dtnpaletteofpaws.common.entity.fabric_helper;

import dtnpaletteofpaws.common.entity.DTNWolf;
import dtnpaletteofpaws.common.network.PacketDistributor;
import dtnpaletteofpaws.common.network.PacketHandler;
import dtnpaletteofpaws.common.network.data.FabricWolfVariantSyncData;
import dtnpaletteofpaws.common.util.WolfVariantUtil;
import dtnpaletteofpaws.common.variant.WolfVariant;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class FabricWolfVariantSyncher {
    
    private final DTNWolf wolf;

    private WolfVariant variant = WolfVariantUtil.getDefault();
    private boolean variantDirty = false;

    public FabricWolfVariantSyncher(DTNWolf wolf) {
        this.wolf = wolf;
    }

    public WolfVariant getVariant() {
        return this.variant;
    }

    public void setVariant(WolfVariant variant) {
        if (!wolf.level().isClientSide)
            this.variantDirty = true;
        this.variant = variant;
    }

    public void tick() {
        if (wolf.level().isClientSide)
            return;
        if (this.variantDirty) {
            this.sendSyncPacketToAllTracking();
        }
    }

    public void onStartSeeingWolf(ServerPlayer player) {
        sendSyncPacketTo(player);
    }

    private void sendSyncPacketTo(ServerPlayer player) {
        PacketHandler.send(PacketDistributor.PLAYER.with(() -> player), 
            new FabricWolfVariantSyncData(this.wolf.getId(), this.getVariant())
        );
    }

    private void sendSyncPacketToAllTracking() {
        PacketHandler.send(PacketDistributor.TRACKING_ENTITY.with(() -> this.wolf), 
            new FabricWolfVariantSyncData(this.wolf.getId(), this.getVariant())
        );
    }

    public void clientHandlePacket(FabricWolfVariantSyncData data) {
        this.setVariant(data.variant);
    }

}
