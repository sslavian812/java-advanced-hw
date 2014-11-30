package ru.ifmo.ctddev.shalamov.task3;

import info.kgeorgiy.java.advanced.implementor.Impler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

import info.kgeorgiy.java.advanced.implementor.*;

/**
 * The implementation of interface Impler
 *
 * @author Viacheslav Shalamov
 * @version 1.3
 */
public class Implementor implements Impler, JarImpler {

    /**
     * A String value for UI operations.
     */
    private static final String IMPL = "Impl";
    /**
     * A String value representing line separator of current OS
     */
    private static final String LS = System.lineSeparator();
    /**
     * A String value for UI operations.
     */
    private static final String NOT_FOUND = "class not found: ";
    /**
     * A String value for UI operations.
     */
    private static final String IO_ERR = "io error";
    /**
     * A String value for UI operations.
     */
    private static final String IMPL_ERR = "implementation error!";
    /**
     * A String value for UI operations.
     */
    private static final String ARGS = "invalid arguments";
    /**
     * A String value for file "*.java" generating
     */
    private static final String DOT_JAVA = ".java";
    /**
     * A String value of "*"
     *
     * @since 1.3
     */
    private static final String STAR = "*";
    /**
     * The package-name of class to be generated
     */
    private static String implPackage;

    /**
     * Used for manual testing and debug.
     * Produces code implementing class or interface specified by provided
     * full class name
     *
     * @param args - the full class or interface name to be implemented,
     *             and the path where  source code should be placed in
     * @since 1.4
     */
    public static void main(String[] args) {
        if (args.length < 2 || args.length > 3) {
            System.out.println(ARGS);
            //printUsage();
            System.exit(1);
        }

        String name;
        String FileName;

        if (args.length == 3) {
            if (!"-jar".equals(args[0])) {
                System.out.println(ARGS);
                //printUsage();
                System.exit(1);
            }

            name = args[1];
            FileName = args[2].substring(0, args[2].indexOf(".jar"));
        } else {
            name = args[0];
            FileName = args[1];
        }

        File root = new File(FileName);
        Class clazz = null;
        try {
            clazz = Class.forName(name);
        } catch (ClassNotFoundException e) {
            System.out.println(NOT_FOUND + name);
            System.exit(1);
        }

        try {
            Implementor i = new Implementor();

            if (args.length == 3) {
                i.implementJar(clazz, new File(args[2]));
            } else {
                i.implement(clazz, root);
            }
        } catch (ImplerException e) {
            System.out.println(IMPL_ERR + " for " + name);
            System.exit(1);
        }
    }

    /**
     * Produces code implementing class or interface specified by provided <tt>token</tt> in specified file.
     * Generated source code implementing <tt>clazz</tt> should be placed in the file specified by <tt>out</tt>.
     *
     * @param clazz type token to create implementation for.
     * @param out   {@link java.io.FileWriter} where the implementation will be placed.
     * @throws {@link java.io.IOException} - if an I/O error occurs
     * @since 1.0
     */
    private static void generateImplementation(Class clazz, Appendable out) throws IOException {
        final String pcg = clazz.getPackage().getName();
        genPackage(pcg, out);
        out.append(LS);
        genImports(clazz, out);
        out.append(LS);
        genDecl(clazz, out);
        genConstructor(clazz, out);
        genMethods(clazz, out);
        out.append("}").append(LS);
    }

    /**
     * Checks, is it possible to generate implementation fo given <tt>token</tt>.
     * {@link info.kgeorgiy.java.advanced.implementor.ImplerException} - if generation is impossible,
     * and does nothing otherwise.
     *
     * @param clazz type token to check the possibility of generating implementation for.
     * @throws {@link info.kgeorgiy.java.advanced.implementor.ImplerException} - if generation is impossible.
     * @since 1.4
     */
    private void checkIfPossible(Class clazz) throws ImplerException {
        boolean flag = true;
        for (Constructor c : clazz.getDeclaredConstructors()) {
            if (!Modifier.isPrivate(c.getModifiers())) {
                flag = false;
            }
        }

        if (clazz.getDeclaredConstructors().length == 0)
            flag = false;


        if (flag || Modifier.isFinal(clazz.getModifiers())) {
            throw new ImplerException();
        }
    }

    public void implement(Class<?> clazz, File root) throws ImplerException {
        checkIfPossible(clazz);

        String Name = clazz.getSimpleName();
        String fileName = null;
        System.out.println("Creating implementation for " + Name);
        implPackage = clazz.getPackage().getName();
        try {
            fileName = root.getAbsolutePath() + "/";
            fileName += clazz.getPackage().getName().replaceAll("\\.", "/");
            fileName += (clazz.getPackage().getName().isEmpty()) ? "" : "/";
            (new File(fileName)).mkdirs();

            fileName += clazz.getSimpleName() + IMPL + DOT_JAVA;

            FileWriter fileWriter = new FileWriter(fileName, true); // true - append mode

            Implementor.generateImplementation(clazz, fileWriter);
            fileWriter.close();

        } catch (FileNotFoundException e) {
            System.out.println("file not found");
            System.exit(1);
        } catch (IOException e) {
            System.out.println(IO_ERR);
            System.exit(1);
        }

        System.out.println("done: " + new File(fileName).getAbsoluteFile());
    }

    public void implementJar(Class<?> token, File jarFile) throws ImplerException {
        checkIfPossible(token);

        String Name = token.getSimpleName();
        String fileName = null;
        System.out.println("Creating implementation and JAR for " + Name);
        implPackage = token.getPackage().getName();
        try {
            fileName = "tmp/";
            fileName += token.getPackage().getName().replaceAll("\\.", "/");
            fileName += (token.getPackage().getName().isEmpty()) ? "" : "/";
            (new File(fileName)).mkdirs();

            fileName += token.getSimpleName() + IMPL + DOT_JAVA;

            FileWriter fileWriter = new FileWriter(fileName, true);

            Implementor.generateImplementation(token, fileWriter);
            fileWriter.close();

            RuntimeOps.compileFile(fileName);
            RuntimeOps.createJar(jarFile.getPath(), fileName.substring(0, fileName.indexOf(".java")) + ".class");

        } catch (FileNotFoundException e) {
            System.out.println("file not found");
            System.exit(1);
        } catch (IOException e) {
            System.out.println(IO_ERR);
            System.exit(1);
        }

        System.out.println("done: " + new File(fileName).getAbsoluteFile());
    }

    /**
     * Appends package name to a file.
     *
     * @param s   the package name
     * @param out a {@link java.io.FileWriter} where to append code
     * @throws {@link java.io.IOException} - if an I/O error occurs
     * @since 1.0
     */
    private static void genPackage(String s, Appendable out) throws IOException {
        out.append("package ").append(s).append(";").append(LS);
    }

    /**
     * Appends imports to a file.
     *
     * @param clazz type token to get imports
     * @param out   a {@link java.io.FileWriter} where to append code
     * @throws {@link java.io.IOException} - if an I/O error occurs
     * @since 1.0
     */
    private static void genImports(Class clazz, Appendable out) throws IOException {
        for (Class c : findUsedClasses(clazz)) {
            out.append("import ").append(c.getCanonicalName()).append(";").append(LS);
        }
    }

    /**
     * Appends class declaration to file
     *
     * @param clazz type token to generate declaration
     * @param out   a {@link java.io.FileWriter} where to append code
     * @throws {@link java.io.IOException} - if an I/O error occurs
     * @since 1.0
     */
    private static void genDecl(Class clazz, Appendable out) throws IOException {
        out.append("public class " + clazz.getSimpleName() + IMPL);
        if (clazz.isInterface())
            out.append(" implements " + clazz.getSimpleName()).append(" {");
        else
            out.append(" extends " + clazz.getSimpleName()).append(" {");
    }

    /**
     * Appends constructor of implementing class to file.
     *
     * @param clazz type token to generate constructor
     * @param out   a {@link java.io.FileWriter} where to append code
     * @throws {@link java.io.IOException} - if an I/O error occurs
     * @since 1.0
     */
    private static void genConstructor(Class clazz, Appendable out) throws IOException {
        Constructor[] c = clazz.getDeclaredConstructors();
        boolean defaultConstuctor = false;
        if (c.length == 0)
            defaultConstuctor = true;
        for (Constructor ctor : c) {
            if (Modifier.isPrivate(ctor.getModifiers()))
                continue;
            if (ctor.getParameterTypes().length == 0)
                defaultConstuctor = true;
        }

        if (!defaultConstuctor) {
            int k = 0;
            while ((Modifier.isPrivate(c[k].getModifiers())))
                ++k;
            Class[] params = c[k].getParameterTypes();
            out.append(LS);
            out.append("    public " + clazz.getSimpleName() + IMPL + "()");
            if (c[k].getExceptionTypes().length != 0) {
                out.append(" throws ");
                Class[] es = c[k].getExceptionTypes();
                for (int i = 0; i < es.length; ++i) {
                    out.append(es[i].getSimpleName());
                    if (i < es.length - 1)
                        out.append(", ");
                }
            }
            out.append("{").append(LS);
            out.append("        super(");
            for (int i = 0; i < params.length; ++i) {
                out.append("(" + params[i].getSimpleName() + ")");
                out.append(getDefault(params[i]));
                if (i < params.length - 1)
                    out.append(", ");
            }
            out.append(");").append(LS);
            out.append("    }");
            out.append(LS);
        }
    }

    /**
     * Appends method's implementation's to file.
     *
     * @param clazz type token to retrieve all methods from it
     * @param out   a {@link java.io.FileWriter} where to append code
     * @throws {@link java.io.IOException} - if an I/O error occurs
     * @since 1.0
     */
    private static void genMethods(Class clazz, Appendable out) throws IOException {
        for (Method m : getMethods(clazz)) {
            int mod = m.getModifiers();
            if (Modifier.isFinal(mod) || Modifier.isNative(mod) || Modifier.isPrivate(mod)
                    || !Modifier.isAbstract(mod)) {
                continue;
            }
            mod ^= Modifier.ABSTRACT;
            if (Modifier.isTransient(mod)) {
                mod ^= Modifier.TRANSIENT;
            }
            out.append(LS);
            if (m.isAnnotationPresent(Override.class)) {
                out.append("    @Override").append(LS);
            }
//            for (Annotation annotation : m.getAnnotations()) {
//                if (annotation instanceof Override)
//                    continue;
//                out.append(annotation.toString()).append(LS);
//            }
            out.append("    ");
            out.append(Modifier.toString(mod));

            out.append(" " + m.getReturnType().getSimpleName() + " ");
            out.append(m.getName() + "(");
            Class[] params = m.getParameterTypes();
            for (int i = 0; i < params.length; ++i) {
                out.append(params[i].getSimpleName() + " " + "arg" + i);
                if (i < params.length - 1)
                    out.append(", ");
            }
            out.append(")");
            Class[] exceptions = m.getExceptionTypes();

            if (exceptions.length != 0) {
                out.append(" throws ");
                for (int i = 0; i < exceptions.length; ++i) {
                    out.append(exceptions[i].getSimpleName());
                    if (i < exceptions.length - 1) {
                        out.append(", ");
                    }
                }
            }

            out.append("{").append(LS).append("        return ");
            out.append(getDefault(m.getReturnType())).append(";").append(LS);
            out.append("    }").append(LS);
        }
    }

    /**
     * Provides all methods from class, it's super classes und interfaces to be overridden
     *
     * @param clazz Class object to retrieve all methods from it
     * @return {@link java.util.List} with Method object's, representing methods to be overridden
     *         in requested implementation
     * @since 1.0
     */
    private static List<Method> getMethods(Class clazz) {
        List<Method> methods = new ArrayList<Method>();
        if (clazz == null)
            return methods;

        methods.addAll(getMethods(clazz.getSuperclass()));

        for (Class interf : clazz.getInterfaces()) {
            methods.addAll(getMethods(interf));
        }

        for (Method m : clazz.getDeclaredMethods()) {

            if (Modifier.isNative(m.getModifiers())
                    || Modifier.isStatic(m.getModifiers()) || m.isSynthetic())
                continue;

            if (Modifier.isPublic(m.getModifiers()) || Modifier.isProtected(m.getModifiers())
                    || (!Modifier.isProtected(m.getModifiers())
                    && !Modifier.isPublic(m.getModifiers())
                    && !Modifier.isPrivate(m.getModifiers())
                    && clazz.getPackage().getName().equals(implPackage))) {
                boolean noAdding = false;
                for (int i = 0; i < methods.size(); ++i) {
                    Method pm = methods.get(i);

                    if (cmp(m, pm))     // compare signatures
                    {
//                        if (!Modifier.isAbstract(m.getModifiers())) {
//                            methods.set(i, m);
//                        }
                        methods.set(i, m);
                        noAdding = true;
                        break;
                    }
                }
                if (!noAdding) {
                    methods.add(m);
                }
            }
        }
        return methods;
    }

    /**
     * Compares two methods by their signatures.
     *
     * @param m1 method to be compared
     * @param m2 method to be compared
     * @return true, if the method's signatures are equal, false - otherwise
     * @since 1.2
     */
    private static boolean cmp(Method m1, Method m2) {
        if (m1.getName().equals(m2.getName())) {
            Class[] args1 = m1.getParameterTypes();
            Class[] args2 = m2.getParameterTypes();
            if (args1.length == args2.length) {
                for (int i = 0; i < args1.length; ++i) {
                    if (!args1[i].getCanonicalName().equals(args2[i].getCanonicalName())) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Provides a type of element by given array(array of arrays)
     *
     * @param arrayType the type of array
     * @return a type of array's element
     * @since 1.0
     */
    private static Class getFromArray(Class arrayType) {
        if (arrayType.getComponentType().isArray()) {
            return getFromArray(arrayType.getComponentType());
        } else {
            return arrayType.getComponentType();
        }
    }

    /**
     * Provide a {@link java.util.Set} of type token representing classes used in methods an constructors.
     * Note: primitives, classes from the same package(the same to the class ot interface to be implemented)
     * and classes from java.lang are excluded from the result set.
     *
     * @param clazz type token where from all the classes will be retrieved.
     * @return a set of classes
     * @since 1.0
     */
    private static Set<Class> findUsedClasses(Class clazz) {
        Set<Class> classes = new HashSet<Class>();
        for (Method method : getMethods(clazz)) {
            for (Class paramType : method.getParameterTypes()) {
                if (paramType.isArray()) {
                    Class cls = getFromArray(paramType);
                    if (!cls.isPrimitive())
                        classes.add(cls);
                } else if (!paramType.isPrimitive()
                        && !paramType.getPackage().getName().startsWith("java.lang")
                        && !paramType.getPackage().getName().equals(clazz.getPackage().getName())
                        ) {
                    classes.add(paramType);
                }
            }

            if (method.getReturnType().isArray()) {
                Class cls = getFromArray(method.getReturnType());
                if (!cls.isPrimitive())
                    classes.add(cls);
            } else if (!method.getReturnType().isPrimitive()
                    && !method.getReturnType().getPackage().getName().startsWith("java.lang")
                    && !method.getReturnType().getPackage().getName().equals(clazz.getPackage().getName())) {
                classes.add(method.getReturnType());
            }

            for (Class e : Arrays.asList(method.getExceptionTypes())) {
                if (e.isArray()) {
                    Class cls = getFromArray(e);
                    if (!cls.isPrimitive())
                        classes.add(cls);
                } else if (!e.isPrimitive()
                        && !e.getPackage().getName().startsWith("java.lang")
                        && !e.getPackage().getName().equals(clazz.getPackage().getName())
                        ) {
                    classes.add(e);
                }
            }

        }

        for (Constructor ctr : Arrays.asList(clazz.getConstructors())) {
            for (Class paramType : ctr.getParameterTypes()) {
                if (paramType.isArray()) {
                    Class cls = getFromArray(paramType);
                    if (!cls.isPrimitive())
                        classes.add(cls);
                } else if (!paramType.isPrimitive()
                        && !paramType.getPackage().getName().startsWith("java.lang")
                        && !paramType.getPackage().getName().equals(clazz.getPackage().getName())
                        ) {
                    classes.add(paramType);
                }
            }

            for (Class e : Arrays.asList(ctr.getExceptionTypes())) {
                if (e.isArray()) {
                    Class cls = getFromArray(e);
                    if (!cls.isPrimitive())
                        classes.add(cls);
                } else if (!e.isPrimitive()
                        && !e.getPackage().getName().startsWith("java.lang")
                        && !e.getPackage().getName().equals(clazz.getPackage().getName())
                        ) {
                    classes.add(e);
                }
            }
        }
        return classes;
    }

    /**
     * Provides a default value corresponding the given type.
     * For boolean provides false, for other primitives - 0,
     * and null - otherwise
     *
     * @param type type token to become default value
     * @return the default value of given type token
     * @since 1.0
     */
    private static String getDefault(Class type) {
        if (type.isPrimitive()) {
            if (Boolean.TYPE.equals(type))
                return "false";
            else if (Void.TYPE.equals(type))
                return "";
            else
                return "0";
        } else
            return "null";
    }

}
