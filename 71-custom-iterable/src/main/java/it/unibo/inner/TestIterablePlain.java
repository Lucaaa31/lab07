package it.unibo.inner;

import it.unibo.inner.api.IterableWithPolicy;
import it.unibo.inner.test.impl.IterableWithPolicyImpl;

import java.util.ArrayList;
import java.util.Arrays;

import static it.unibo.inner.test.Assertions.assertContentEqualsInOrder;

public class TestIterablePlain {

    private TestIterablePlain() {}

    private static <T> IterableWithPolicy<T> getIterableWithPolicy(T[] elements) {
        return new IterableWithPolicyImpl<>(elements); // TODO: return the implementation of IterableWithPolicy
    }

    public static void main(String[] args) {
        Integer[] test1 = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };

        IterableWithPolicy<Integer> evenIterable = getIterableWithPolicy(test1);
        assertContentEqualsInOrder(evenIterable, Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));

        Integer[] test2 = {};

        IterableWithPolicy<Integer> emptyIterable = getIterableWithPolicy(test2);
        assertContentEqualsInOrder(emptyIterable, new ArrayList<>());

        Integer[] test3 = { 100 };

        IterableWithPolicy<Integer> oneIterable = getIterableWithPolicy(test3);
        assertContentEqualsInOrder(oneIterable, Arrays.asList(100));
    }
}
