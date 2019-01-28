package top.lovelily;

import java.lang.annotation.Annotation;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException, NoSuchFieldException {


        System.out.println( PacketHandler.class.isAnnotationPresent(PacketMapping.class));
        System.out.println(PacketHandler.class.getMethods().length);
        System.out.println(PacketHandler.class.getAnnotations().length);
        Annotation[] annotations = PacketHandler.class.getAnnotations();
        PacketMapping packetMappings = (PacketMapping) annotations[0];
        System.out.println(packetMappings.value());
        Class clz = ClassLoader.getSystemClassLoader().loadClass("top.lovelily.PacketHandler");

        UploadMessageQueueManagerBak.SP.start();



//        UserService userService = new UserService();
//        try {
//            userService.add();
//        }catch (Exception e){
//            System.out.println("catch the exception:"+e);
//        }
//
//        try {
//            userService.update();
//        }catch (Exception e){
//            System.out.println("catch the exception:"+e);
//        }

    }
}
