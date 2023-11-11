package it.unibo.inner.test.impl;

import java.util.Iterator;
import java.util.function.Predicate;
import java.util.NoSuchElementException;

import it.unibo.inner.api.IterableWithPolicy;

public class IterableWithPolicyImpl<T> implements IterableWithPolicy<T>{

    private T[] elements;
    private Predicate<T> filter;

    /**
     * 1-ary
     * Non capisco perché funzioni... forse perché con 
     * il this richiami il secondo costruttore(?)
     * @param elements elementi del vettore
     */
    public IterableWithPolicyImpl(T[] elements){
        this( 
            elements,
            new Predicate<T>() {

                @Override
                public boolean test(T t) {
                    return true;
                }
            
        });
    }
    
    /**
     * 2-ary
     * @param elements elementi del vettore
     * @param filter filtro
     */
    public IterableWithPolicyImpl(T[] elements, Predicate<T> filter){
        this.elements = elements;
        this.filter = filter;

    }
    @Override
    public Iterator<T> iterator() {
        return new InnerIterator();
    }

    /**
     * ini  zializzo il filtro
     */
    @Override
    public void setIterationPolicy(Predicate<T> filter) {
        this.filter = filter;
    }
    
    private class InnerIterator implements Iterator<T>{

        private int index=0;

        @Override
        public boolean hasNext() {
            while (index < elements.length) {
                T elem = elements[index];
                if (filter.test(elem)) {
                    return true;
                }
                index++;
            }
            return false;
            
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
