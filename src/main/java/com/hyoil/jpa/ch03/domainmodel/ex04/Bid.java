package com.hyoil.jpa.ch03.domainmodel.ex04;

public class Bid {

    private Item item;

    public Bid() {
    }

    public Bid(Item item) {
        this.item = item;
        item.bids.add(this); // 양방향
        // setItem이 없기 때문에 package-private가 된다.
    }

    public Item getItem() {
        return item;
    }

}