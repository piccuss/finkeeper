package pri.piccus.finkeeper.core.keep;

import jakarta.annotation.Nonnull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import pri.piccus.finkeeper.core.common.FinkeeperConfig;

@Component
public class KeeperFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(KeeperFactory.class);

    @Nonnull
    public KeeperContext create(@Nonnull FinkeeperConfig config, @Nonnull String content) {
        //TODO 待实现真实keep
        var type = "EMPTY";
        var keeper = Keeper.getInstance(type);
        if (keeper == null) {
            throw new IllegalArgumentException(String.format("invalid datasource config,type=%s", type));
        }
        LOGGER.info("get keep instance={}", keeper.getKeeperName());
        return new KeeperContext(keeper, content, config);
    }

    public static class KeeperContext {
        @Nonnull
        private final Keeper keeper;

        @Nonnull
        private final String content;

        @Nonnull
        private final FinkeeperConfig finkeeperConfig;

        private KeeperContext(@Nonnull Keeper keeper,@Nonnull String content,@Nonnull FinkeeperConfig finkeeperConfig) {
            this.keeper = keeper;
            this.content = content;
            this.finkeeperConfig = finkeeperConfig;
        }

        public void keep() {
            keeper.keep(content);
        }
    }


}
