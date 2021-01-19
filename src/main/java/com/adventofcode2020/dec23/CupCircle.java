package com.adventofcode2020.dec23;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

class CupCircle {

    private final CupMap cupMap;
    private CupNode current;

    CupCircle( int capacity ) {
        this.cupMap = new CupMap( capacity );
    }

    void addCup( int cup ) {
        if ( current == null ) {
            current = createFirstNode( cup );
            cupMap.put( cup, current );
        } else {
            addCupAfter( current.previous, new CupNode( cup ) );
        }
    }

    private CupNode createFirstNode( int data ) {
        CupNode node = new CupNode( data );
        node.next = node;
        node.previous = node;
        return node;
    }

    private void addCupAfter( CupNode cupNodeBefore, CupNode cupNodeToInsert ) {
        CupNode cupNodeAfter = cupNodeBefore.next;
        cupNodeBefore.next = cupNodeToInsert;
        cupNodeAfter.previous = cupNodeToInsert;
        cupNodeToInsert.previous = cupNodeBefore;
        cupNodeToInsert.next = cupNodeAfter;
        cupMap.put( cupNodeToInsert.cup, cupNodeToInsert );
    }

    void takeTurn() {
        Stack<CupNode> cupsToMove = new Stack<>();
        for ( int i = 0; i < 3; ++i ) {
            cupsToMove.push( removeCupAfterCurrent() );
        }
        final CupNode destinationCupNode = cupMap.cupNodeBefore( current.cup );
        while ( ! cupsToMove.isEmpty() ) {
            CupNode cupNode = cupsToMove.pop();
            addCupAfter( destinationCupNode, cupNode );
        }
        current = current.next;
    }

    private CupNode removeCupAfterCurrent() {
        CupNode nodeToRemove = current.next;
        current.next = nodeToRemove.next;
        nodeToRemove.next.previous = current;
        cupMap.remove( nodeToRemove.cup );
        return nodeToRemove;
    }

    String cupsAfter( int cup ) {
        StringBuilder builder = new StringBuilder();
        CupNode startingNode = cupMap.get( cup );
        CupNode currentNode = startingNode.next;
        while ( currentNode != startingNode ) {
            builder.append( currentNode.cup );
            currentNode = currentNode.next;
        }
        return builder.toString();
    }

    List<Integer> cupsAfter( int cup, int numberOfCups ) {
        List<Integer> cups = new ArrayList<>( numberOfCups );
        CupNode currentNode = cupMap.get( cup );
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
}
