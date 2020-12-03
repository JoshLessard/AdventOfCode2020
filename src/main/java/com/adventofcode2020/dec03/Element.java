package com.adventofcode2020.dec03;

public enum Element {

    OPEN {
        @Override
        public String toString() {
            return ".";
        }
    },
    TREE {
        @Override
        public String toString() {
            return "#";
        }
    };
}
