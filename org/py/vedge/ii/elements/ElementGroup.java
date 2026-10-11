package org.py.vedge.ii.elements;

import org.py.vedge.graphics.external.Graphics;
import org.py.vedge.util.Sparry;

public class ElementGroup  {

    private Sparry<Element> value = new Sparry<>();

    public void add(Element... elements) {
        value.addAll(elements);
    }

    public void tick() {
        value.forEach(Element::tick);
    }
    public void render(Graphics g) {
        value.forEach(e -> e.render(g));
    }

    public void onEnter() {
        value.forEach(Element::onEnter);
    }
    public void onExit() {
        value.forEach(Element::onExit);
    }

    public int length() {
        return value.length;
    }

}
