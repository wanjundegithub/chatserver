package com.company.im.chat.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.classreading.SimpleMetadataReaderFactory;
import org.springframework.util.ClassUtils;
import org.springframework.util.ResourceUtils;

import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

/*
**类扫描（借助Spring）
 */
public class ClassScannerUtil {

    private static Logger logger= LoggerFactory.getLogger(ClassScannerUtil.class);

    /*
    **默认过滤器（无限定条件）
     */
    private static  final Predicate<Class<?>> Empty_Filter=clazz->true;

    /*
    **获取路径下所有类
     */
    public static Set<Class<?>> getClass(String scanClassPath){
        return getClass(scanClassPath,Empty_Filter);
    }

    /*
    **获取路径下所有子类（不包括抽象类）
     */
    public static Set<Class<?>> getAllSubClass(String scanClassPath,Class<?> parent){
        return getClass(scanClassPath,clazz->parent.isAssignableFrom(clazz)&&!
                Modifier.isAbstract(clazz.getModifiers()));
    }

    /*
    **根据过滤条件获取包下类
     */
    public static Set<Class<?>> getClass(String pack, Predicate<Class<?>> filter){
        Set<Class<?>> result=new HashSet<>();
        //获取spring资源解析器
        ResourcePatternResolver patternResolver=new PathMatchingResourcePatternResolver();
        //元数据读取工厂
        MetadataReaderFactory metadataReaderFactory=new SimpleMetadataReaderFactory(patternResolver);
        //将带.的包路径转换为带/的路径
        String path= ClassUtils.convertClassNameToResourcePath(pack);
        //组合该类的全路径(带classPath:前缀)
        String location= ResourceUtils.CLASSPATH_URL_PREFIX+path+"/**/*.class";
        Resource[] resources;
        try {
            //根据路径获取资源
            resources=patternResolver.getResources(location);
            for(var resource:resources){
                MetadataReader metadataReader=metadataReaderFactory.getMetadataReader(resource);
                if(resource.isReadable()){
                    String clazzName=metadataReader.getClassMetadata().getClassName();
                    //忽略内部类
                    if(clazzName.contains("$")){
                        continue;
                    }
                    Class<?> clazz=Thread.currentThread().getContextClassLoader().loadClass(clazzName);
                    //根据限定条件过滤类
                    if(filter.test(clazz)){
                        result.add(clazz);
                    }
                }
            }
        }
        catch (Exception e){
            logger.error(e.getMessage());
        }
        return result;
    }
}
