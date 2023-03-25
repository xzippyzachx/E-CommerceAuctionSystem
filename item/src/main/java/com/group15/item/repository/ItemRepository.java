package com.group15.item.repository;

import com.group15.item.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Integer> {
   @Query(value="SELECT i FROM Item i WHERE LOWER(i.itm_name) LIKE %:keyword% OR LOWER(i.itm_description) LIKE %:keyword%")
   List<Item> findByItmNameContainingOrItmDescriptionContaining(@Param("keyword") String keyword);

   @Modifying
   @Transactional
   @Query(value="CALL item_data_reset()", nativeQuery = true)
   void resetItemData();
}
