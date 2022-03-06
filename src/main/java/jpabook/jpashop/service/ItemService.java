package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private Item item;

    @Transactional //overRiding해버림
    public void saveItem(Item item){
        this.item = item;
        itemRepository.save(item);
    }
    @Transactional //그때는 변경 감지를 사용해야함 -> 이것이 변경감지임 머지를 쓰지 않고 변경 감지를 사용한다
    public void updateItem(Long itemId,String name,int price,int stockQuantity){
        Item findItem = itemRepository.findOne(itemId); // merge는 book.price = null 이면 null로 업데이트됨

        // findItem.change(price,name,stockQuantity);

        findItem.setPrice(price);
        findItem.setName(name);
        findItem.setStockQuantity(stockQuantity);
    }

    public List<Item> findItems(){
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId){
        return itemRepository.findOne(itemId);
    }
}
