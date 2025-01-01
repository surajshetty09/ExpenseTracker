package com.example;

import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@ViewScoped
public class RecordBean implements Serializable {
    private String name;
    private int age;
    private List<Record> records = new ArrayList<>();
    private boolean showDialog = false;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<Record> getRecords() {
        return records;
    }

    public boolean isShowDialog() {
        return showDialog;
    }

    public void setShowDialog(boolean showDialog) {
        this.showDialog = showDialog;
    }

    public void addRecord() {
    	System.out.println("Entered addRecord");
        if (name != null && !name.isEmpty() && age > 0) {
            records.add(new Record(name, age));
        }
        clear();
        this.showDialog = false; 
    }

    public void openDialog() {
        this.showDialog = true;  
    }

    public void closeDialog() {
        this.showDialog = false;
    }

    public void clear() {
        this.name = "";
        this.age = 0;
    }


    public static class Record {
        private String name;
        private int age;

        public Record(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }
    }
}
