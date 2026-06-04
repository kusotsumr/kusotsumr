package musicbandlab.common.contracts;

import java.io.Serializable;

public record DataEntry<K extends Serializable, V extends Serializable>(
        K key,
        V value
) implements Serializable {}