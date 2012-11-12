package com.jaysan1292.project.c3062.util;

import com.jaysan1292.jdcommon.Extensions;
import com.jaysan1292.jdcommon.Range;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/** @author Jason Recillo */
public class PlaceholderGenerator {

    private static final HashMap<Character, Double> letterFreq = new HashMap<Character, Double>() {{
        put('a', 8.167); put('b', 1.492); put('c', 2.782); put('d', 4.253); put('e', 12.702);
        put('f', 2.228); put('g', 2.015); put('h', 6.094); put('i', 6.966); put('j', 0.153);
        put('k', 0.747); put('l', 4.025); put('m', 2.406); put('n', 6.749); put('o', 7.507);
        put('p', 1.929); put('q', 0.095); put('r', 5.987); put('s', 6.327); put('t', 9.056);
        put('u', 2.758); put('v', 1.037); put('w', 2.365); put('x', 0.150); put('y', 1.974);
        put('z', 0.074);
    }};
    private static final HashMap<Character, Double> punctuationFreq = new HashMap<Character, Double>() {{
        put('.', 88.005); put('?', 7.547); put('!', 4.447);
    }};
    private static final Random random = new Random();
    private static final Range<Integer> wordLength = new Range<Integer>(1, 12);
    private static final Range<Integer> sentenceLength = new Range<Integer>(3, 11);

    private PlaceholderGenerator() {}

    private static char getRandomChar(HashMap<Character, Double> frequencies) {
        double totalWeight = 0;

        for (Double weight : frequencies.values()) {
            totalWeight += weight;
        }

        while (true) {
            double choice = random.nextInt((int) totalWeight + 1);
            double sum = 0;
            for (Map.Entry<Character, Double> letter : frequencies.entrySet()) {
                for (double i = sum; i < (letter.getValue() + sum); i++) {
                    if (i >= choice) {
                        return letter.getKey();
                    }
                }
                sum += letter.getValue();
            }
        }
    }

    private static String generateWord() {
        int newWordLen;
        try {
            newWordLen = (Integer) wordLength.getRandomValue();
        } catch (Exception e) {
            newWordLen = (int) Math.floor((wordLength.Minimum + wordLength.Maximum) / 2);
        }
        StringBuilder newWord = new StringBuilder();

        while (newWord.length() != newWordLen) {
            newWord.append(PlaceholderGenerator.getRandomChar(letterFreq));
        }

        String output = newWord.toString();
        if (output.equals("i")) output = output.toUpperCase();
        return output;
    }

    private static String generateSentence() {
        int sentenceLen;
        try {
            sentenceLen = (Integer) sentenceLength.getRandomValue();
        } catch (Exception e) {
            sentenceLen = (int) Math.floor((sentenceLength.Minimum + sentenceLength.Maximum) / 2);
        }
        StringBuilder newSentence = new StringBuilder();

        while (Extensions.wordCount(newSentence.toString()) != sentenceLen) {
            newSentence.append(generateWord()).append(' ');
        }
        String output = newSentence.toString().trim() + getRandomChar(punctuationFreq);
        output = Character.toUpperCase(output.charAt(0)) + output.substring(1);

        return output.trim();
    }

    private static String generateParagraph() {
        int lineBreakChance = 15;
        int paragraphBreakChance = lineBreakChance / 2;

        StringBuilder output = new StringBuilder("<p>");
        while (true) {
            output.append(PlaceholderGenerator.generateSentence()).append(' ');
            double chance = random.nextDouble() * 100;

            if (chance <= lineBreakChance) {
                if (chance <= paragraphBreakChance) {
                    output.append("</p>");
                    break;
                }
                output.append("<br/>");
            }
        }
        return output.toString();
    }

    public static String generateRandomContent(int length, ContentType type) {
        try {
            StringBuilder output = new StringBuilder();
            // introduce some variance so we don't have output with the exact amount of words every time
            double offset = random.nextDouble();

            if (random.nextDouble() > 0.5) {
                length += (int) (offset * 1.618);
            } else {
                length -= (int) (offset * 1.618);
            }

            switch (type) {
                case Sentence:
                    for (int i = 0; i < length; i++) {
                        output.append(generateSentence()).append(' ');
                    }
                    break;
                case Paragraph:
                    for (int i = 0; i < length; i++) {
                        output.append(generateParagraph());
                    }
                    break;
            }
            return output.toString().trim();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static enum ContentType {Sentence, Paragraph}
}
