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
