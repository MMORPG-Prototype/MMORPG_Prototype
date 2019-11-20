package pl.mmorpg.prototype.clientservercommon.registering;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import org.reflections.Reflections;

import com.esotericsoftware.kryo.Kryo;

public class PacketHandlersRegisterer
{
    private final static Class<? extends Annotation> defaultAnnotationType = Registerable.class;
    private final static Class<? extends Annotation> defaultAnnotationBase = RegisterableBase.class;

    public static Kryo registerPackets(Kryo destination)
    {
    	destination = registerAllAnnotated(destination);
    	destination = registerDefaults(destination);
    	return destination;
    }
    
    public static Kryo registerAllAnnotated(Kryo destination)
    {
        destination = registerAllAnnotated(destination, defaultAnnotationType);
        return destination;
    }

    private static String getUsedPackageName()
    {
		return "pl.mmorpg.prototype.clientservercommon.packets";
    }

    public static Kryo registerAllAnnotated(Kryo destination, Class<? extends Annotation> annotationType)
    {
		Reflections reflections = new Reflections(getUsedPackageName());
        Set<Class<?>> registerableTypes = reflections.getTypesAnnotatedWith(annotationType);
        destination = registerCollection(destination, registerableTypes);
        Set<Class<?>> registerableBaseTypes = reflections.getTypesAnnotatedWith(defaultAnnotationBase);
        destination = registerSubTypesOf(destination, registerableBaseTypes);
        return destination;
    }

    private static Kryo registerSubTypesOf(Kryo destination, Set<Class<?>> registerableBaseTypes)
    {
        for (Class<?> baseType : registerableBaseTypes)
            destination = registerAllSubtypes(destination, baseType);
        return destination;
    }

    private static Kryo registerCollection(Kryo destination, Set<Class<?>> types)
    {
        List<Class<?>> sorted = sort(types);
        for (Class<?> registerableType : sorted)
            destination = registerType(destination, registerableType);

        return destination;
    }

    private static List<Class<?>> sort(Set<Class<?>> registerableTypes)
    {
        List<Class<?>> sorted = new ArrayList<Class<?>>(registerableTypes);
        Collections.sort(sorted, new Comparator<Class<?>>()
        {
            public int compare(Class<?> o1, Class<?> o2)
            {
                String name1 = o1.getSimpleName();
                String name2 = o2.getSimpleName();
                return name1.compareTo(name2);
            }
        });
        return sorted;
    }

    private static Kryo registerType(Kryo destination, Class<?> registerableType)
    {
        destination.register(registerableType);
        destination = registerArrayType(destination, registerableType);
        return destination;
    }

    private static Kryo registerArrayType(Kryo destination, Class<?> registerableType)
    {
        Class<?> arrayClass = Array.newInstance(registerableType, 0).getClass();
        destination.register(arrayClass);
        return destination;
    }

    public static <T> Kryo registerAllSubtypes(Kryo destination, Class<T> baseType)
    {
        String packageStr = getUsedPackageName();
        destination = registerAllSubtypes(destination, baseType, packageStr);
        return destination;
    }

    public static <T> Kryo registerAllSubtypes(Kryo destination, Class<T> baseType, String sourcePackageName)
    {
        Reflections reflections = new Reflections(sourcePackageName);
        @SuppressWarnings("unchecked")
        Set<Class<?>> subtypes = (Set<Class<?>>) (Set<?>) reflections.getSubTypesOf(baseType);
        registerCollection(destination, subtypes);
        return destination;
    }
    
    public static Kryo registerDefaults(Kryo destination)
    {
    	destination.register(String[].class);
		return destination;
    }
}
