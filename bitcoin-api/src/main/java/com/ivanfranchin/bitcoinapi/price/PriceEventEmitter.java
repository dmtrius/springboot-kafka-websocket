package com.ivanfranchin.bitcoinapi.price;

import com.ivanfranchin.bitcoinapi.price.event.PriceChanged;
import com.ivanfranchin.bitcoinapi.price.model.Price;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class PriceEventEmitter {

    private final StreamBridge streamBridge;

    public void send(Price price) {
        PriceChanged priceMessage = new PriceChanged(price.getId(), price.getValue(), price.getTimestamp());
        streamBridge.send("prices-out-0", priceMessage);
        log.info("{} sent to bus.", priceMessage);
    }
}
