package com.example.javatestlib;

import java.util.Observable;
import java.util.Observer;

public class MyClass {

    public static void main(String[] args) {

        MyObserver observer = new MyObserver();
        MyObservable observable = new MyObservable();
        //TODO 订阅
        observable.addObserver(observer);
        //TODO 发布事件
        observable.sayChanged();
    }

    static class  MyObserver implements Observer {

        @Override
        public void update(Observable observable, Object o) {
            System.out.println("anhao =  "+o);
        }
    }

    static class MyObservable extends  Observable{

        void sayChanged(){
            //TODO  setchanged不能漏掉
            //TODO 否则observerb不会收到
            setChanged();
            notifyObservers("123456");
        }
    }

}

