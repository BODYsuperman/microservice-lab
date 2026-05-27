package com.hmall.api.client.fallback;

import com.hmall.api.client.UserClient;
import com.hmall.common.exception.BizIllegalException;
import org.springframework.cloud.openfeign.FallbackFactory;

public class UserClientFallback implements FallbackFactory {
    @Override
    public Object create(Throwable cause) {
        return new UserClient() {
            @Override
            public void deductMoney(String pw, Integer amount) {
                throw  new BizIllegalException("deduct balance failed" + amount, cause);
            }
        };
    }
}
