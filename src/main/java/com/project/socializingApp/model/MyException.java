package com.project.socializingApp.model;

public class MyException extends RuntimeException{
    public MyException(String str) {
        super(str);
    }
    public MyException(String str, Exception exception) {
        super(str, exception);
    }
}
