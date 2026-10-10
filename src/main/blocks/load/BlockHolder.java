package main.blocks.load;

import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BlockHolder {
    Map<Integer,List<Vector>> id_vecs = new HashMap<>();


    public BlockHolder(){
    }
    public boolean conatins(Integer id){
        return id_vecs.containsKey(id);
    }
    public synchronized void  put(Integer id, Vector vec){
        if(id_vecs.containsKey(id)){
            id_vecs.get(id).add(vec);
        }else{
            List<Vector> vecs = new ArrayList<>();
            vecs.add(vec);
            id_vecs.put(id, vecs);

        }
    }

}
