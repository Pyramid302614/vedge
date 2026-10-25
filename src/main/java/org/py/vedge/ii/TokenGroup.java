package org.py.vedge.ii;

import org.py.vedge.infa.external.Window;
import org.py.vedge.util.Sparry;

import java.util.function.Consumer;

public class TokenGroup {

    public final Sparry<Token> all = new Sparry<>();
//    private Sparry<Token> primary = new Sparry<>();
//    private Sparry<Token> secondary = new Sparry<>();
//
//    public void addPrimaries(Sparry<Token> tokens) {
//        all.addAll(tokens);
//        primary.addAll(tokens);
//    }
//    public void addSecondaries(Sparry<Token> tokens) {
//        all.addAll(tokens);
//        secondary.addAll(tokens);
//    }
//    public void addPrimaries(Token... tokens) {
//        all.addAll(tokens);
//        primary.addAll(tokens);
//    }
//    public void addSecondaries(Token... tokens) {
//        all.addAll(tokens);
//        secondary.addAll(tokens);
//    }
    public void add(Sparry<Token> tokens) {
        all.addAll(tokens);
    }
    public void add(Token... tokens) {
        all.addAll(tokens);
    }

    public void tickAll() {
        all.forEach(Token::tick);
    }
    public void collideAll() {

    }
    public void renderAll() {
        all.forEach(i -> i.render(Window.vg.graphics));
    }

    public void forEach(Consumer<Token> consumer) {
        all.forEach(consumer);
    }

}
