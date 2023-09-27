package pri.piccus.finkeeper.core.config;

import jakarta.annotation.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;
import pri.piccus.finkeeper.core.common.FinkeeperConfig;
import pri.piccus.finkeeper.utils.JsonUtil;

import java.io.FileNotFoundException;
import java.io.IOException;

public class FinkeeperConfigManager {

    private static final String CONFIG_FILE_DEST = "./finkeeper_config.json";

    private static final Logger LOGGER = LoggerFactory.getLogger(FinkeeperConfig.class);

    private static FinkeeperConfig loadFinkeeperConfig() {
        try {
            var file = ResourceUtils.getFile(CONFIG_FILE_DEST);
            var json = JsonUtil.MAPPER.readTree(file);
            var builder = FinkeeperConfig.Builder.create();
            json.get("stock_codes").forEach(jsonNode -> builder.addStockCode(jsonNode.asText()));
            var config = builder.setReceiver(json.get("receiver").asText())
                    .setStockDataSource(json.get("stock_data_source").asText())
                    .build();
            LOGGER.info("load FinkeeperConfig file success, config={}", config);
            return config;
        } catch (FileNotFoundException e) {
            LOGGER.error("FinkeeperConfig file not found. desc={}", CONFIG_FILE_DEST);
            return null;
        } catch (IOException e) {
            LOGGER.error("FinkeeperConfig initialize failed.", e);
            return null;
        }
    }

    private static final class FinkeeperConfigHolder {
        private static final FinkeeperConfig finkeeperConfig = loadFinkeeperConfig();
    }

    @Nullable
    public static FinkeeperConfig getFinkeeperConfig() {
        if (FinkeeperConfigHolder.finkeeperConfig == null) {
            LOGGER.error("finkeeperConfig not initialize!");
            return null;
        }
        return FinkeeperConfigHolder.finkeeperConfig;
    }

    public static void main(String[] args) {
        LOGGER.info("t={}", getFinkeeperConfig());
    }
}
