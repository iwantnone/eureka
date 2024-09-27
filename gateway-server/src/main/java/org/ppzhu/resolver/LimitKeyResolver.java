package org.ppzhu.resolver;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @Author: ppzhu
 * @Date: 2024/9/27
 * @Description: Describe the purpose or functionality of the class here.
 * You can provide detailed explanations, usage examples, etc.
 * @Modification History:
 * Date         Author          Description
 * -----------  --------------  -----------------------------------
 * 2024/9/27      ppzhu         Initial version
 * @Copyright: Copyright 2024, ppzhu.
 * All rights reserved.
 */
@Component
public class LimitKeyResolver implements KeyResolver {

    @Override
    public Mono<String> resolve(ServerWebExchange exchange) {
        String ip = exchange.getRequest()
                .getRemoteAddress()
                .getAddress()
                .getHostAddress();

        return Mono.just(ip);
    }
}
