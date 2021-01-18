package com.adventofcode2020.dec23;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

class CupCircle {

    private final SortedMap<Integer, CupNode> nodesByCup = new TreeMap<>();
    private CupNode current;

    void addCup( int cup ) {
        if ( current == null ) {
            current = createFirstNode( cup );
            nodesByCup.put( cup, current );
        } else {
            addCupAfter( current.previous, cup );
        }
    }

    private CupNode createFirstNode( int data ) {
        CupNode node = new CupNode( data );
        node.next = node;
        node.previous = node;
        return node;
    }

    private void addCupAfter( CupNode cupNodeBefore, int newCup ) {
        CupNode cupNodeAfter = cupNodeBefore.next;
        CupNode newNode = new CupNode( newCup );
        cupNodeBefore.next = newNode;
        cupNodeAfter.previous = newNode;
        newNode.previous = cupNodeBefore;
        newNode.next = cupNodeAfter;
        nodesByCup.put( newCup, newNode );
    }

    void takeTurn() {
        List<Integer> cupsToMove = new ArrayList<>( 3 );
        for ( int i = 0; i < 3; ++i ) {
            cupsToMove.add( removeCupAfterCurrent() );
        }
        final CupNode destinationCupNode = nodesByCup.get( getDestinationCup() );
        for ( int i = cupsToMove.size() - 1; i >= 0; --i ) {
            int cup = cupsToMove.get( i );
            addCupAfter( destinationCupNode, cup );
        }
        current = current.next;
    }

    private int removeCupAfterCurrent() {
        CupNode nodeToRemove = current.next;
        current.next = nodeToRemove.next;
        nodeToRemove.next.previous = current;
        nodesByCup.remove( nodeToRemove.cup );
        return nodeToRemove.cup;
    }

    private int getDestinationCup() {
        int candidateCup = current.cup - 1;
        while ( true ) {
            if ( nodesByCup.containsKey( candidateCup ) ) {
                return candidateCup;
            }
            --candidateCup;
            if ( candidateCup < nodesByCup.firstKey() ) {
                candidateCup = nodesByCup.lastKey();
            }
        }
    }

    String cupsAfter( int cup ) {
        StringBuilder builder = new StringBuilder();
        CupNode startingNode = nodesByCup.get( cup );
        CupNode currentNode = startingNode.next;
        while ( currentNode != startingNode ) {
            builder.append( currentNode.cup );
            currentNode = currentNode.next;
        }
        return builder.toString();
    }

    List<Integer> cupsAfter( int cup, int numberOfCups ) {
        List<Integer> cups = new ArrayList<>( numberOfCups );
        CupNode currentNode = nodesByCup.get( cup );
        for ( int i = 0; i < numberOfCups; ++i ) {
            currentNode = currentNode.next;
            cups.add( currentNode.cup );
        }
        return cups;
    }

    @Override
    public String toString() {
        return current.cup + cupsAfter( current.cup );
    }

    private static class CupNode {

        private final int cup;

        private CupNode previous;
        private CupNode next;

        private CupNode( int cup ) {
            this.cup = cup;
        }
    }
}
