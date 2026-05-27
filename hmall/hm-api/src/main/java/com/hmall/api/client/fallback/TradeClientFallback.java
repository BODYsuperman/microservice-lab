package com.hmall.api.client.fallback;

import com.hmall.api.client.OrderClient;
import com.hmall.common.exception.BizIllegalException;
import org.springframework.cloud.openfeign.FallbackFactory;

public class TradeClientFallback implements FallbackFactory {
    @Override
    public Object create(Throwable cause) {
        return new OrderClient() {
            @Override
            public void markOrderPaySuccess(Long orderId) {
                throw new BizIllegalException("call trade service failed" + orderId, cause);
            }
        };
    }
}
