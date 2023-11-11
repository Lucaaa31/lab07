package it.unibo.inner;

import it.unibo.inner.api.IterableWithPolicy;
import it.unibo.inner.test.api.Product;
import it.unibo.inner.test.impl.IterableWithPolicyImpl;
import it.unibo.inner.test.impl.ProductImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import static it.unibo.inner.test.Assertions.assertContentEqualsInOrder;

public class TestIterableWithPolicy {

    private TestIterableWithPolicy() {}

    private static <T> IterableWithPolicy<T> getIterableWithPolicy(T[] elements, Predicate<T> filter) {
        return new IterableWithPolicyImpl<>(elements, filter); // TODO: return the implementation of IterableWithPolicy
    }

    private static <T> IterableWithPolicy<T> getIterableWithPolicy(T[] elements) {
        return new IterableWithPolicyImpl<>(elements); // TODO: return the implementation of IterableWithPolicy
    }

    public static void main(String[] args) {
        Integer[] test1 = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
        Predicate<Integer> filterEven = new Predicate<Integer>() {
            public boolean test(Integer elem) {
                return elem % 2 == 0;
            }
        };
        Predicate<Integer> filterOdd = new Predicate<Integer>() {
            public boolean test(Integer elem) {
                return elem % 2 != 0;
            }
        };

        IterableWithPolicy<Integer> evenIterable = getIterableWithPolicy(test1, filterEven);
        IterableWithPolicy<Integer> oddIterable = getIterableWithPolicy(test1, filterOdd);

        assertContentEqualsInOrder(evenIterable, Arrays.asList(2, 4, 6, 8));
        assertContentEqualsInOrder(oddIterable, Arrays.asList(1, 3, 5, 7, 9));

        Predicate<Integer> filterOutAll = new Predicate<Integer>() {
            public boolean test(Integer elem) {
                return false;
            }
        };
        Predicate<Integer> takeAll = new Predicate<Integer>() {
            public boolean test(Integer elem) {
                return true;
            }
        };

        IterableWithPolicy<Integer> emptyIterable = getIterableWithPolicy(test1, filterOutAll);
        IterableWithPolicy<Integer> allIterable = getIterableWithPolicy(test1, takeAll);

        assertContentEqualsInOrder(emptyIterable, new ArrayList<>());
        assertContentEqualsInOrder(allIterable, Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));

        IterableWithPolicy<Integer> switchPolicy = getIterableWithPolicy(test1);

        // By default, if no Predicate is given, the iterator should return all the elements
        assertContentEqualsInOrder(switchPolicy, Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));

        switchPolicy.setIterationPolicy(filterOutAll);

        // After setting a new policy, the iterator should return no elements
        assertContentEqualsInOrder(switchPolicy, new ArrayList<>());


        // Test with products

        Product prod1 = new ProductImpl("Prod 1", 0);
        Product prod2 = new ProductImpl("Prod 2", 100);
        Product prod3 = new ProductImpl("Prod 3", 20);
        Product prod4 = new ProductImpl("Prod 4", 35);
        Product prod5 = new ProductImpl("Prod 5", 450);

        Product[] productsTest = { prod1, prod2, prod3, prod4, prod5 };
        Predicate<Product> filterAllAvailable = new Predicate<Product>() {
            public boolean test(Product elem) {
                return elem.getQuantity() > 0;
            }
        };
        Predicate<Product> filterGraterThanFifty = new Predicate<Product>() {
            public boolean test(Product elem) {
                return elem.getQuantity() > 50;
            }
        };
        Predicate<Product> takeOnlyProductOne = new Predicate<Product>() {
            public boolean test(Product elem) {
                return elem.getName().equals("Prod 1");
            }
        };

        IterableWithPolicy<Product> availableProducts = getIterableWithPolicy(productsTest, filterAllAvailable);
        assertContentEqualsInOrder(availableProducts, Arrays.asList(prod2, prod3, prod4, prod5));

        IterableWithPolicy<Product> expensiveProducts = getIterableWithPolicy(productsTest, filterGraterThanFifty);
        assertContentEqualsInOrder(expensiveProducts, Arrays.asList(prod2, prod5));

        IterableWithPolicy<Product> onlyProductOne = getIterableWithPolicy(productsTest, takeOnlyProductOne);
        assertContentEqualsInOrder(onlyProductOne, Arrays.asList(prod1));
    }
}
