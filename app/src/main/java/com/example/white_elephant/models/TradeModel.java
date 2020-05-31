package com.example.white_elephant.models;

import java.io.Serializable;

public class TradeModel implements Serializable {
    private Item item1;
    private Item item2;
    private boolean user1Confirm;
    private boolean user2Confirm;

    public TradeModel(Item a, Item b){
        item1 = a;
        item2 = b;
    }

    public void setUser1Confirm(boolean a){
        user1Confirm = a;
    }

    public void setUser2Confirm(boolean a){
        user2Confirm = a;
    }

    public Item getItem1() {
        return item1;
    }

    public Item getItem2(){
        return item2;
    }

    public boolean isUser1Confirm() {
        return user1Confirm;
    }

    public boolean isUser2Confirm() {
        return user2Confirm;
    }
}
