package com.mycompany.practicaltask1_14;

public class Main {
    private static final Object lock = new Object();
    private static int currentThrd = 0;

    public static void main(String[] args) {
        System.out.print("Zverev Pavel, RIBO-01-22\n");
        Thread thread1 = new Thread(() -> printThrdName(0));
        Thread thread2 = new Thread(() -> printThrdName(1));

        thread1.start();
        thread2.start();

        System.out.println("Нажмите Enter для завершения программы.");
        try {
            System.in.read();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        thread1.interrupt();
        thread2.interrupt();
    }

    private static void printThrdName(int thrdId) {
        while (!Thread.currentThread().isInterrupted()) {
            synchronized (lock) {
                while (currentThrd != thrdId) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }
                System.out.println(Thread.currentThread().getName());
                currentThrd = (currentThrd + 1) % 2;
                lock.notify();
            }
        }
    }
}
