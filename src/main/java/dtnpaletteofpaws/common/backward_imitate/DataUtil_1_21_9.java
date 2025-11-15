package dtnpaletteofpaws.common.backward_imitate;

import java.util.function.Function;

import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackResources;
import net.neoforged.neoforge.resource.JarContentsPackResources;
import net.neoforged.neoforgespi.language.IModInfo;

public class DataUtil_1_21_9 {
    
    public static Function<PackLocationInfo, PackResources> getPackResourceSupplier(IModInfo modInfo, String prefix) {
        return (pack_info) -> {
            var contents = modInfo.getOwningFile().getFile().getContents();
            return new JarContentsPackResources(pack_info, contents, prefix);
        };
    }

}
