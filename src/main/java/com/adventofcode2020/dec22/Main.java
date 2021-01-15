package com.adventofcode2020.dec22;

import static com.adventofcode2020.common.InputUtilities.getInput;

import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    private static final Pattern CARD_PATTERN = Pattern.compile( "\\d+" );

    public static void main( String[] args ) throws IOException {
        List<String> input = getInput( "src/main/resources/dec22/input.txt" );
        List<Integer> player1StartingHand = getHand( 1, input );
        List<Integer> player2StartingHand = getHand( 2, input );

        List<Integer> partAWinningHand = partA( new LinkedList<>( player1StartingHand ), new LinkedList<>( player2StartingHand ) );
        System.out.println( "Part A: " + calculateScore( partAWinningHand ) );

        HandResult partBResult = partB( new LinkedList<>( player1StartingHand ), new LinkedList<>( player2StartingHand ) );
        System.out.println( "Part B: " + calculateScore( partBResult.winningHand() ) );
    }

    private static List<Integer> getHand( int player, List<String> input ) {
        int handStartIndex = getHandStartIndex( player, input );
        List<Integer> hand = new LinkedList<>();
        for ( int i = handStartIndex; i < input.size(); ++i ) {
            String inputLine = input.get( i );
            Matcher matcher = CARD_PATTERN.matcher( inputLine );
            if ( ! matcher.matches() ) {
                break;
            }
            hand.add( Integer.valueOf( inputLine ) );
        }
        return hand;
    }

    private static int getHandStartIndex( int player, List<String> input ) {
        for ( int i = 0; i < input.size(); ++i ) {
            if ( input.get( i ).equals( "Player " + player + ":" ) ) {
                return i + 1;
            }
        }

        throw new IllegalArgumentException( "Player " + player + " not found in input." );
    }

    private static List<Integer> partA(List<Integer> player1Hand, List<Integer> player2Hand ) {
        while ( ! player1Hand.isEmpty() && ! player2Hand.isEmpty() ) {
            int player1Card = player1Hand.remove( 0 );
            int player2Card = player2Hand.remove( 0 );
            if ( player1Card > player2Card ) {
                player1Hand.add( player1Card );
                player1Hand.add( player2Card );
            } else if ( player2Card > player1Card ) {
                player2Hand.add( player2Card );
                player2Hand.add( player1Card );
            } else {
                throw new RuntimeException( "Did not expect a tie." );
            }
        }

        return player1Hand.isEmpty() ? player2Hand : player1Hand;
    }

    private static HandResult partB( List<Integer> player1Hand, List<Integer> player2Hand ) {
        Set<HandConfiguration> seenHandConfigurations = new HashSet<>();
        while ( ! player1Hand.isEmpty() && ! player2Hand.isEmpty() ) {
            HandConfiguration handConfiguration = new HandConfiguration( player1Hand, player2Hand );
            if ( seenHandConfigurations.contains( handConfiguration ) ) {
                return new HandResult( 1, player1Hand );
            }
            seenHandConfigurations.add( handConfiguration );

            int player1Card = player1Hand.remove( 0 );
            int player2Card = player2Hand.remove( 0 );
            if ( player1Hand.size() >= player1Card && player2Hand.size() >= player2Card ) {
                int winningPlayer = partB( new LinkedList<>( player1Hand.subList( 0, player1Card ) ), new LinkedList<>( player2Hand.subList( 0, player2Card ) ) ).winningPlayer();
                if ( winningPlayer == 1 ) {
                    player1Hand.add( player1Card );
                    player1Hand.add( player2Card );
                } else if ( winningPlayer == 2 ) {
                    player2Hand.add( player2Card );
                    player2Hand.add( player1Card );
                } else {
                    throw new RuntimeException( "Did not expect a tie." );
                }
            } else {
                if ( player1Card > player2Card ) {
                    player1Hand.add( player1Card );
                    player1Hand.add( player2Card );
                } else if ( player2Card > player1Card ) {
                    player2Hand.add( player2Card );
                    player2Hand.add( player1Card );
                } else {
                    throw new RuntimeException( "Did not expect a tie." );
                }
            }
        }

        return player1Hand.isEmpty()
            ? new HandResult( 2, player2Hand )
            : new HandResult( 1, player1Hand );
    }

    private static int calculateScore( List<Integer> hand ) {
        int score = 0;
        int multiplier = hand.size();
        for ( int card : hand ) {
            score += multiplier * card;
            --multiplier;
        }

        return score;
    }
}
