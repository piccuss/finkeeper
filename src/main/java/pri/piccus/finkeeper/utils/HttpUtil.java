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

package pri.piccus.finkeeper.utils;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.zip.GZIPInputStream;

public interface HttpUtil {

    Logger LOGGER = LoggerFactory.getLogger(HttpUtil.class);

    String ALL_TYPE = "*/*";

    String JSON_TYPE = "application/json";

    int SUCCESS_CODE = 200;

    @Nullable
    static String get(@Nonnull String uri) {
        var start = System.currentTimeMillis();
        try (var client = HttpClient.newHttpClient()) {
            var request = HttpRequest.newBuilder()
                    .uri(URI.create(uri))
                    .timeout(Duration.ofMinutes(2))
                    .header("Accept-Encoding", "gzip,deflate")
                    .header("Content-Type", ALL_TYPE)
                    .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/117.0.0.0 Safari/537.36 Edg/117.0.2045.43")
                    .build();
            var resp = client.send(request, HttpResponse.BodyHandlers.ofInputStream());
            var statusCode = resp.statusCode();
            if (SUCCESS_CODE != statusCode) {
                LOGGER.error("get request fail.status code={}", statusCode);
                return null;
            }
            var respStr = CharStreams.toString(new InputStreamReader(getDecodedInputStream(resp), Charsets.UTF_8));
            LOGGER.debug("get request, uri={}, resp={}", uri, respStr);
            return respStr;
        } catch (Exception e) {
            LOGGER.error("get request fail, uri={}", uri, e);
        } finally {
            LOGGER.info("get request time cost={}ms, uri={}", System.currentTimeMillis() - start, uri);
        }
        return null;
    }

    private static InputStream getDecodedInputStream(
            HttpResponse<InputStream> httpResponse) {
        String encoding = determineContentEncoding(httpResponse);
        try {
            return switch (encoding) {
                case "" -> httpResponse.body();
                case "gzip" -> new GZIPInputStream(httpResponse.body());
                default -> throw new UnsupportedOperationException(
                        "Unexpected Content-Encoding: " + encoding);
            };
        } catch (IOException ioe) {
            throw new UncheckedIOException(ioe);
        }
    }

    private static String determineContentEncoding(
            HttpResponse<?> httpResponse) {
        return httpResponse.headers().firstValue("Content-Encoding").orElse("");
    }
}
