package com.vicenzo;

import org.springframework.stereotype.Service;

@Service
public class FooService {

    private final Main.Foo foo;

    public FooService(Main.Foo foo) {
        this.foo = foo;
    }

    public String getFoo() {
        return foo.name();
    }
}
