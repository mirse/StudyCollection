package com.wdz.module_architecture.pattern.singleton;


/**
 * Java并发
 * 1、原子性：对基本数据类型变量的读取和赋值性操作是原子性，即不可被中断，要么执行要么不执行
 * 2、可见性：volatile关键字保证可见性，他会保证修改的值马上更新到主存
 * 3、有序性：java模型中，允许编译器和处理器对指令进行重排序，但是重排序过程不会影响到单线程程序的执行，却会影响到多线程并发执行的正确性。
 *
 *
 * Java虚拟机有5种情况必须立即对类进行初始化
 * 1、new关键字实例对象，读取或者设置一个类的静态对象，调用一个类的静态方法
 * 2、使用反射方法对类进行调用时
 * 3、当初始化一个类，其父类还未初始化时
 * 4、虚拟机启动时，用户需要指定一个要执行的主类
 * 5、当使用JDK 1.7的动态语言支持时，如果一个java.lang.invoke.MethodHandle实例最后的解析结果REF_getStatic、REF_putStatic、REF_invokeStatic的方法句柄，
 * 并且这个方法句柄所对应的类没有进行过初始化，则需要先触发其初始化。
 *
 */
public class Singleton {
    /**
     * 懒汉式 —— 线程不安全
     * 缺点：当多线程并行调用getInstance(),会创建多个实例
     */
//    private static Singleton singleton;
//    public static Singleton getInstance(){
//        if (singleton == null){
//            singleton = new Singleton();
//        }
//        return singleton;
//    }

    /**
     * 懒汉式 —— 线程安全
     * 缺点：在任何时候只能有一个线程调用getInstance()
     */
//    private static Singleton singleton;
//    public synchronized static Singleton getInstance(){
//        if (singleton == null){
//            singleton = new Singleton();
//        }
//        return singleton;
//    }

    /**
     * 双重校验锁
     * 缺点：instance = new Singleton();并非原子操作
     * 1、给instance分配空间 2、调用Singleton的构造函数来初始化成员变量 3、将instance对象指向分配的内存空间
     * 编译执行顺序可能是1，3，2
     *
     * 3执行完毕，2未执行前，线程2抢占了，这时instance是非null(实际上未初始化)，导致问题
     */
//    private static Singleton singleton;
//    public static Singleton getInstance(){
//        if (singleton == null){ //single check
//            synchronized (Singleton.class){
//                if (singleton == null){ //double check,防止生成多个实例
//                    singleton = new Singleton();
//                }
//            }
//        }
//        return singleton;
//    }

    /**
     * singleton变量声明成volatile，保证singleton的可见性与排序性
     */
//    private static volatile Singleton singleton;
//    private Singleton() {
//
//    }
//    public static Singleton getInstance(){
//        if (singleton==null){
//            synchronized (Singleton.class){
//                if (singleton == null){
//                    singleton = new Singleton();
//                }
//            }
//        }
//        return singleton;
//    }
    /**
     * 饿汉式
     * 缺点：并不是一种懒加载形式，加载类后就初始化，如果实例是依赖参数或者配置文件的，这种单例无法实现
     */
//    private static final Singleton singleton = new Singleton();
//    public static Singleton getInstance(){
//        return singleton;
//    }

    /**
     * 静态内部类
     * 优点：外部类加载时，并不会马上去加载内部类，内部类不加载则不去初始化SINGLETON，故不占内存
     * 缺点：无法传参
     */
    private static class SingletonHolder{
        private static final Singleton SINGLETON = new Singleton();
    }
    public static Singleton getInstance(){
        return SingletonHolder.SINGLETON;
    }
}
