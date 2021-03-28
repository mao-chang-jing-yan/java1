package data;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Triple{
    private String tripleId;//三元组id
    private Entity head;//头实体
    private Entity tail;//尾实体

    public String getTripleId() {
        return tripleId;
    }

    public void setTripleId(String tripleId) {
        this.tripleId = tripleId;
    }

    public Triple(String tripleId, Entity head, Entity tail, String rela, String itemId) {
        this.tripleId = tripleId;
        this.head = head;
        this.tail = tail;
        this.rela = rela;
        this.itemId = itemId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Triple triple = (Triple) o;
        return Objects.equals(tripleId, triple.tripleId) &&
                Objects.equals(head, triple.head) &&
                Objects.equals(tail, triple.tail) &&
                Objects.equals(rela, triple.rela) &&
                Objects.equals(itemId, triple.itemId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tripleId, head, tail, rela, itemId);
    }

    @Override
    public String toString() {
        return "Triple{" +
                "tripleId='" + tripleId + '\'' +
                ", head=" + head +
                ", tail=" + tail +
                ", rela='" + rela + '\'' +
                ", itemId='" + itemId + '\'' +
                '}';
    }

    public Triple() {
        this.tripleId = "";
        this.head = null;
        this.tail = null;
        this.rela = "";
        this.itemId = "";
    }

    public Entity getHead() {
        return head;
    }

    public void setHead(Entity head) {
        this.head = head;
    }

    public Entity getTail() {
        return tail;
    }

    public void setTail(Entity tail) {
        this.tail = tail;
    }

    public String getRela() {
        return rela;
    }

    public void setRela(String rela) {
        this.rela = rela;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    private String rela;//关系名称
    private String itemId;//所属条目的id(从这个条目中抽取出来的)

    public JSONObject toJson(){
        Map<String, Object> map1 = this.toMap();
        return new JSONObject(map1);
    };

    public Map<String, Object> toMap(){
        Map<String, Object> map1 = new HashMap<String, Object>();

        map1.put("tripleId", tripleId);
        map1.put("head", head);
        map1.put("tail", tail);
        map1.put("rela", rela);
        map1.put("itemId", itemId);
        return map1;
    }
    public static JSONArray tripleListToJsonArray(List<Triple> l){
        JSONArray jsonArray = new JSONArray();
        for (Triple t:l
             ) {
            jsonArray.add(t.toMap());
        }
        return jsonArray;

    }
}

