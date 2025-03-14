package cardgame;

//Card.java
public class Card {
 private int value;  // The value of the card, used for comparisons

 public Card(int value) {
     this.value = value;
 }

 public int getValue() {
     return value;
 }

 @Override
 public String toString() {
     return String.valueOf(value);
 }
}