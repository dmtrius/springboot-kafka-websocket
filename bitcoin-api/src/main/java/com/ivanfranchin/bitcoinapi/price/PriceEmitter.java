package com.ivanfranchin.bitcoinapi.price;

import com.ivanfranchin.bitcoinapi.price.message.PriceMessage;
import com.ivanfranchin.bitcoinapi.price.model.Price;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class PriceEmitter {

    private final StreamBridge streamBridge;

    public void send(Price price) {
        PriceMessage priceMessage = new PriceMessage(price.getId(), price.getValue(), price.getTimestamp());
        streamBridge.send("prices-out-0", priceMessage);
        log.info("{} sent to bus.", priceMessage);
    }
}
