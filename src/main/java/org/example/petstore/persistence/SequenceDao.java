package org.example.petstore.persistence;

import org.example.petstore.domain.Sequence;

public interface SequenceDao {
    Sequence getSequence(Sequence sequence);
    void updateSequence(Sequence sequence);
}
