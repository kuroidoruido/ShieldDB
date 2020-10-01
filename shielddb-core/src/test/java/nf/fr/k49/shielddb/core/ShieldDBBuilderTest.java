package nf.fr.k49.shielddb.core;

import nf.fr.k49.shielddb.core.shield.BottomShield;
import nf.fr.k49.shielddb.core.shield.ShieldDBShield;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ShieldDBBuilderTest {

    @Test
    public void firstShieldPlacedAtBeginningOfShield() {
        ShieldDBShield<String> anyFirstShield = new MockDBShield();
        ShieldDBShield<String> anySecondShield = new MockDBShield();

        List<String> build = ShieldDB.<String>builder()
                .shield(anyFirstShield)
                .shield(anySecondShield)
                .build();

        MatcherAssert.assertThat(build, Matchers.is(anyFirstShield));
    }

    @Test
    public void shieldDbWithoutShieldsHasOnlyBottomShield() {
        List<String> build = ShieldDB.<String>builder()
                .build();

        MatcherAssert.assertThat(build, Matchers.instanceOf(BottomShield.class));
    }

    @Test
    public void builderNestsShields() {
        ShieldDBShield<String> anyFirstShield = new MockDBShield();
        ShieldDBShield<String> anySecondShield = new MockDBShield();

        List<String> build = ShieldDB.<String>builder()
                .shield(anyFirstShield)
                .shield(anySecondShield)
                .build();

        MockDBShield shield = (MockDBShield)build;
        MatcherAssert.assertThat(shield.getNextShield(), Matchers.is(anySecondShield));
    }

    @Test
    public void lastElementIsBottomShield() {
        ShieldDBShield<String> anyFirstShield = new MockDBShield();
        ShieldDBShield<String> anySecondShield = new MockDBShield();

        List<String> build = ShieldDB.<String>builder()
                .shield(anyFirstShield)
                .shield(anySecondShield)
                .build();

        MockDBShield shield = (MockDBShield)build;
        MatcherAssert.assertThat(shield.getNextShield().getNextShield(), Matchers.instanceOf(BottomShield.class));
    }

    @Test
    public void lastElementOfManyConfiguredShieldIsBottomShield() {
        int countShields = 20;
        List<MockDBShield> shields = IntStream.range(0, countShields).mapToObj((i) -> new MockDBShield()).collect(Collectors.toList());

        ShieldDBBuilder<String> builder = ShieldDB.<String>builder();
        shields.stream().forEach(builder::shield);

        ShieldDBShield<String> db = (ShieldDBShield<String>)builder.build();

        for (int i = 0; i < countShields; i++) {
            db = db.getNextShield();
        }
        MatcherAssert.assertThat(db, Matchers.instanceOf(BottomShield.class));
    }


    private static class MockDBShield implements ShieldDBShield<String> {
        @Override
        public ShieldDBShield<String> getNextShield() {
            return nextShield;
        }

        ShieldDBShield<String> nextShield = null;
        @Override
        public void setNextShield(ShieldDBShield<String> next) {
            nextShield = next;
        }

        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean contains(Object o) {
            return false;
        }

        @Override
        public Iterator<String> iterator() {
            return null;
        }

        @Override
        public Object[] toArray() {
            return new Object[0];
        }

        @Override
        public <T> T[] toArray(T[] ts) {
            return null;
        }

        @Override
        public boolean add(String s) {
            return false;
        }

        @Override
        public boolean remove(Object o) {
            return false;
        }

        @Override
        public boolean containsAll(Collection<?> collection) {
            return false;
        }

        @Override
        public boolean addAll(Collection<? extends String> collection) {
            return false;
        }

        @Override
        public boolean addAll(int i, Collection<? extends String> collection) {
            return false;
        }

        @Override
        public boolean removeAll(Collection<?> collection) {
            return false;
        }

        @Override
        public boolean retainAll(Collection<?> collection) {
            return false;
        }

        @Override
        public void clear() {

        }

        @Override
        public String get(int i) {
            return null;
        }

        @Override
        public String set(int i, String s) {
            return null;
        }

        @Override
        public void add(int i, String s) {

        }

        @Override
        public String remove(int i) {
            return null;
        }

        @Override
        public int indexOf(Object o) {
            return 0;
        }

        @Override
        public int lastIndexOf(Object o) {
            return 0;
        }

        @Override
        public ListIterator<String> listIterator() {
            return null;
        }

        @Override
        public ListIterator<String> listIterator(int i) {
            return null;
        }

        @Override
        public List<String> subList(int i, int i1) {
            return null;
        }
    }

}
