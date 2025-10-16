package hu.webarticum.inno.valuegeneratordemo;

import hu.webarticum.holodb.core.data.binrel.monotonic.Monotonic;
import hu.webarticum.holodb.core.data.binrel.permutation.Permutation;
import hu.webarticum.holodb.core.data.source.MonotonicSource;
import hu.webarticum.holodb.core.data.source.NullPaddedSortedSource;
import hu.webarticum.holodb.core.data.source.NullPaddedSource;
import hu.webarticum.holodb.core.data.source.PermutatedIndexedSource;
import hu.webarticum.holodb.core.data.source.SortedSource;
import hu.webarticum.holodb.core.data.source.Source;
import hu.webarticum.miniconnect.lang.LargeInteger;

public class ValueSetState {
    
    public enum Status {
        
        EMPTY, SOURCE, ERROR
        
    }
    
    private static final ValueSetState EMPTY = new ValueSetState(Status.EMPTY, null, null, null, null);
    
    private final Status status;
    
    private final Source<?> finalSource;
    
    private final Monotonic monotonic;
    
    private final Permutation permutation;
    
    private final String errorMessage;
    
    private ValueSetState(
            Status status, Source<?> finalSource, Monotonic monotonic, Permutation permutation, String errorMessage) {
        this.status = status;
        this.finalSource = finalSource;
        this.monotonic = monotonic;
        this.permutation = permutation;
        this.errorMessage = errorMessage;
    }
    
    public static ValueSetState empty() {
        return EMPTY;
    }

    public static ValueSetState ofSource(Source<?> baseSource, Monotonic monotonic, Permutation permutation) {
        LargeInteger targetSize = permutation.size();
        Source<?> finalSource;
        if (baseSource instanceof SortedSource) {
            SortedSource<?> resizedSource = new MonotonicSource<>((SortedSource<?>) baseSource, monotonic);
            if (resizedSource.size().isLessThan(targetSize)) {
                resizedSource = new NullPaddedSortedSource<>(resizedSource, targetSize);
            }
            finalSource = new PermutatedIndexedSource<>(resizedSource, permutation);
        } else {
            finalSource = baseSource;
            if (finalSource.size().isLessThan(targetSize)) {
                finalSource = new NullPaddedSource<>(finalSource, targetSize);
            }
        }
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

    public Source<?> finalSource() {
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
