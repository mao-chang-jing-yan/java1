package utlis.thread;

import data.Entity;
import data.KG;
import data.Triple;
import utlis.File;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static java.lang.Thread.State.TERMINATED;

public abstract class MyRunnable implements Runnable {
    private Map<String ,Object> data;
    private String name = "";

    public void setName(String name) {
        this.name = name;
    }

    public MyRunnable(Map<String, Object> data) {
        this.data = data;
    }
    public MyRunnable() { }

    MyRunnable(Map<String ,Object> data, String name) {
        this.data = data;
        this.name = name;
    }
    public void addData(String name, Object value){
        this.data.put(name, value);
    }
    public Object getData(){
        return this.data;
    }
    public Object getDataItem(String name){
        return this.data.get(name);
    }

    public void run() {
        System.out.println("Thread - " + name + " Throwing in " + "MyThread");
//        throw new RuntimeException();
            run1();
    }

    public abstract void run1();

    public static Map<String, String> getMapStringStringObjectToMap(Object obj) throws IllegalAccessException, ClassNotFoundException {
        @SuppressWarnings("unchecked")
        Map<String, String> cast = (Map<String, String>) (obj);
        System.out.println(cast.get("0"));

        return cast;
    }

    public String getName(){
        return this.name;
    };
}


