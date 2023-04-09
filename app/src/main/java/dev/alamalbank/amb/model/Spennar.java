package dev.alamalbank.amb.model;

public class Spennar {
    private    String id="";
    private  String name="";

    public Spennar() {
    }

    public Spennar(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
