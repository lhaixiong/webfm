package com.lhx.webfm.other;

public class HelloImpl implements Hello {
    @Override
    public void say(String content) {
        System.out.println("say +++"+content);
    }
}
