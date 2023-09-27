package pri.piccus.finkeeper.core.keep;

import jakarta.annotation.Nonnull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class EmptyKeeperImpl implements Keeper {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmptyKeeperImpl.class);

    @Nonnull
    @Override
    public String getKeeperName() {
        return "EMPTY";
    }

    @Override
    public void keep(@Nonnull String content) {
        LOGGER.info("empty keep done!, content={}", content);
    }
}
