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
package de.rahn.net.proxy.sllproxy.config;

import static java.util.stream.Collectors.joining;

import java.security.cert.X509Certificate;
import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.AbstractRequestLoggingFilter;

@Configuration
class RequestFilterConfiguration {

  @Bean
  AbstractRequestLoggingFilter commonsRequestLoggingFilter() {
    AbstractRequestLoggingFilter loggingFilter =
        new AbstractRequestLoggingFilter() {
          @Override
          protected boolean shouldLog(HttpServletRequest request) {
            return logger.isDebugEnabled();
          }

          @Override
          protected void beforeRequest(HttpServletRequest request, String message) {
            logger.debug(message);

            X509Certificate[] x509Certificates =
                (X509Certificate[]) request.getAttribute("javax.servlet.request.X509Certificate");

            if (x509Certificates != null) {
              String certificates =
                  Arrays.stream(x509Certificates)
                      .map(certificate -> " " + certificate.toString())
                      .collect(joining("\n", "X509Certificates [\n", "]"));
              logger.debug(certificates);
            }
          }

          @Override
          protected void afterRequest(HttpServletRequest request, String message) {
            logger.debug(message);
          }
        };

    loggingFilter.setIncludeQueryString(false);
    loggingFilter.setIncludeClientInfo(true);
    loggingFilter.setIncludeHeaders(true);
    loggingFilter.setIncludePayload(false);

    return loggingFilter;
  }
}
