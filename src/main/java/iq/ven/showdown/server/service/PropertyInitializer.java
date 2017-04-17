package iq.ven.showdown.server.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;


/**
 * Created by User on 21.03.2017.
 */
@Configuration
@PropertySource("classpath:properties/serverconfig.properties")
public class PropertyInitializer {


    @Value("${port}")
    private int port;
    @Value("${server}")
    private String server;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean(name = "serverInfoBean")
    public PropertyInitializer getServerInfo() {
        return new PropertyInitializer();
    }
}
