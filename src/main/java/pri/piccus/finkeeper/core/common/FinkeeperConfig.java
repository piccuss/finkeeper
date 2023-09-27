package pri.piccus.finkeeper.core.common;

import com.google.common.collect.ImmutableSet;
import jakarta.annotation.Nonnull;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public final class FinkeeperConfig {

    @Nonnull
    private final ImmutableSet<String> stockCodes;

    @Nonnull
    private final String stockDataSource;

    @Nonnull
    private final String receiver;

    private FinkeeperConfig(@Nonnull ImmutableSet<String> stockCodes, @Nonnull String stockDataSource, @Nonnull String receiver) {
        this.stockCodes = stockCodes;
        this.stockDataSource = stockDataSource;
        this.receiver = receiver;
    }

    @Nonnull
    public ImmutableSet<String> getStockCodes() {
        return stockCodes;
    }

    @Nonnull
    public String getStockDataSource() {
        return stockDataSource;
    }

    @Nonnull
    public String getReceiver() {
        return receiver;
    }

    public static class Builder {
        private final Set<String> stockCodes = new HashSet<>();

        private String stockDataSource = "";

        private String receiver = "";

        public static Builder create() {
            return new Builder();
        }

        public Builder addStockCode(String stockCode) {
            Objects.requireNonNull(stockCode, "config stockCode must not be null");
            this.stockCodes.add(stockCode);
            return this;
        }

        public Builder addStockCodes(List<String> stockCodes) {
            Objects.requireNonNull(stockCodes, "config stockCode must not be null");
            this.stockCodes.addAll(stockCodes);
            return this;
        }

        public Builder setStockDataSource(String stockDataSource) {
            Objects.requireNonNull(stockDataSource, "config stockDataSource must not be null");
            this.stockDataSource = stockDataSource;
            return this;
        }

        public Builder setReceiver(String receiver) {
            Objects.requireNonNull(receiver, "config receiver must not be null");
            this.receiver = receiver;
            return this;
        }

        public FinkeeperConfig build() {
            return new FinkeeperConfig(ImmutableSet.copyOf(this.stockCodes), this.stockDataSource, this.receiver);
        }
    }

    @Override
    public String toString() {
        return "FinkeeperConfig{" +
                "stockCodes=" + stockCodes +
                ", stockDataSource='" + stockDataSource + '\'' +
                ", receiver='" + receiver + '\'' +
                '}';
    }
}
