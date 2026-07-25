import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

@SuppressWarnings("unchecked")
public class Sparry<T> implements Iterable<T> {


    public int length = 0;
    private T[] v = (T[]) new Object[0]; // TODO: this is broken


    public Sparry(T[] a) {
        v = a;
        length = a != null ? a.length : 0;
    }
    public Sparry() {}


    public T[] toArray() {
        return v;
    }


    public Sparry<T> add(T item) {
        T[] n = (T[]) new Object[v.length+1];
        System.arraycopy(v, 0, n, 0, v.length);
        n[v.length] = item;
        v = n;
        length = v.length;
        return this;
    }

    public Sparry<T> remove(int index) {
        T[] n = (T[]) new Object[v.length-1];
        int f = 0;
        for(int i = 0; i < v.length-1; i++) {
            if(i == index) i++;
            n[f] = v[i];
            f++;
        }
        v = n;
        length = v.length;
        return this;
    }


    public T get(int index) {
        try {
            return v[index];
        } catch(Exception ignored) {
            return null;
        }
    }

    public Sparry<T> set(int index, T value) {
        v[index] = value;
        return this;
    }


    public void clear() {
        v = (T[]) new Object[0];
    }


    public boolean contains(T value) {
        for(T t : v) if(Objects.equals(t, value)) return true;
        return false;
    }
    public int length() {
        return v.length;
    }

    public String join(String joiner) {
        StringBuilder output = new StringBuilder();
        for(int i = 0; i < length; i++) {
            output.append(get(i));
            if(i != length-1) output.append(joiner);
        }
        return output.toString();
    }

    public Sparry<T> map(Function<T,T> function) {
        for(int i = 0; i < length; i++) function.apply(v[i]);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder r = new StringBuilder("[ ");
        for(int i = 0; i < v.length; i++) {
            r.append(v[i]).append(( i != v.length - 1 ) ? ", " : "");
        }
        r.append(" ]");
        return r.toString();
    }

    @Override
    public Iterator<T> iterator() {
        return new CustomIterator();
    }

    private class CustomIterator implements Iterator<T> {

        private int index = 0;

        @Override
        public boolean hasNext() {
            return index < length;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more elements");
            }
            return v[index++];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Remove not supported");
        }
    }

}