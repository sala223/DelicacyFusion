package com.df.order.promotion;

import java.util.List;

import com.df.idm.entity.User;
import com.df.masterdata.entity.Item;
import com.df.masterdata.entity.Promotion;
import com.df.order.entity.Order;

public interface PromotionRepository {

    List<Promotion> getItemBestPromotions(User user, Item item);

    List<Promotion> getOrderBestPromotions(User user, Order order);

}
