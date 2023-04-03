package com.example.muuniversitybot.configuration;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

@Slf4j
@Configuration
@AllArgsConstructor
public class RestTemplateConfig {
    @Bean("ucell-rest-template")
    public RestTemplate restTemplate() {
        log.debug("create httpClient");
        var requestFactory = disableSSl();
        return new RestTemplate(requestFactory);
    }
    private HttpComponentsClientHttpRequestFactory disableSSl() {
        TrustStrategy acceptingTrustStrategy = (x509Certificates, s) -> true;

        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setConnectTimeout(3000);
        log.debug("ucell-rest-template connectionTimeout: {}", 3000);
        requestFactory.setReadTimeout(3000);
        log.debug("ucell-rest-template readTimeout: {}", 3000);

        SSLContext sslContext = null;
        try {
            sslContext = org.apache.http.ssl.SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
        } catch (NoSuchAlgorithmException | KeyManagementException | KeyStoreException e) {
            log.warn("Ssl context is not valid");
        }

        if (sslContext != null) {
            SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext, new NoopHostnameVerifier());
            CloseableHttpClient httpClient = HttpClients.custom()
                    .setSSLSocketFactory(csf)
                    .build();

            log.warn("ucell-rest-template disabled ssl!!!");
            requestFactory.setHttpClient(httpClient);
        }
        return requestFactory;
    }
}
