package pl.mmorpg.prototype.server.resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.jar.Attributes;
import java.util.jar.Manifest;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.ArrayUtils;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.util.ConfigurationBuilder;

import com.google.common.base.Predicate;

public class ReflectionResourceUtils
{
	public static Set<String> getClasspathResources(Set<String> filesExtensions, Predicate<String> inputsFilter)
	{
		Collection<URL> classpathJars = getUsedJars();
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

	private static Collection<URL> getUsedJars()
	{
		String[] classpathJars = getClasspathJars();
		String[] manifestJars = getManifestJars();
		return Stream.concat(getUsedJars(classpathJars), getUsedJars(manifestJars))
				.collect(Collectors.toList());
	}

	private static String[] getClasspathJars()
	{
		String property = System.getProperty("java.class.path");
		System.out.println("Classpath: " + property);
		String[] classpathJars = property.split(";");
		return classpathJars;
	}

	private static Stream<URL> getUsedJars(String[] classpathJars)
	{
		String prefix = runJarLocation();
		System.out.println("Run jar location: " + prefix);
		return Arrays.stream(classpathJars)
				.map(jar -> toURL(prefix, jar));
	}

	private static String runJarLocation()
	{
		try
		{
			return new File(Assets.class.getProtectionDomain().getCodeSource().getLocation().toURI().getRawPath())
					.getParentFile().getParent();
		} catch (URISyntaxException e)
		{
			throw new RuntimeException(e);
		}
	}

	private static String[] getManifestJars()
	{
		try(InputStream in = ReflectionResourceUtils.class.getResourceAsStream("/META-INF/MANIFEST.MF"))
		{	
			String[] manifestJars = readManifestJars(in);
			if(manifestJars.length == 0)
				return tryReadingFromRootManifest();
			return manifestJars;
		} catch (IOException e)
		{
			return tryReadingFromRootManifest();
		}
	}
	
	private static String[] readManifestJars(InputStream in) throws IOException
	{
		Manifest manifest = new Manifest(in);
		Attributes attributes = manifest.getMainAttributes();
		String classpath = attributes.getValue(Attributes.Name.CLASS_PATH);
		if(classpath == null)
			return ArrayUtils.EMPTY_STRING_ARRAY;
		System.out.println("Manifest jars: " + classpath);
		return classpath.split(" ");
	}

	// Workaround for openJDK where MANIFEST.MF cannot be read from jar file
	private static String[] tryReadingFromRootManifest()
	{
		try(InputStream in = new FileInputStream("META-INF/MANIFEST.MF"))
		{
			return readManifestJars(in);
		} catch (IOException e)
		{
			e.printStackTrace();
			return ArrayUtils.EMPTY_STRING_ARRAY;
		}
	}

	private static URL toURL(String pathPrefix, String jar)
	{
		try
		{
			String path = jar.trim().replace(" ", "%20");
			if (!new File(path).isAbsolute())
				return new URL("file:" + pathPrefix + '/' + path);

			return new URL("file:" + path);
		} catch (MalformedURLException e)
		{
			throw new RuntimeException(e);
		}
	}
}
