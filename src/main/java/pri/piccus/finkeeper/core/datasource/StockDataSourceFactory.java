package pri.piccus.finkeeper.core.datasource;

import jakarta.annotation.Nonnull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import pri.piccus.finkeeper.core.common.FinkeeperConfig;
import pri.piccus.finkeeper.core.common.Stock;

import java.util.List;
import java.util.Objects;

@Component
public class StockDataSourceFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(StockDataSourceFactory.class);

    @Nonnull
    public StockDataSourceContext create(@Nonnull FinkeeperConfig config) throws IllegalArgumentException {
        var fetcher = StockFetcher.getInstance(config.getStockDataSource());
        if (fetcher == null) {
            throw new IllegalArgumentException(String.format("invalid datasource config,type=%s", config.getStockDataSource()));
        }
        LOGGER.info("get fetcher instance={}", fetcher.getFetcherName());
        return new StockDataSourceContext(fetcher, config);
    }

    public static class StockDataSourceContext {
        private final StockFetcher stockFetcher;

        private final FinkeeperConfig finkeeperConfig;

        private StockDataSourceContext(@Nonnull StockFetcher stockFetcher,@Nonnull FinkeeperConfig finkeeperConfig) {
            Objects.requireNonNull(stockFetcher);
            Objects.requireNonNull(finkeeperConfig);
            this.stockFetcher = stockFetcher;
            this.finkeeperConfig = finkeeperConfig;
        }

        @Nonnull
        public List<Stock> fetchStocks() {
            return stockFetcher.fetchStocks(finkeeperConfig.getStockCodes());
        }
    }
}
