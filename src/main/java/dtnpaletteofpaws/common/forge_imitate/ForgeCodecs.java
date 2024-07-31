package dtnpaletteofpaws.common.forge_imitate;

import java.util.function.Supplier;


import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;

public class ForgeCodecs {
    
    public static class DefferedCodec<A> implements Codec<A> {
        
        private Supplier<Codec<A>> memoizedSup;

        public DefferedCodec(Supplier<Codec<A>> codecSup) {
            memoizedSup = Suppliers.memoize(codecSup::get);
        }
  
        public <B> DataResult<com.mojang.datafixers.util.Pair<A, B>> decode(DynamicOps<B> ops, B dataB) {
           return this.memoizedSup.get().decode(ops, dataB);
        }
  
        public <B> DataResult<B> encode(A dataA, DynamicOps<B> ops, B dataB) {
           return this.memoizedSup.get().encode(dataA, ops, dataB);
        }
     }

}
