package pri.piccus.finkeeper.core.keep;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.annotation.PostConstruct;

import java.util.HashMap;
import java.util.Map;

public interface Keeper {

    @Nonnull
    String getKeeperName();

    void keep(@Nonnull String content);

    @PostConstruct
    default void register() {
        NotifierRegistry.register(getKeeperName(), this);
    }

    class NotifierRegistry {
        private static final Map<String, Keeper> KEEPER_MAP = new HashMap<>();

        private static void register(@Nonnull String notifierName, @Nonnull Keeper notifier) {
            KEEPER_MAP.put(notifierName, notifier);
        }
    }

    @Nullable
    static Keeper getInstance(@Nonnull String keeperName) {
        return Keeper.NotifierRegistry.KEEPER_MAP.get(keeperName);
    }
}
