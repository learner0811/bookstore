package ptit.bookstore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import ptit.bookstore.intercepter.EncodingIntercepter;

@EnableWebMvc
@Configuration
@ComponentScan({ "ptit.bookstore" })
public class WebConfig extends WebMvcConfigurerAdapter {
	@Bean
	public InternalResourceViewResolver viewResolve() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");
		
		return viewResolver;
	}
	
	@Bean(name="multipartResolver")
	public CommonsMultipartResolver multipartResolver(){
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
		commonsMultipartResolver.setMaxUploadSize(268435456);
		return commonsMultipartResolver;
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) { 
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}		
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new EncodingIntercepter());		
	}		
}
