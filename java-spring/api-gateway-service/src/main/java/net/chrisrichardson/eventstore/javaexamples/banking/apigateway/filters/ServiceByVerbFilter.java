package net.chrisrichardson.eventstore.javaexamples.banking.apigateway.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import net.chrisrichardson.eventstore.javaexamples.banking.apigateway.ApiGatewayProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.ProxyRouteLocator;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;
import org.springframework.web.util.UrlPathHelper;

/**
 * Created by popikyardo on 06.03.16.
 */
public class ServiceByVerbFilter extends ZuulFilter {

    @Autowired
    private ApiGatewayProperties apiGatewayProperties;

    private UrlPathHelper urlPathHelper = new UrlPathHelper();
    private PathMatcher pathMatcher = new AntPathMatcher();

    @Override
    public String filterType() {
        return "route";
    }

    @Override
    public int filterOrder() {
        return 9;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        final String requestURI = this.urlPathHelper.getPathWithinApplication(ctx
                .getRequest());

        ApiGatewayProperties.Endpoint endpoint = apiGatewayProperties.getEndpoints().stream()
                    .filter(e ->
                            pathMatcher.match(e.getPath(), requestURI) && e.getMethod() == RequestMethod.valueOf(ctx.getRequest().getMethod())
                    )
                    .findFirst().orElseThrow(() -> new RuntimeException( new NoSuchRequestHandlingMethodException(ctx.getRequest())));
        ctx.setRouteHost(null);
        ctx.set("serviceId", endpoint.getService());
        ctx.setSendZuulResponse(true);
        return null;
    }

}