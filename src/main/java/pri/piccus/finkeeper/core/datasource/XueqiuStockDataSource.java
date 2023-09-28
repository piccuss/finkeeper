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
class XueqiuStockDataSource implements StockFetcher{

    private static final Logger LOGGER = LoggerFactory.getLogger(XueqiuStockDataSource.class);

    private static final String API= "stock.xueqiu.com/v5/stock/realtime/quotec.json";

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

    private String buildReqUri(Set<String> stockCodes) {
        return null;
    }
}
