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
        private String service;

        public Endpoint() {
        }

        public Endpoint(String service) {
            this.service = service;
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

        public String getService() {
            return service;
        }

        public void setService(String service) {
            this.service = service;
        }
    }

    public List<Endpoint> getEndpoints() {
        return endpoints;
    }

    public void setEndpoints(List<Endpoint> endpoints) {
        this.endpoints = endpoints;
    }
}
