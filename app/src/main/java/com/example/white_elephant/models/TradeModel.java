package com.example.white_elephant.models;

public class TradeModel {
    private ItemModel item1;
    private ItemModel item2;
    private boolean user1Confirm;
    private boolean user2Confirm;

    public TradeModel(ItemModel a, ItemModel b){
        item1 = a;
        item2 = b;
    }

    public void setUser1Confirm(boolean a){
        user1Confirm = a;
    }

    public void setUser2Confirm(boolean a){
        user2Confirm = a;
    }

    public ItemModel getItem1() {
        return item1;
    }

    public ItemModel getItem2(){
        return item2;
    }

    public boolean isUser1Confirm() {
        return user1Confirm;
    }

    public boolean isUser2Confirm() {
        return user2Confirm;
    }
}
