package cn.wolfcode.feign;

import cn.wolfcode.domain.Product;
import cn.wolfcode.feign.fallback.ProductFeignFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

//远程调用服务名称
//@FeignClient(name = "product-service")
@FeignClient(name = "product-service",fallback = ProductFeignFallBack.class)
public interface ProductFeignApi {
    @RequestMapping("/product/{pid}")
    Product findByPid(@PathVariable("pid") Long pid);
}
