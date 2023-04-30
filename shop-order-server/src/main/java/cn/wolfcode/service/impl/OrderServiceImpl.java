package cn.wolfcode.service.impl;

import cn.wolfcode.dao.OrderDao;
import cn.wolfcode.domain.Order;
import cn.wolfcode.domain.Product;
import cn.wolfcode.feign.ProductFeignApi;
import cn.wolfcode.service.IOrderService;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderServiceImpl implements IOrderService {
    @Autowired
    private OrderDao orderDao;
    @Qualifier("cn.wolfcode.feign.ProductFeignApi")
    @Autowired
    private ProductFeignApi productFeignApi;
    //版本三：1
    //版本二：1
    @Override
    public Order createOrder(Long productId,Long userId) {
        log.info("接收到{}号商品的下单请求,接下来调⽤商品微服务查询此商品信息", productId);
        //远程调⽤商品微服务,查询商品信息
        //版本一
        //从nacos中获取服务地址
        //版本二：2
        //版本三：2
        Product product = productFeignApi.findByPid(productId);
        log.info("字节码:{}",productFeignApi.getClass());
        log.info("查询到{}号商品的信息,内容是:{}", productId, JSON.toJSONString(product));
        //创建订单并保存
        Order order = new Order();
        order.setUid(userId);
        order.setUsername("叩丁狼教育");
        order.setPid(productId);
        order.setPname(product.getPname());
        order.setPprice(product.getPprice());
        order.setNumber(1);
        orderDao.save(order);
        log.info("创建订单成功,订单信息为{}", JSON.toJSONString(order));
        return order;
    }
}

//版本一
//        Product product = restTemplate.getForObject(
//                "http://localhost:8081/product/"+productId,Product.class);

//版本二：1
//    @Autowired
//    private DiscoveryClient discoveryClient;
//    int i = 0;
//版本二：2
//        List<ServiceInstance> instance = discoveryClient.getInstances("product-service");
//        i ++;
//        int index = i%instance.size();
//        ServiceInstance serviceInstance = instance.get(index);
//        String url = "http://" + serviceInstance.getHost()+":"+serviceInstance.getPort() + "/product/"  + productId;

//版本三：1
//    @Autowired
//    private RestTemplate restTemplate;
//版本三：2
//        String url = "http://product-service/product/" + productId;
//        log.info("服务查询的地址:{}",url);
//        Product product = restTemplate.getForObject(url, Product.class);