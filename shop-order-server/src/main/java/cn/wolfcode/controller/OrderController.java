package cn.wolfcode.controller;

import cn.wolfcode.domain.Order;
import cn.wolfcode.service.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class OrderController {
    @Autowired
    private IOrderService orderService;
    @RequestMapping("/save")
    public Order order(Long pid, Long uid) {
        return orderService.createOrder(pid,uid);
    }
}