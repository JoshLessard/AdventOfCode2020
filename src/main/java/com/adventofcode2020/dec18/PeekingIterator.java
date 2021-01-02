package com.adventofcode2020.dec18;

import java.util.Iterator;

interface PeekingIterator<T> extends Iterator<T> {

    T peek();
}
