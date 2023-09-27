/*
 *     Copyright (C) 2023 piccus@outlook.com
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

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
