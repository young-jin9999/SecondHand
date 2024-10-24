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

    public Item updateItem(Item item) {
        // 아이템 ID가 null인지 확인
        if (item.getId() == null) {
            throw new IllegalArgumentException("아이템 ID는 null일 수 없습니다.");
        }

        // 기존 아이템이 존재하는지 확인 후 업데이트
        if (itemRepository.existsById(item.getId())) {
            return itemRepository.save(item); // 아이템 업데이트
        } else {
            throw new RuntimeException("아이템을 찾을 수 없습니다."); // 예외 처리
        }
    }
}
