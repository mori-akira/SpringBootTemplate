package jp.co.molygray;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * SpringBoot用のServletInitializer
 *
 * @author Moriaki Kogure
 * @version 0.0.1
 */
public class ServletInitializer extends SpringBootServletInitializer {

    /**
     * {@inheritDoc}
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(App.class);
    }
}
