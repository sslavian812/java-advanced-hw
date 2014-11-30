package ru.ifmo.ctddev.shalamov.task3;

import javax.tools.*;
import java.io.*;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Locale;
import java.util.jar.Attributes;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Compilation's and jar-generation's provider.
 * Used an class-helper in Implementor for compilation and jar-file generation at runtime.
 *
 * @author Viacheslav Shalamov
 * @version 1.3
 */
public class RuntimeOps {

    /**
     * A String value of "*.java"
     */
    private static final String DOT_JAVA = ".java";
    /**
     * A String value of "*"
     *
     * @since 1.3
     */
    private static final String STAR = "*";

    /**
     * Compiles all the files in given directory
     *
     * @param path - a {@link java.lang.String} representing a path to files to be compiled
     */
    public static void compile(String path) throws IOException {
        File dir = new File(path);
        File[] javaFiles = dir.listFiles(
                new FilenameFilter() {
                    public boolean accept(File file, String name) {
                        return name.endsWith(DOT_JAVA);
                    }
                });

        JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();

        String[] compilationUnits = new String[javaFiles.length];
        for (int i = 0; i < javaFiles.length; ++i) {
            compilationUnits[i] = javaFiles[i].getPath();
        }

        int exitCode = javaCompiler.run(null, null, null, compilationUnits);

        if (exitCode != 0) {
            System.out.println("some compile errors occurred!");
        }
    }

    /**
     * Compiles a specified file
     *
     * @param pathToFile - a {@link java.lang.String} representing a path to file to be compiled
     */
    public static void compileFile(String pathToFile) throws IOException {
        JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();

        String[] compilationUnits = {pathToFile};

        int exitCode = -1;
        try {
            exitCode = javaCompiler.run(null, null, null, compilationUnits);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        if (exitCode != 0) {
            System.out.println("some compile errors occurred!: exitcode: " + exitCode);
            System.out.println(compilationUnits[0] + " vs " + pathToFile);
        }
    }

    /**
     * creates specified jar file and adds the generated implementation to it.
     *
     * @param pathToJar   - a {@link java.lang.String} representing a path to .jar file to be created.
     * @param pathToClass - a path to compiled file (file.class) to de added to jar
     */
    public static void createJar(String pathToJar, String pathToClass) throws IOException {

        FileOutputStream fos = null;
        JarOutputStream jarOutputStream = null;
        try {
            File f = new File(pathToJar);

            if(f.getParentFile()!= null)
                f.getParentFile().mkdirs();

            if(f!= null)
                f.createNewFile();
            else
            {
                System.out.println("invalid path to jar file to be created");
                System.exit(1);
            }

            fos = new FileOutputStream(pathToJar);

            String newClassPath = pathToClass.substring(pathToClass.indexOf("tmp/") + 4);

            Manifest manifest = new Manifest();
            manifest.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0");

            //manifest.getMainAttributes().put(Attributes.Name.MAIN_CLASS, newClassPath);
            jarOutputStream = new JarOutputStream(fos, manifest);

            jarOutputStream.putNextEntry(new ZipEntry(newClassPath));

            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(pathToClass));

            int bytesRead;
            byte[] buffer = new byte[8 * 1024];
            while ((bytesRead = bis.read(buffer)) != -1) {
                jarOutputStream.write(buffer, 0, bytesRead);
            }
            jarOutputStream.closeEntry();
            jarOutputStream.close();
            fos.close();

        } catch (IOException e) {

            jarOutputStream.closeEntry();
            jarOutputStream.close();
            fos.close();
            throw new IOException(e.getMessage());
        }
    }

    /**
     * compiles the specified implementaion and creates a <tt>.jar</tt> file for it.
     *
     * @param rootPath - a {@link java.lang.String} specifying a root directory
     * @param clazz    - type token
     */
    public static void doFromConsole(String rootPath, Class clazz) throws IOException {
        String s = rootPath + "\\" + clazz.getPackage().getName().replaceAll("\\.", "/") + "\\" + STAR + DOT_JAVA;
        Runtime.getRuntime().exec("javac  " + s);
        String s2 = clazz.getPackage().getName() + " " + rootPath + "/" + clazz.getCanonicalName().toString() + " " + rootPath;
        Runtime.getRuntime().exec("jar cfe " + s2);
    }
}
