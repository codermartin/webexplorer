package top.codermartin.webexplorer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Component
@Configuration
public class ProjectConfig extends WebMvcConfigurerAdapter {
	@Value("#{project.static_path}")
	static public String static_path;
	
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

//            registry.addResourceHandler(static_path);
//            System.out.println("!!!!!!!!!!!!!!!static_path "+static_path);
        super.addResourceHandlers(registry);
    }
}