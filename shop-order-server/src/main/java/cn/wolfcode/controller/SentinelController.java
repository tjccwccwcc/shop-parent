package cn.wolfcode.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
public class SentinelController {
    @RequestMapping("/sentinel1")
    public String sentinel1() throws InterruptedException {
            TimeUnit.SECONDS.sleep(1);
        return "sentinel1";
    }
    @RequestMapping("/sentinel2")
    public String sentinel2(){
        return "测试⾼并发下的问题";
    }
    @RequestMapping("/sentinel3")
    public String sentinel3(){
        return "sentinel3";
    }
}
