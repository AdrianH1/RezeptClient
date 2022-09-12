package com.example.rezeptclient;

public class Recipe {

    private int id;
    private String name;
    private String components;

    public Recipe (String name, String components)
    {
        this.name = name;
        this.components = components;
    }

    public int getId() {
        return id;
    }

    public String getComponents() {
        return components;
    }

    public String getName() {
        return name;
    }

    public void setComponents(String components) {
        this.components = components;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
