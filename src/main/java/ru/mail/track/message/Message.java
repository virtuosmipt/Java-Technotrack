package ru.mail.track.message;


import ru.mail.track.comands.CommandType;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 */

public  class Message implements Serializable {

    private Long id;
    private Long sender;
    private String[] args;
    private String infoString;
    private ArrayList<Long> infoLong;
    private ArrayList<String> stringArrayList = new ArrayList<>();

    private CommandType type;

    public Message() {
    }
    public Message(String[] args){
        this.args=args;
    }
    public String[] getArgs(){return this.args;}
    public ArrayList<Long> getInfoLong(){return infoLong;}
    public void setInfoLong(ArrayList<Long> longArrayList){this.infoLong=longArrayList;}
    public void setStringArrayList(ArrayList<String> str){this.stringArrayList=str;}
    public ArrayList<String> getStringArrayList(){return this.stringArrayList;}

    public void setArgs(String[] args){
        this.args=args;
    }

    public void setInfoString(String str){
        this.infoString=str;
    }
    public String getInfoString(){
        return infoString;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSender() {
        return sender;
    }

    public void setSender(Long sender) {
        this.sender = sender;
    }

    public CommandType getType() {
        return type;
    }

    public void setType(CommandType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Message{" +
                ", sender=" + sender +
                ", type=" + type +
                '}';
    }
}
