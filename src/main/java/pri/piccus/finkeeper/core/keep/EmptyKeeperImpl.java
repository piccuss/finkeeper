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
