package com.SecondHand.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public Item saveItem(Item item) {
        return itemRepository.save(item);
    }

    public List<Item> getAllItems() {
        return itemRepository.findAll(); // 모든 아이템 목록을 반환
    }

    public Item getItemById(Long id) {
        Optional<Item> item = itemRepository.findById(id); // 아이템 조회
        return item.orElse(null); // 아이템이 없으면 null 반환
    }

    public void deleteItem(Long id) {
        itemRepository.deleteById(id); // 아이템을 데이터베이스에서 삭제
    }
}
