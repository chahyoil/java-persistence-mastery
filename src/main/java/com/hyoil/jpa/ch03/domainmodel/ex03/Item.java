package com.hyoil.jpa.ch03.domainmodel.ex03;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Item {
    // Item <-> Bid : 사용자는 각 품목에 대해 입찰을 여러 번 할 수 있음
    private Set<Bid> bids = new HashSet<>();

    public Set<Bid> getBids() {
        return Collections.unmodifiableSet(bids);
    }

    public void addBid(Bid bid) {
        if (bid == null)
            throw new NullPointerException("Can't add null Bid");
        if (bid.getItem() != null)
            throw new IllegalStateException("Bid is already assigned to an Item");
        bids.add(bid);
        bid.setItem(this);
    }


}