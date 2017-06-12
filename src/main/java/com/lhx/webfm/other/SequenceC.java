package com.lhx.webfm.other;

public class SequenceC implements Sequence {
    private static MyThreadLocal<Integer> numberContainer=new MyThreadLocal(){
        @Override
        protected Object initialValue() {
            return 0;
        }
    };
    @Override
    public int getNumber() {
        numberContainer.set(numberContainer.get()+1);
        return numberContainer.get();
    }
    public static void main(String[] args) {
        Sequence sequence=new SequenceC();
        ClientThread clientThread1=new ClientThread(sequence);
        ClientThread clientThread2=new ClientThread(sequence);
        ClientThread clientThread3=new ClientThread(sequence);
        clientThread1.start();
        clientThread2.start();
        clientThread3.start();
    }
}
