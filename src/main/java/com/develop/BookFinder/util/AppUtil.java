package com.develop.BookFinder.util;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AppUtil {


    public static void finalizer(){
        fakeCleaner(50);
        System.out.println("The application has been closed.________________________________________________________");
        System.exit(0);
    }

    public static void fakeCleaner(int lines){
        for (int i = 0; i < lines; i++) {
            System.out.println("\n");
        }
    }
}
