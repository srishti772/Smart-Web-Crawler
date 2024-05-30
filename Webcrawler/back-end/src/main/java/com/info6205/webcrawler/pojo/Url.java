package com.info6205.webcrawler.pojo;

public class Url {
    private static int idCounter = 1; 
    private int id; 
    private String path;
    private String name;
    private int status;
    private int level;
    private String message;


    public Url() {
        this.id = idCounter++; // Increment and assign ID
        this.name="Untitled";
    }

    public static void resetCounter(){
        idCounter=1;
    }

  

    public Url(String path) {
        this.path = path.toLowerCase();
    }



    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path.toLowerCase();
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.level = level;
    }


    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }
   


   
    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", path='" + getPath() + "'" +
            ", name='" + getName() + "'" +
            ", status='" + getStatus() + "'" +
            ", level='" + getLevel() + "'" +
            "}";
    }
   
    
}

    
