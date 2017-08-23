package pl.mmorpg.prototype;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringApplicationContext implements ApplicationContextAware
{
	private static ApplicationContext context;

	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException
	{
		this.context = context;
	}

	public static Object getBean(String beanName)
	{
		return context.getBean(beanName);
	}
}
