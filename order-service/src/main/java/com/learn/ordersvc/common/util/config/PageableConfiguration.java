package com.learn.ordersvc.common.util.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.PageableHandlerMethodArgumentResolverCustomizer;
import org.springframework.data.web.config.SortHandlerMethodArgumentResolverCustomizer;

/**
 * Class that implements the necessary settings to the pagination feature works correctly.
 *
 * @author Mariana Azevedo
 * @since 20/09/2020
 */
@Configuration
public class PageableConfiguration {

    /**
     * Method that allow customize Pageable configurations.
     *
     * @return <code>PageableHandlerMethodArgumentResolverCustomizer</code> object
     * @author Mariana Azevedo
     * @since 20/09/2020
     */
    @Bean
    PageableHandlerMethodArgumentResolverCustomizer pageableResolverCustomizer() {
        return pageableResolver -> pageableResolver.setOneIndexedParameters(true);
    }

    /**
     * Method that allow customize Sort configurations.
     *
     * @return <code>SortHandlerMethodArgumentResolverCustomizer</code> object
     * @author Mariana Azevedo
     * @since 20/09/2020
     */
    @Bean
    SortHandlerMethodArgumentResolverCustomizer sortResolverCustomizer() {
        return sortResolver -> sortResolver.setSortParameter("sort");
    }
}