package main.blocks.load;

import Configs.BlockConfig;
import main.Futh.StaticItem;
import main.blocks.BlockZarj;
import main.hezar;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class ZBlocksListener implements Listener {


    @EventHandler
    public void onWorldLoad(WorldLoadEvent e) {
        String worldName = e.getWorld().getName();
        if(BlockSavedLoader.pendingBlocks.containsKey(worldName)){
            BlockHolder block_holder = BlockSavedLoader.pendingBlocks.remove(worldName);
            hezar.inst.getLogger().info("[Hezar] Мир '" + worldName + "' загружен. Начинаю обработку " + block_holder.getVecs().size() + " отложенных блоков (ID: " +  block_holder.getId() + ")");

            BlockZarj zb = BlockConfig.getBlockZarj(block_holder.getId());
            if (zb == null) {
                hezar.inst.getLogger().warning("[Hezar] Не удалось найти BlockZarj для ID: " + block_holder.getId());
                return;
            }
            List<Vector> vecs = block_holder.getVecs();
            int totalLocs = vecs.size();
            int chunkSize = Math.min(25, totalLocs);
            World world = e.getWorld();
            for (int i = 0; i < totalLocs; i += chunkSize) {
                final int end = Math.min(i + chunkSize, totalLocs);
                final List<Vector> batchVecs = vecs.subList(i, end);
                Bukkit.getScheduler().runTaskAsynchronously(hezar.inst, () -> {
                    List<Location> locsReal = new ArrayList<>(batchVecs.size());
                    for (Vector vec : batchVecs) {
                        locsReal.add(vec.toLocation(world));
                    }
                    // 2. Синхронно выполняем манипуляции с миром через твой готовый класс
                    Bukkit.getScheduler().runTask(hezar.inst, new ChunkedBlockLoader(locsReal, zb));
                });

            }
        }
    }
}
