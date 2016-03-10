package net.chrisrichardson.eventstore.javaexamples.banking.apigateway;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by popikyardo on 15.01.16.
 */
@ConfigurationProperties(prefix = "api.gateway")
public class ApiGatewayProperties {

    private List<Endpoint> endpoints;

    public static class Endpoint {
        private String path;
        private RequestMethod method;
        private String host;
        private int port;

        public Endpoint() {
        }

        public Endpoint(String host, int port) {
            this.host = host;
            this.port = port;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public RequestMethod getMethod() {
            return method;
        }

        public void setMethod(RequestMethod method) {
            this.method = method;
        }

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }
    }

    public List<Endpoint> getEndpoints() {
        return endpoints;
    }

    public void setEndpoints(List<Endpoint> endpoints) {
        this.endpoints = endpoints;
    }
}
