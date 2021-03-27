package data;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Node{
    private String nodeId;//实体id

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
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

    private String nodeName;//实体名称
    private String parentId;//父实体（上一层实体）id
    private String itemId;//所属条目的id(从这个条目中抽取出来的)

    public JSONObject toJson(){
        Map<String, Object> map1 = this.toMap();
        return new JSONObject(map1);
    };

    public Node(String nodeId, String nodeName, String parentId, String itemId) {
        this.nodeId = nodeId;
        this.nodeName = nodeName;
        this.parentId = parentId;
        this.itemId = itemId;
    }

    public Map<String, Object> toMap(){
        Map<String, Object> map1 = new HashMap<String, Object>();

        map1.put("nodeId", nodeId);
        map1.put("nodeName", nodeName);
        map1.put("parentId", parentId);
        map1.put("itemId", itemId);
        return map1;
    }
    public Node(Entity entity){
        this.itemId = entity.getItemId();
        this.parentId = entity.getParentId();
        this.nodeName = entity.getEntityName();
        this.nodeId = entity.getEntityId();
    }

    @Override
    public String toString() {
        return "Node{" +
                "nodeId='" + nodeId + '\'' +
                ", nodeName='" + nodeName + '\'' +
                ", parentId='" + parentId + '\'' +
                ", itemId='" + itemId + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return Objects.equals(nodeId, node.nodeId) &&
                Objects.equals(nodeName, node.nodeName) &&
                Objects.equals(parentId, node.parentId) &&
                Objects.equals(itemId, node.itemId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nodeId, nodeName, parentId, itemId);
    }

    public Node(){
        this.itemId = "";
        this.parentId = "";
        this.nodeName = "";
        this.nodeId = "";
    };

}

