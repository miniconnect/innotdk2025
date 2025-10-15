package hu.webarticum.inno.valuegeneratordemo;

import java.util.function.BiFunction;
import java.util.function.Function;

import hu.webarticum.holodb.core.data.binrel.monotonic.Monotonic;
import hu.webarticum.holodb.core.data.binrel.permutation.Permutation;
import hu.webarticum.holodb.core.data.source.IndexedSource;
import hu.webarticum.holodb.core.data.source.MonotonicSource;
import hu.webarticum.holodb.core.data.source.PermutatedIndexedSource;
import hu.webarticum.holodb.core.data.source.SortedSource;
import hu.webarticum.miniconnect.lang.LargeInteger;

public class ValueSetState {
    
    public enum Status {
        
        EMPTY, SOURCE, ERROR
        
    }
    
    private static final ValueSetState EMPTY = new ValueSetState(Status.EMPTY, null, null, null, null);
    
    private final Status status;
    
    private final IndexedSource<?> finalSource;
    
    private final Monotonic monotonic;
    
    private final Permutation permutation;
    
    private final String errorMessage;
    
    private ValueSetState(
            Status status, IndexedSource<?> finalSource, Monotonic monotonic, Permutation permutation, String errorMessage) {
        this.status = status;
        this.finalSource = finalSource;
        this.monotonic = monotonic;
        this.permutation = permutation;
        this.errorMessage = errorMessage;
    }
    
    public static ValueSetState empty() {
        return EMPTY;
    }

    public static ValueSetState ofSource(
            LargeInteger targetSize,
            SortedSource<?> baseSource,
            BiFunction<LargeInteger, LargeInteger, Monotonic> monotonicFactory,
            Function<LargeInteger, Permutation> permutationFactory) {
        Monotonic monotonic = monotonicFactory.apply(targetSize, baseSource.size());
        SortedSource<?> resizedSource = new MonotonicSource<>(baseSource, monotonic);
        Permutation permutation = permutationFactory.apply(targetSize);
        IndexedSource<?> finalSource = new PermutatedIndexedSource<>(resizedSource, permutation);
        return new ValueSetState(Status.SOURCE, finalSource, monotonic, permutation, null);
    }

    public static ValueSetState ofError(String message) {
        return new ValueSetState(Status.ERROR, null, null, null, message);
    }

    public static ValueSetState getEmpty() {
        return EMPTY;
    }

    public Status status() {
        return status;
    }

    public IndexedSource<?> fFinalSource() {
        return finalSource;
    }

    public Monotonic monotonic() {
        return monotonic;
    }

    public Permutation permutation() {
        return permutation;
    }

    public String errorMessage() {
        return errorMessage;
    }

}
