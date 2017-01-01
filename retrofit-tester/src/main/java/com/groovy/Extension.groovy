package com.groovy;

class Extension {
    static String greets(String self, String name) {
        "Hi ${name}, I'm ${self}"
    }

    static void ext(String self) {
        println self+"ext"
    }

}