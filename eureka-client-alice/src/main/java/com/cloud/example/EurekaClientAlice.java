package com.cloud.example;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
@RestController
public class EurekaClientAlice {

    @Autowired
    private DiscoveryClient discoveryClient;

    public static void main(String[] args) {
        SpringApplication.run(EurekaClientAlice.class, args);
    }

    @GetMapping("/generate-stuff")
    public String generateStuff() {
        return "Stuff by Alice";
    }

    @GetMapping("/get-stuff/{serviceName}")
    public String generateStuff(@PathVariable("serviceName") String serviceName) {
        return getServiceResponse(serviceName, String.class);
    }

    private <T> T getServiceResponse(String serviceName, Class<T> responseType) {
        List<ServiceInstance> list = discoveryClient.getInstances(serviceName);
        if (list != null && !list.isEmpty()) {
            URI uri = list.get(0).getUri();
            System.out.println(uri.toString());
            if (uri != null) {
                return new RestTemplate().getForObject(uri + "/generate-stuff", responseType);
            }
        }
        return null;
    }

}
