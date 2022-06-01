
package github.leavesczy.asm.plugins.okhttp;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.util.Properties;
import java.util.function.BiConsumer;
import java.util.logging.Logger;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

/**
 * The profiler transform added by Studio. This transform can read input configuration arguments
 * from a property file stored at:
 *
 * <p>System.getProperty("android.profiler.properties").
 */
@SuppressWarnings("unused")
public final class ProfilerTransform implements BiConsumer<InputStream, OutputStream> {

    private static final Properties PROPERTIES = loadTransformProperties();
    private static final boolean OKHTTP_PROFILING_ENABLED =
            "true".equals(PROPERTIES.getProperty("android.profiler.okhttp.enabled"));

    private static Logger getLog() {
        return Logger.getLogger(ProfilerTransform.class.getName());
    }

    @Override
    public void accept(InputStream in, OutputStream out) {
        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        ClassVisitor visitor = writer;
        visitor = new InitializerAdapter(visitor);
        visitor = new HttpURLAdapter(visitor);
        if (OKHTTP_PROFILING_ENABLED) {
            visitor = new OkHttpAdapter(visitor);
        }

        try {
            ClassReader cr = new ClassReader(in);
            cr.accept(visitor, 0);
            out.write(writer.toByteArray());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private static Properties loadTransformProperties() {
        Properties properties = new Properties();
        String propertiesFile = System.getProperty("android.profiler.properties");
        if (propertiesFile != null && !propertiesFile.trim().isEmpty()) {
            try (InputStream inputStream = new FileInputStream(propertiesFile)) {
                properties.load(inputStream);
            } catch (FileNotFoundException e) {
                getLog().warning("Profiler properties file cannot be found.");
            } catch (IOException e) {
                getLog().warning("Profiler properties file is not read properly.");
            }
        }
        return properties;
    }
}