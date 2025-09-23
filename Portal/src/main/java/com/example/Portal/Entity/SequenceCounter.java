package com.example.Portal.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table
public class SequenceCounter {
    @Id
    private String dateKey;
    private int counter;

    public SequenceCounter(String dateKey,int counter){
        this.dateKey=dateKey;
        this.counter=counter;
    }

    public String getDateKey() {
        return dateKey;
    }

    public void setDateKey(String dateKey) {
        this.dateKey = dateKey;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }
}
