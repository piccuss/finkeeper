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

import com.google.common.collect.ImmutableList;
import jakarta.annotation.Nonnull;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import pri.piccus.finkeeper.core.common.Stock;
import pri.piccus.finkeeper.utils.HttpUtil;
import pri.piccus.finkeeper.utils.JsonUtil;

import java.text.ParseException;
import java.util.*;

/**
 * sample:
 * `curl 'https://stock.xueqiu.com/v5/stock/realtime/quotec.json?symbol=SH600036,SH513500' \
 * -H 'User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36 Edg/114.0.1823.82' \
 * --compressed`
 */
@Component
class XueqiuStockDataSource implements StockFetcher{

    private static final Logger LOGGER = LoggerFactory.getLogger(XueqiuStockDataSource.class);

    private static final String API= "https://stock.xueqiu.com/v5/stock/realtime/quotec.json?symbol=";

    @Nonnull
    @Override
    public String getFetcherName() {
        return "xueqiu";
    }

    @Nonnull
    @Override
    public List<Stock> fetchStocks(@Nonnull Set<String> stockCodes) {
        Objects.requireNonNull(stockCodes);
        var reqUri = buildReqUri(stockCodes);
        LOGGER.info("build req uri={}", reqUri);
        var rawData = HttpUtil.get(reqUri);
        if (rawData == null) {
            LOGGER.error("");
            return ImmutableList.of();
        }
        return parseData(rawData);
    }

    private String buildReqUri(@Nonnull Set<String> stockCodes) {
        return API + Strings.join(stockCodes, ',');
    }

    private List<Stock> parseData(@Nonnull String rawData) {
        List<Stock> list = new ArrayList<>();
        try {
            var tree = JsonUtil.MAPPER.readTree(rawData);
            if (tree == null) {
                throw new RuntimeException("readTree json parse is null");
            }
            var dataCollectionNode = tree.get("data");
            if (dataCollectionNode == null) {
                throw new RuntimeException("readTree json get data node is null");
            }
            dataCollectionNode.forEach(dataNode -> list.add(new Stock("", dataNode.get("symbol").asText(), dataNode.get("current").asDouble())));

        } catch (Exception e) {
            LOGGER.error("parseData failed.e={}", e.getMessage());
        }
        return ImmutableList.copyOf(list);
    }
}
