package com.example.Portal.Dto;

import java.time.LocalDateTime;

public class AttandanceRequestClock {
    private String typeOf;
    private LocalDateTime time;
    private String note;

    public String getTypeOf() {
        return typeOf;
    }

    public void setTypeOf(String typeOf) {
        this.typeOf = typeOf;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
