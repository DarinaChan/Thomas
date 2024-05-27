package edu.thomas;

public class Incident {
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    String desc;
    String id;
    int typeId;
    public Incident(String id, int typeId, String desc){
        this.id = id;
        this.typeId = typeId;
        this.desc = desc;
    }

}
