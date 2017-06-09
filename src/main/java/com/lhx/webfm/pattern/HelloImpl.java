package com.lhx.webfm.pattern;

public class HelloImpl implements Hello {
    @Override
    public void say(String content) {
        System.out.println("say +++"+content);
    }
}
