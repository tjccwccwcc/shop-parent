package cn.wolfcode.filters;

import lombok.Getter;
import lombok.Setter;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@Component
public class TimeGatewayFilterFactory extends
        AbstractGatewayFilterFactory<TimeGatewayFilterFactory.Config> {
    private static final String BEGIN_TIME = "beginTime";
    //构造函数
    public TimeGatewayFilterFactory() {
        super(TimeGatewayFilterFactory.Config.class);
    }

    // - Time=true
    //读取配置⽂件中的参数 赋值到 配置类中
    //和config类里参数对应，读取的filters:中Time参数
    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("show");
    }

    // 拦截到之后就会调用apply方法，吧创建对象时候的反射创建的config传入进来
    @Override
    public GatewayFilter apply(Config config) {
        return new GatewayFilter() {
            @Override
            public Mono<Void> filter(ServerWebExchange exchange,
                                     GatewayFilterChain chain) {
                if(!config.show){
                    return chain.filter(exchange);
                }
                exchange.getAttributes().put(BEGIN_TIME,
                        System.currentTimeMillis());
                /**
                 * pre的逻辑（前置的逻辑）
                 * chain.filter().then(Mono.fromRunable(()->{
                 * post的逻辑（后置的逻辑
                 * }))
                 */
                //注意访问：http://localhost:9000/order-serv/save?pid=1&uid=2
                //System.out.println("前置逻辑,config信息" + config.show);//前置逻辑,config信息true
                return chain.filter(exchange).then(Mono.fromRunnable(()->{
                    //System.out.println("后置逻辑");
                    Long startTime = exchange.getAttribute(BEGIN_TIME);
                    if (startTime != null) {
                        System.out.println(exchange.getRequest().getURI() +
                                "请求耗时: " + (System.currentTimeMillis() - startTime) + "ms");
                    }
                }));
            }
        };
    }
    @Setter
    @Getter
    static class Config{
        private boolean show;
    }
}