package ptit.bookstore.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class ConfigInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {		
		return new Class[] {WebConfig.class, DbConfig.class};
	}

	@Override
	protected String[] getServletMappings() {		
		return new String[] {"/"};
	}
		
}
