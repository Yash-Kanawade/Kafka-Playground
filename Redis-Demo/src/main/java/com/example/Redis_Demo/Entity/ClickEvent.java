package com.example.Redis_Demo.Entity;

import lombok.Data;

@Data
public class ClickEvent {

    private String buttonId;
    private String pageName;
    private long timeStamp;

}
