package jmu.shijh.community_system.common.util;

import com.alibaba.fastjson.JSON;

import java.util.LinkedHashMap;
import java.util.Map;

public class MapBuilder {
     public static class MapBuildAction {
         private final Map<String, Object> map;

         MapBuildAction() {
             map = new LinkedHashMap<>();
         }

         MapBuildAction(Map<? extends String, ?> map) {
             this.map = new LinkedHashMap<>(map);
         }

         public MapBuildAction add(String key, Object value) {
             map.put(key, value);
             return this;
         }

         public Map<String, Object> build() {
             return this.map;
         }

         @SuppressWarnings("unchecked")
         public <K,V> Map<K,V> castBuild() {
             return (Map<K,V>) map;
         }

         public String json() {
             return JSON.toJSONString(this.map);
         }
     }

    public static MapBuildAction map() {
        return new MapBuildAction();
    }

    public static MapBuildAction map(Map<? extends String, ?> map) {
        return new MapBuildAction(map);
    }
}
