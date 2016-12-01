package com.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class JokeTeller {
    HashMap<Integer, String> mJokeCollection;
    List<Integer> keys;
    Random random;
    Integer randomKey;

    public JokeTeller() {
        mJokeCollection = new HashMap<>();
        mJokeCollection.put(1, "I'd tell you a chemistry joke but I know I wouldn't get a reaction.");
        mJokeCollection.put(2, "Did you hear about the guy who got hit in the head with a can of soda? He was lucky it was a soft drink.");
        mJokeCollection.put(3, "I'm reading a book about anti-gravity. It's impossible to put down.");
        mJokeCollection.put(4, "Weight loss pills stolen this morning - police say suspects are still at large.");
        mJokeCollection.put(5, "What do you do to an open wardrobe? You closet.");
        keys = new ArrayList<>(mJokeCollection.keySet());
        random = new Random();

    }
    public String tellJoke() {
        randomKey = keys.get(random.nextInt(keys.size()));
        return mJokeCollection.get(randomKey);
    }
}
