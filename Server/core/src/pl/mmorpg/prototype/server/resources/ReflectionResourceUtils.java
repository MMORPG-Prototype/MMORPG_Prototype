package pl.mmorpg.prototype.server.resources;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.jar.Attributes;
import java.util.jar.Manifest;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.util.ConfigurationBuilder;

import com.google.common.base.Predicate;

public class ReflectionResourceUtils
{
	public static Set<String> getClasspathResources(Set<String> filesExtensions, Predicate<String> inputsFilter)
	{
		Collection<URL> classpathJars = classpathJars();
		System.out.println(classpathJars.size());
		classpathJars.forEach(System.out::println);
		ConfigurationBuilder configuration = new ConfigurationBuilder().setUrls(classpathJars)
				.setScanners(new ResourcesScanner());
		configuration.setInputsFilter(inputsFilter);
		Reflections reflections = new Reflections(configuration);
		Set<String> resources = reflections.getResources(getResourcePattern(filesExtensions));
		return resources;
	}
	
	private static Pattern getResourcePattern(Set<String> filesExtensions)
	{
		return Pattern.compile(".+\\." + getCombinedExtensions(filesExtensions));
	}
	
	private static String getCombinedExtensions(Set<String> filesExtensions)
	{
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append('(');
		String joinedExtensions = filesExtensions.stream().collect(Collectors.joining("|"));
		strBuilder.append(joinedExtensions);
		strBuilder.append(')');
		return strBuilder.toString();
	}

	private static Collection<URL> classpathJars()
	{
		String property = System.getProperty("java.class.path");
		System.out.println(property);
		String[] classpathJars = property.split(";");
		Collection<URL> classpathJarUrls = classpathJars(classpathJars);
		return Stream.concat(classpathJarUrls.stream(), manifestJars().stream()).collect(Collectors.toList());
	}

	private static Collection<URL> classpathJars(String[] classpathJars)
	{
		String prefix = runJarLocation();
		List<URL> result = Arrays.stream(classpathJars)
				.map(jar -> toURL(prefix, jar))
				.collect(Collectors.toList());
		return result;
	}

	private static String runJarLocation()
	{
		try
		{
			return new File(Assets.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath())
					.getParentFile().getParent();
		} catch (URISyntaxException e)
		{
			throw new RuntimeException(e);
		}
	}

	private static Collection<URL> manifestJars()
	{
		ClassLoader cl = Assets.class.getClassLoader();

		try
		{
			try (InputStream manifestStream = cl.getResourceAsStream("META-INF/MANIFEST.MF"))
			{
				Manifest manifest = new Manifest(manifestStream);
				Attributes attributes = manifest.getMainAttributes();
				String classpath = attributes.getValue(Attributes.Name.CLASS_PATH);
				if (classpath == null)
					return Collections.emptyList();
				System.out.println(classpath);
				String[] classpathJars = classpath.split(" ");
				return classpathJars(classpathJars);
			}
		} catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	private static URL toURL(String pathPrefix, String jar)
	{
		try
		{
			String path = jar.trim().replace(" ", "%20");
			if (!new File(path).isAbsolute())
				return new URL("file:\\" + pathPrefix + '\\' + path);

			return new URL("file:\\" + path);
		} catch (MalformedURLException e)
		{
			throw new RuntimeException(e);
		}
	}
}
