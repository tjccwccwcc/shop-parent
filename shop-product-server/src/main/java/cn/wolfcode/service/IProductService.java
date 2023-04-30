package cn.wolfcode.service;

import cn.wolfcode.domain.Product;

public interface IProductService {
    Product findByPid(Long pid);
}
