package com.hyoil.jpa.ch03.domainmodel.ex04;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Item {
    Set<Bid> bids = new HashSet<>();

    public Set<Bid> getBids() {
        return Collections.unmodifiableSet(bids); // 변경 불가능 컬렉션
    }

}