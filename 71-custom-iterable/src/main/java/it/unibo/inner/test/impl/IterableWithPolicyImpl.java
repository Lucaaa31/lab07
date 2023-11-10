package it.unibo.inner.test.impl;

import java.util.Iterator;
import java.util.function.Predicate;
import java.util.NoSuchElementException;

import it.unibo.inner.api.IterableWithPolicy;

public class IterableWithPolicyImpl<T> implements IterableWithPolicy<T>{

    final private T[] elements;

    public IterableWithPolicyImpl(T[] elements){
        this.elements = elements;
    }
    @Override
    public Iterator<T> iterator() {
        return new InnerIterator();
    }

    @Override
    public void setIterationPolicy(Predicate filter) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setIterationPolicy'");
    }
    
    private class InnerIterator implements Iterator<T>{

        private int index=0;
        @Override
        public boolean hasNext() {
            return index < elements.length;
        }
        /**
         * Restituisce l'elemento dopo del vettore
         */
        @Override
        public T next() {
            if (hasNext()) {
                T element = elements[index];
                this.index++;
                return element;
            }
            throw new NoSuchElementException();
        }

    }
}
