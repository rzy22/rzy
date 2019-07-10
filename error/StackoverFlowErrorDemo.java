package com.rzy.error;

/**
 *      栈溢出
 *
 *      方法的加载符合栈，栈是管运行的，一个递归调用了以后一个深度的加载方法容易导致 StackoverFlowError
 */

public class StackoverFlowErrorDemo {

    public static void main(String[] args) {
        stackOverflowError();
    }

    private static void stackOverflowError() {
        stackOverflowError(); // Exception in thread "main" java.lang.StackOverflowError
    }
}
