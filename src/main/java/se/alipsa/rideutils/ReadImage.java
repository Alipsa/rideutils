package se.alipsa.rideutils;

import de.codecentric.centerdevice.javafxsvg.SvgImageLoaderFactory;
import javafx.scene.image.Image;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ReadImage {

    static {
        SvgImageLoaderFactory.install();
    }


    public static Image read(String name) {
        URL url = getResourceUrl(name);
        System.out.println("Reading image from " + url);
        return new Image(url.toExternalForm());
        //return url.toExternalForm();
    }


    /**
     * Find a resource using available class loaders.
     * It will also load resources/files from the
     * absolute path of the file system (not only the classpath's).
     *
     * @param resource the path to the resource
     * @return the URL representation of the resource
     */
    public static URL getResourceUrl(String resource) {
        final List<ClassLoader> classLoaders = new ArrayList<ClassLoader>();
        classLoaders.add(Thread.currentThread().getContextClassLoader());
        classLoaders.add(ReadImage.class.getClassLoader());

        for (ClassLoader classLoader : classLoaders) {
            final URL url = getResourceWith(classLoader, resource);
            if (url != null) {
                return url;
            }
        }

        final URL systemResource = ClassLoader.getSystemResource(resource);
        if (systemResource != null) {
            return systemResource;
        } else {
            try {
                return new File(resource).toURI().toURL();
            } catch (MalformedURLException e) {
                return null;
            }
        }
    }

    private static URL getResourceWith(ClassLoader classLoader, String resource) {
        if (classLoader != null) {
            return classLoader.getResource(resource);
        }
        return null;
    }
}
