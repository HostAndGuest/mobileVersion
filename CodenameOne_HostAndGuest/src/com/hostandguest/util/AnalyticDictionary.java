package com.hostandguest.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * this class is a basic emotion dictionnary not a complex highly advanced one
 * this class contains english-only words not auto translated based on user language
 * contains different dictionnaries of semantic words that allows to verify the emotion given in a comment
 * it might be better to read and evaluate from worst to best, in case of sarcasm for example
 * this class doesnt take in consideration statments that can express multiple emtion
 * for example :
 *      "If Only" could mean
 *          if only i could stay longer
 *          if only there was what promised
 *      "It Might Have Been"
 *          it might have been better to tell who is better at throwing that chopstick :D
 *          it might have been better to actually tell that wifi is not free
 */
public class AnalyticDictionary {
    /**
     * represents a sad unpleasant time
     */
    private static final List<String> SADNESS = new ArrayList<>(
            Arrays.asList("disappoint", "sad", "unpleasant", "depressed", "bored", "bored", "upset", "horrible", "lonely", "mopey")
        );
    /**
     * represents a raging unpleasant time
     */
    private static final List<String> ANGER = new ArrayList<>(
            Arrays.asList("Stressed", "rage", "anger", "angry", "annoyanbce", "nervous", "disguest", "loathing", "irritate", "mad", 
                "furious", "contempt", "shitty")
        );
    /**
     * represents a happy pleasant time
     */
    private static final List<String> JOY = new ArrayList<>(
            Arrays.asList("happy", "glad", "passionate", "good", "love", "liked", "serenity", "amazement", "surprise", "ecstasy", "aroused", "antsy", "fulfill",
                "optimistic", "complete", "content", "awe", "elated", "ecstatic", "nice")
        );

    private static final List<String> NEGATION = new ArrayList<>(Arrays.asList("not", "no", "nor", "none", "neither", "can't", "doesn't", "didn't", "don't"));
    
    public static List<String> getSADNESS() {
        return SADNESS;
    }

    public static List<String> getANGER() {
        return ANGER;
    }

    public static List<String> getJOY() {
        return JOY;
    }

    public static List<String> getNEGATION() {
        return NEGATION;
    }
}
