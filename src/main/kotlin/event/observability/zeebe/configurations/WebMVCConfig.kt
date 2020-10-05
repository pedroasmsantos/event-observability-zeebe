package event.observability.zeebe.configurations

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebMVCConfig : WebMvcConfigurer{

    override fun addViewControllers(registry: ViewControllerRegistry) {
        registry.addViewController("/").setViewName("index")
        registry.addViewController("/hello").setViewName("hello")
        registry.addViewController("/login").setViewName("login")
    }

}