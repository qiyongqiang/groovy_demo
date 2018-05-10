import groovy.lang.Binding;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;
import groovy.lang.GroovyShell;
import groovy.util.GroovyScriptEngine;
import groovy.util.ResourceException;
import groovy.util.ScriptException;
import org.codehaus.groovy.control.CompilationFailedException;
import org.codehaus.groovy.control.CompilerConfiguration;

import java.io.File;
import java.io.IOException;
import java.net.URI;

public class GroovyDemo {
    public static void main(String[] agrs) {
        try {
//            groovyShell执行Groovy片段
//            testGroovy1();
//            groovyShell执行groovy文件
//            testGroovy2();
            //通过GroovyClassLoader加载GroovyShell类
//            initGroovyClassLoader();
//            classLoaderInvokeSayHello("maomao", "male", 20);
            //通过groovyScriptEngine 执行脚本
            testGroovy4();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ResourceException e) {
            e.printStackTrace();
        } catch (ScriptException e) {
            e.printStackTrace();
        }
    }

    /**
     * groovyShell执行Groovy片段
     *
     * @throws CompilationFailedException
     * @throws IOException
     */
    public static void testGroovy1() throws CompilationFailedException, IOException {
        GroovyShell groovyShell = new GroovyShell();
        groovyShell.evaluate("println 'My First Groovy shell.'");
    }

    /**
     * groovyShell执行groovy文件
     *
     * @throws IOException
     */
    public static void testGroovy2() throws IOException {
        Binding binding = new Binding();
        binding.setProperty("name", "maomaodddd");
        GroovyShell groovyShell = new GroovyShell(binding);
        //厉害了,我的哥 😄
//        groovyShell.evaluate(new File("src/main/java/GroovyShell_1_1.groovy"));
        //安全上可以做业务逻辑校验,应该不是问题ing,比如可以参数校验/可以加密 都可以的～，厉害了 赞赞赞,简直是厉害 ing
//        groovyShell.evaluate(URI.create("http://s3.mogucdn.com/mlcdn/c45406/180510_48c66d4975hjck9hd92a3deef4k7g.groovy"));

        groovyShell.evaluate("def sayHello(name) {\n" +
                "    println(\"hello \" + name)\n" +
                "}\n" +
                "\n" +
                "sayHello(name)");
    }

    /**
     * 通过groovyClassloader动态加载Groovy Class
     *
     * @throws IOException
     */
    public static void testGroovy3() throws IOException {

    }

    private static GroovyClassLoader groovyClassLoader = null;

    /**
     * 初始化类加载器
     */
    public static void initGroovyClassLoader() {
        CompilerConfiguration compilerConfiguration = new CompilerConfiguration();
        compilerConfiguration.setSourceEncoding("utf-8");
        groovyClassLoader = new GroovyClassLoader(Thread.currentThread().getContextClassLoader(), compilerConfiguration);
    }

    /**
     * 反射调用
     *
     * @param name
     * @param sex
     * @param age
     * @return
     * @throws IOException
     */
    public static String classLoaderInvokeSayHello(String name, String sex, int age) throws IOException {
        String result = "";
        File groovyFile = new File("src/main/java/GroovyShell_2.groovy");
        if (!groovyFile.exists()) {
            return result;
        }
        try {
            //获得GroovyShell_2加载的class
            Class<?> groovyClass = groovyClassLoader.parseClass(groovyFile);
            //获得GroovyShell_2的实例
            GroovyObject groovyObject = (GroovyObject) groovyClass.newInstance();
            //反射调用
            Object methodResult = groovyObject.invokeMethod("sayHello", new Object[]{name, sex, age});
            if (methodResult != null) {
                result = methodResult.toString();
            }
        } catch (Exception e) {
            System.out.println("加载groovy类失败");
        }
        return result;
    }

    /**
     * 使用GroovyScriptEngine加载运行groovy脚本
     */
    public static void testGroovy4() throws IOException, ResourceException, ScriptException {
        //本地文件或者http文件 ？ groovyScript的根路径
        GroovyScriptEngine engine = new GroovyScriptEngine("src/main/java/shell/");

        Binding binding = new Binding();
        binding.setVariable("name", "麻豆");

        //厉害的一P啊～ ,本地和远端都可以的 赞赞赞～
//        Object result = engine.run("GroovyShell_3.groovy", binding);
        Object result = engine.run("http://s11.mogucdn.com/mlcdn/c45406/180510_0g80a8klc0h43l98jlj6hkj9k9kb9.groovy", binding);
        System.out.println(result);
    }

}
