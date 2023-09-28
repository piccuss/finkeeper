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

package pri.piccus.finkeeper.boot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pri.piccus.finkeeper.core.Finkeeper;

@Component
public class CronScheduleRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(CronScheduleRunner.class);

    @Autowired
    final Finkeeper finkeeper;

    public CronScheduleRunner(Finkeeper finkeeper) {
        this.finkeeper = finkeeper;
    }

    @Scheduled(cron = "${boot.cron.pattern}")
    public void run() {
        LOGGER.info("It's show time.");
        try {
            finkeeper.keep();
        } catch (Exception e) {
            LOGGER.error("show is break.cause=", e);
        }
    }

}
