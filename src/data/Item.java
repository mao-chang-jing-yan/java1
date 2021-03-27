package data;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Item {
    private String itemTitle;  //条目标题
    private String itemText;//条目内容
    private String itemId;//条目id
    private Float score=0.0f; //匹配分数（计算相似度时）
    private String entityIds;//从这个条目中抽取出来的实体的id, id之间用‘,’隔开
    private String tripleIds;//从这个条目中抽取出来的三元组的id, id之间用‘,’隔开

    public Item() {
        this.itemTitle = "";
        this.itemText = "";
        this.itemId = "";
        this.chapterId = "";
        this.docId = "";
        this.score = 0.0f;
        this.entityIds = "";
        this.tripleIds = "";
    }

    private String chapterId;//章节号
    private String docId;//文档id

    public Item(String itemTitle, String itemText, String itemId, String chapterId, String docId) {
        this.itemTitle = itemTitle;
        this.itemText = itemText;
        this.itemId = itemId;
        this.chapterId = chapterId;
        this.docId = docId;
    }

    public Item(String itemTitle, String itemText, String itemId, String chapterId, String docId, Float score, String entityIds, String tripleIds) {
        this.itemTitle = itemTitle;
        this.itemText = itemText;
        this.itemId = itemId;
        this.chapterId = chapterId;
        this.docId = docId;
        this.score = score;
        this.entityIds = entityIds;
        this.tripleIds = tripleIds;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public String getItemText() {
        return itemText;
    }

    public void setItemText(String itemText) {
        this.itemText = itemText;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getChapterId() {
        return chapterId;
    }

    public void setChapterId(String chapterId) {
        this.chapterId = chapterId;
    }

    public String getDocId() {
        return docId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(itemTitle, item.itemTitle) &&
                Objects.equals(itemText, item.itemText) &&
                Objects.equals(itemId, item.itemId) &&
                Objects.equals(chapterId, item.chapterId) &&
                Objects.equals(docId, item.docId) &&
                Objects.equals(score, item.score) &&
                Objects.equals(entityIds, item.entityIds) &&
                Objects.equals(tripleIds, item.tripleIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemTitle, itemText, itemId, chapterId, docId, score, entityIds, tripleIds);
    }

    @Override
    public String toString() {
        return "data.Item{" +
                "itemTitle='" + itemTitle + '\'' +
                ", itemText='" + itemText + '\'' +
                ", itemId='" + itemId + '\'' +
                ", chapterId='" + chapterId + '\'' +
                ", docId='" + docId + '\'' +
                ", score=" + score +
                ", entityIds='" + entityIds + '\'' +
                ", tripleIds='" + tripleIds + '\'' +
                '}';
    }

    //    public JSONObject obj = (JSONObject) JSON.toJSON(data.Item);
    public JSONObject toJson(){
        Map<String, Object> map1 = this.toMap();
        return new JSONObject(map1);
    };

    public Map<String, Object> toMap(){
        Map<String, Object> map1 = new HashMap<String, Object>();

        map1.put("itemTitle", itemTitle);
        map1.put("itemText", itemText);
        map1.put("itemId", itemId);
        map1.put("chapterId", chapterId);
        map1.put("docId", docId);
        map1.put("score", score);
        map1.put("entityIds", entityIds);
        map1.put("tripleIds", tripleIds);
        return map1;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public String getEntityIds() {
        return entityIds;
    }

    public void setEntityIds(String entityIds) {
        this.entityIds = entityIds;
    }

    public String getTripleIds() {
        return tripleIds;
    }

    public void setTripleIds(String tripleIds) {
        this.tripleIds = tripleIds;
    }


}

