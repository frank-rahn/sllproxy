/*
 * Copyright (c) 2019-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package de.rahn.net.proxy.tlsproxy.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.server.WebFilter;

@Configuration
class RequestFilterConfiguration {

  private static final Logger LOGGER = LoggerFactory.getLogger(RequestFilterConfiguration.class);

  @Bean
  WebFilter commonsRequestLoggingFilter() {
    return (exchange, chain) ->
        chain
            .filter(exchange)
            .doFinally(
                signalType -> {
                  LOGGER.debug("Request-SSL-Info={}", exchange.getRequest().getSslInfo());
                  LOGGER.debug("Request-Headers={}", exchange.getRequest().getHeaders());
                });
  }

  //  /**
  //   * Ändere das HTTP-Attribute <code>Location</code>, wenn der Service einen 3XX HTTP Statuscode
  //   * liefert.
  //   */
  //  @Bean
  //  LocationRewriteFilter locationRewriteFilter() {
  //    return new LocationRewriteFilter();
  //  }
}
