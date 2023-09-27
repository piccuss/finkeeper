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
