package cn.wolfcode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableDiscoveryClient
public class ApiGatewayServer {
    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayServer.class,args);
    }
}