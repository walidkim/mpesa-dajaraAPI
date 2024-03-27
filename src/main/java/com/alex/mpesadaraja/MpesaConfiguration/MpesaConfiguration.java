package com.alex.mpesadaraja.MpesaConfiguration;

import lombok.Data;
import okhttp3.OkHttpClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "mpesa.daraja")
public class MpesaConfiguration {
    private String consumerKey;
    private String consumerSecret;
    private String grantType;
    private String oauthEndpoint;

    public String getOauthEndpoint() {
        return oauthEndpoint;
    }

    @Override
    public String toString() {
        return String.format("{consumerKey='%s', consumerSecret='%s', grantType='%s', oauthEndpoint='%s'}",
                consumerKey, consumerSecret, grantType, oauthEndpoint);
    }
    @Configuration

    public  class OkHttpClientConfiguration{
    @Bean
    public OkHttpClient okHttpClient(){
        return new OkHttpClient();
    }

    }
}

