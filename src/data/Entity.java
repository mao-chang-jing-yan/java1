package data;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Entity{
    private String entityName;//实体名称
    private String entityId;//实体id

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    private String parentId;//父实体（上一层实体）id
    private String itemId;//所属条目的id(从这个条目中抽取出来的)

    public JSONObject toJson(){
        Map<String, Object> map1 = this.toMap();
        return new JSONObject(map1);
    };

    public Map<String, Object> toMap(){
        Map<String, Object> map1 = new HashMap<String, Object>();

        map1.put("entityName", entityName);
        map1.put("entityId", entityId);
        map1.put("parentId", parentId);
        map1.put("itemId", itemId);
        return map1;
    }

    public Entity(String entityName, String entityId, String parentId, String itemId) {
        this.entityName = entityName;
        this.entityId = entityId;
        this.parentId = parentId;
        this.itemId = itemId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entity entity = (Entity) o;
        return Objects.equals(entityName, entity.entityName) &&
                Objects.equals(entityId, entity.entityId) &&
                Objects.equals(parentId, entity.parentId) &&
                Objects.equals(itemId, entity.itemId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(entityName, entityId, parentId, itemId);
    }

    @Override
    public String toString() {
        return "Entity{" +
                "entityName='" + entityName + '\'' +
                ", entityId='" + entityId + '\'' +
                ", parentId='" + parentId + '\'' +
                ", itemId='" + itemId + '\'' +
                '}';
    }

    public Entity(Node node) {
        this.entityName = node.getNodeName();
        this.entityId = node.getNodeId();
        this.parentId = node.getParentId();
        this.itemId = node.getItemId();
    }

    public Entity() {
        this.entityName = "";
        this.entityId = "";
        this.parentId = "";
        this.itemId = "";
    }
}
