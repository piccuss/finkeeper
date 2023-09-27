package pri.piccus.finkeeper.core.datasource;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.annotation.PostConstruct;
import pri.piccus.finkeeper.core.common.Stock;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface StockFetcher {

    @Nonnull
    String getFetcherName();

    @Nonnull
    List<Stock> fetchStocks(@Nonnull Set<String> stockCodes);

    @PostConstruct
    default void register() {
        StockFetcherRegistry.register(getFetcherName(), this);
    }

    class StockFetcherRegistry {
        private static final Map<String, StockFetcher> FETCHER_MAP = new HashMap<>();

        private static void register(@Nonnull String fetcherName, @Nonnull StockFetcher fetcher) {
            FETCHER_MAP.put(fetcherName, fetcher);
        }
    }

    @Nullable
    static StockFetcher getInstance(@Nonnull String fetcherName) {
        return StockFetcherRegistry.FETCHER_MAP.get(fetcherName);
    }

}
