package main.blocks.load;

import main.blocks.BlockZarj;
import org.bukkit.Location;
import org.bukkit.block.Block;

import java.util.Collection;

public class ChunkedBlockLoader implements Runnable{
    private final Collection<Location> locs;
    private final BlockZarj block;

    public ChunkedBlockLoader(Collection<Location> locs,  BlockZarj bl) {
        this.locs = locs;
        block = bl;
    }

    @Override
    public void run() {
        for(Location loc : locs) {
            Block block_vanilla = loc.getBlock();
            if (block_vanilla.getType() != block.getItemBlock().getType()) {
                if (block.getBD() != null) {
                    block_vanilla.setBlockData(block.getBD());
                }
            }
            block_vanilla.setMetadata("ZARJ_BLOCK_ID", block.meta_value);
        }
    }
}

