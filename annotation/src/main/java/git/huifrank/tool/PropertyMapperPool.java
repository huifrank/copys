package git.huifrank.tool;

import git.huifrank.handler.PropertiesMapper;

import java.util.HashMap;
import java.util.Map;

public class PropertyMapperPool {


    private Map<String, PropertiesMapper> pool = new HashMap<>();

    private PropertyMapperPool(){}

    public void register(String clazz,PropertiesMapper propertiesMapper){
        pool.put(clazz,propertiesMapper);
    }




}
