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
