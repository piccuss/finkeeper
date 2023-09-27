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

package pri.piccus.finkeeper.core;

import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import pri.piccus.finkeeper.core.common.Stock;
import pri.piccus.finkeeper.core.config.FinkeeperConfigManager;
import pri.piccus.finkeeper.core.datasource.StockDataSourceFactory;
import pri.piccus.finkeeper.core.keep.KeeperFactory;

import java.util.List;

@Component
public class Finkeeper {

    private static final Logger LOGGER = LoggerFactory.getLogger(Finkeeper.class);

    @Resource
    final StockDataSourceFactory stockDataSourceFactory;

    @Resource
    final KeeperFactory keeperFactory;

    public Finkeeper(StockDataSourceFactory stockDataSourceFactory, KeeperFactory keeperFactory) {
        this.stockDataSourceFactory = stockDataSourceFactory;
        this.keeperFactory = keeperFactory;
    }

    public void keep() throws Exception{
        LOGGER.info("finkeep invoked.");
        var start = System.currentTimeMillis();
        //1、获取配置
        var config = FinkeeperConfigManager.getFinkeeperConfig();
        if (config == null) {
            LOGGER.error("get FinkeeperConfig null, terminate keep.");
            return;
        }
        //2、调用数据源，获取最新数据
        var stocks = stockDataSourceFactory.create(config).fetchStocks();
        //3、compose结果
        var content = parseStocksContent(stocks);
        //4、发送通知
        keeperFactory.create(config, content).keep();
        LOGGER.info("finkeep finished, time cost={}ms", System.currentTimeMillis() - start);
    }

    private String parseStocksContent(List<Stock> stocks) {
        //TODO parse stock result
        return "mock content";
    }

}
