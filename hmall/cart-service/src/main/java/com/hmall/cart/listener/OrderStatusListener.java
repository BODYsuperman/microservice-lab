package com.hmall.cart.listener;


import com.hmall.cart.service.ICartService;
import com.hmall.cart.service.impl.CartServiceImpl;
import com.hmall.common.constants.MqConstants;
import com.hmall.common.utils.UserContext;
import lombok.AllArgsConstructor;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class OrderStatusListener {


    public  final ICartService iCartService;

    @RabbitListener(bindings = @QueueBinding(value = @Queue(value = "cart.clear.queue", durable = "true"),
            exchange = @Exchange(value = MqConstants.TRADE_EXCHANGE_NAME, type = ExchangeTypes.TOPIC,durable = "true"),

            key = MqConstants.ROUTING_KEY_ORDER_CREATE


    ))
    public void listenOrderCreate(List<Long> itemIds, @Header("user-info")Long userId){

        UserContext.setUser(userId);

        iCartService.removeByItemIds(itemIds);

        UserContext.removeUser();


    }
}
