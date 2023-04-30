package cn.wolfcode.service;

import cn.wolfcode.domain.Order;

public interface IOrderService {
    Order createOrder(Long productId, Long userId);
}
