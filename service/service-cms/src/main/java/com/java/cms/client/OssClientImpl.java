package com.java.cms.client;

import com.java.commonutils.api.APICODE;
import org.springframework.stereotype.Component;

@Component
public class OssClientImpl implements OssClient {
    @Override
    public APICODE deleteFile(String imageUrl) {
        return APICODE.ERROR().message("服务调用失败");
    }
}
