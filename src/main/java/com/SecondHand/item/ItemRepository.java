package com.SecondHand.item; // item 패키지 내의 Repository

import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
