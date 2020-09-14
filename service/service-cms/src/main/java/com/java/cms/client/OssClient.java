package com.java.cms.client;

import com.java.commonutils.api.APICODE;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
                //                  熔断降级的注解
@FeignClient(name = "service-oss",fallback = OssClientImpl.class)//要调用谁就写谁的服务名
@Component
public interface OssClient {

    @DeleteMapping("/oss/deleteFile/{imageUrl}")
    public APICODE deleteFile(@PathVariable(name = "imageUrl") String imageUrl);

}
