package pri.piccus.finkeeper.core.datasource;

import jakarta.annotation.Nonnull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import pri.piccus.finkeeper.core.common.Stock;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * sample:
 * `curl 'https://stock.xueqiu.com/v5/stock/realtime/quotec.json?symbol=SH600036,SH513500' \
 * -H 'User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36 Edg/114.0.1823.82' \
 * --compressed`
 */
@Component
public class XueqiuStockDataSource implements StockFetcher{

    private static final Logger LOGGER = LoggerFactory.getLogger(XueqiuStockDataSource.class);

    @Nonnull
    @Override
    public String getFetcherName() {
        return "xueqiu";
    }

    @Nonnull
    @Override
    public List<Stock> fetchStocks(@Nonnull Set<String> stockCodes) {
        //TODO 实现真实获取
        LOGGER.info("mock fetch, codes={}", stockCodes);
        return new ArrayList<>();
    }
}
