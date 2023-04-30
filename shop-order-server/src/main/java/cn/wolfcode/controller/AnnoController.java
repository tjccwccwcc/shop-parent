package cn.wolfcode.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class AnnoController {
    //设置流控，qps=1
    //访问/anno1，达到阈值，调用anno1BlockHandler返回异常
    //访问/anno1?name=wolfcode，调用anno1Fallback返回异常
    @RequestMapping("/anno1")
    @SentinelResource(value = "anno1",
            //当被限流或降级会调用这个字符串对应的方法
            blockHandler="anno1BlockHandler",
            //当方法出现异常（没有这个被降级时就会调用blockHandler）
            fallback = "anno1Fallback"
    )
    public String anno1(String name){
        if("wolfcode".equals(name)){
            throw new RuntimeException();
        }
        return "anno1";
    }
    public String anno1BlockHandler(String name, BlockException ex){
        log.error("{}", ex);
        return "接⼝被限流或者降级了";
    }
    //Throwable时进⼊的⽅法
    public String anno1Fallback(String name,Throwable throwable) {
        log.error("{}", throwable);
        return "接⼝发⽣异常了";
    }
}
