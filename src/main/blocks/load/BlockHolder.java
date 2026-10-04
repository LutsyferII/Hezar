package main.blocks.load;

import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class BlockHolder {
    int block_id;
    List<Vector> vecs = new ArrayList<>();

    public BlockHolder(int id){
        block_id=id;
    }
    public void addVector(Vector vec){
        vecs.add(vec);
    }
    public List<Vector> getVecs(){
        return vecs;
    }
    public boolean validId(int test_id){
        return (test_id == block_id);
    }
    public int getId(){
        return block_id;
    }
}
