package nf.fr.k49.shielddb.core;

import nf.fr.k49.shielddb.core.shield.BottomShield;
import nf.fr.k49.shielddb.core.shield.ShieldDBShield;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@SuppressWarnings("unchecked")
public interface BaseShieldDBBuilderTest<T> {

    @Test
    default void firstShieldPlacedAtBeginningOfShield() {

        ShieldDBShield<T> anyFirstShield = mock(ShieldDBShield.class);
        ShieldDBShield<T> anySecondShield = mock(ShieldDBShield.class);

        List<T> build = ShieldDB.<T>builder()
                .shield(anyFirstShield)
                .shield(anySecondShield)
                .build();

        assertThat(build, is(anyFirstShield));
    }

    @Test
    default  void setNextShieldForNestingShieldsWouldBeProceedByBuilder() {
        ShieldDBShield<T> anyFirstShield = mock(ShieldDBShield.class);

        ShieldDB.<T>builder()
                .shield(anyFirstShield)
                .build();

        verify(anyFirstShield).setNextShield(any());
    }

    @Test
    default  void setNextShieldForNestingShieldsWouldBeProceedInRightOrder() {
        ShieldDBShield<T> anyFirstShield = mock(ShieldDBShield.class);
        ShieldDBShield<T> anySecondShield = mock(ShieldDBShield.class);

        ShieldDB.<T>builder()
                .shield(anyFirstShield)
                .shield(anySecondShield)
                .build();

        verify(anyFirstShield).setNextShield(eq(anySecondShield));
    }

    @Test
    default  void setNextShieldForLastShieldWouldBeProceedWithBottomShield() {
        ShieldDBShield<T> anyFirstShield = mock(ShieldDBShield.class);
        ShieldDBShield<T> anySecondShield = mock(ShieldDBShield.class);

        ShieldDB.<T>builder()
                .shield(anyFirstShield)
                .shield(anySecondShield)
                .build();

        verify(anySecondShield).setNextShield(isA(BottomShield.class));
    }

    @Test
    default  void shieldDbWithoutShieldsHasOnlyBottomShield() {
        List<T> build = ShieldDB.<T>builder()
                .build();

        assertThat(build, Matchers.instanceOf(BottomShield.class));
    }

    @Test
    default  void nestingShieldsProceedsInRightOrder() {
        int countShields = 20;

        List<ShieldDBShield<T>> shields = prepareShields(countShields);

        ShieldDBBuilder<T> builder = ShieldDB.builder();
        shields.forEach(builder::shield);
        builder.build();

        Assertions.assertAll(createCheckCallSetNextShieldForAllShields(shields));

    }

    static <T> Stream<Executable> createCheckCallSetNextShieldForAllShields(List<ShieldDBShield<T>> shields){
        return shields.stream().map(BaseShieldDBBuilderTest::createCheckCallSetNextShield);
    }

    static <T> Executable createCheckCallSetNextShield(ShieldDBShield<T> shield) {
        return  () ->  verify(shield).setNextShield(any());
    }

    @Test
    default  void lastElementOfManyConfiguredShieldIsBottomShield() {
        int countShields = 20;
        List<ShieldDBShield<T>> shields = prepareShields(countShields);

        ShieldDBBuilder<T> builder = ShieldDB.builder();
        shields.forEach(builder::shield);

        builder.build();
        verify(shields.get(countShields-1)).setNextShield(isA(BottomShield.class));
    }


    default List<ShieldDBShield<T>> prepareShields(int count) {
        List<ShieldDBShield<T>> shields = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            shields.add(mock(ShieldDBShield.class));
        }

        return shields;
    }
}
