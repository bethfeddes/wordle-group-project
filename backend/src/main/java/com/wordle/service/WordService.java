package com.wordle.service;

import java.util.*;

public class WordService {

    private Set<String> wordVault = new HashSet<>();
    private List<String> availableWords = new ArrayList<>();

    public WordService() {
        initializeWordVault();
    }

    private void initializeWordVault() {
        // Basic 5-letter English words as fallback
        String[] commonWords = {
                "ABOUT", "ABOVE", "ABUSE", "ACTOR", "ACUTE", "ADMIT", "ADOPT", "ADULT", "AFTER", "AGAIN",
                "AGENT", "AGREE", "AHEAD", "ALARM", "ALBUM", "ALERT", "ALIEN", "ALIGN", "ALIKE", "ALIVE",
                "ALLOW", "ALONE", "ALONG", "ALTER", "ANGEL", "ANGER", "ANGLE", "ANGRY", "APART", "APPLE",
                "APPLY", "ARENA", "ARGUE", "ARISE", "ARRAY", "ASIDE", "ASSET", "AVOID", "AWAKE", "AWARE",
                "BADLY", "BAKER", "BASES", "BASIC", "BEACH", "BEGAN", "BEING", "BELOW", "BENCH", "BILLY",
                "BIRTH", "BLACK", "BLAME", "BLIND", "BLOCK", "BLOOD", "BOARD", "BOOST", "BOOTH", "BOUND",
                "BRAIN", "BRAND", "BRAVE", "BREAD", "BREAK", "BREED", "BRIEF", "BRING", "BROAD", "BROKE",
                "BROWN", "BUILD", "BUILT", "BUYER", "CABLE", "CARRY", "CATCH", "CAUSE", "CHAIN",
                "CHAIR", "CHAOS", "CHARM", "CHART", "CHASE", "CHEAP", "CHECK", "CHEST", "CHIEF", "CHILD",
                "CHINA", "CHOSE", "CIVIL", "CLAIM", "CLASS", "CLEAN", "CLEAR", "CLIMB", "CLOCK", "CLOSE",
                "CLOUD", "COACH", "COAST", "COULD", "COUNT", "COURT", "COVER", "CRAFT", "CRASH", "CRAZY",
                "CREAM", "CRIME", "CROSS", "CROWD", "CROWN", "CRUDE", "CURVE", "CYCLE", "DAILY", "DANCE",
                "DATED", "DEALT", "DEATH", "DEBUT", "DELAY", "DELTA", "DENSE", "DEPOT", "DEPTH", "DERBY",
                "DIGIT", "DIRTY", "DOING", "DOUBT", "DOZEN", "DRAFT", "DRAMA", "DRANK", "DRAWN",
                "DREAM", "DRESS", "DRIED", "DRILL", "DRINK", "DRIVE", "DROVE", "DYING", "EAGER", "EARLY",
                "EARTH", "EIGHT", "ELITE", "EMPTY", "ENEMY", "ENJOY", "ENTER", "ENTRY", "EQUAL",
                "ERROR", "EVENT", "EVERY", "EXACT", "EXIST", "EXTRA", "FAITH", "FALSE", "FANCY", "FAULT",
                "FENCE", "FIBER", "FIELD", "FIFTH", "FIFTY", "FIGHT", "FINAL", "FIRST", "FIXED", "FLASH",
                "FLEET", "FLESH", "FLOOR", "FLUID", "FOCUS", "FORCE", "FORTH", "FORTY", "FORUM",
                "FOUND", "FRAME", "FRANK", "FRAUD", "FRESH", "FRONT", "FRUIT", "FULLY", "FUNNY", "GIANT",
                "GIVEN", "GLASS", "GLOBE", "GOING", "GRACE", "GRADE", "GRAIN", "GRAND", "GRANT", "GRASS",
                "GRAVE", "GREAT", "GREEN", "GROSS", "GROUP", "GROWN", "GUARD", "GUESS", "GUEST", "GUIDE",
                "GUILD", "HABIT", "HAPPY", "HARRY", "HEART", "HEAVY", "HENCE", "HENRY", "HORSE", "HOTEL",
                "HOUSE", "HUMAN", "IDEAL", "IMAGE", "IMPLY", "INDEX", "INNER", "INPUT", "ISSUE", "JAPAN",
                "JIMMY", "JOINT", "JONES", "JUDGE", "KNOWN", "LABEL", "LARGE", "LASER", "LATER", "LAUGH",
                "LAYER", "LEARN", "LEASE", "LEAST", "LEAVE", "LEGAL", "LEMON", "LEVEL", "LEWIS", "LIGHT",
                "LIMIT", "LINKS", "LIVES", "LOCAL", "LOGIC", "LOOSE", "LOWER", "LUCKY", "LUNCH", "LYING",
                "MAGIC", "MAJOR", "MAKER", "MARCH", "MARIA", "MATCH", "MAYBE", "MAYOR", "MEANT", "MEDIA",
                "METAL", "MIGHT", "MINOR", "MINUS", "MIXED", "MODEL", "MONEY", "MONTH", "MORAL", "MOTOR",
                "MOUNT", "MOUSE", "MOUTH", "MOVED", "MOVIE", "MUSIC", "NEEDS", "NEVER", "NEWLY", "NIGHT",
                "NOISE", "NORTH", "NOTED", "NOVEL", "NURSE", "OCCUR", "OCEAN", "OFFER", "OFTEN", "ORDER",
                "OTHER", "OUGHT", "OUTER", "OWNED", "OWNER", "PAINT", "PANEL", "PAPER", "PARIS", "PARTY",
                "PEACE", "PENNY", "PETER", "PHASE", "PHONE", "PHOTO", "PIANO", "PIECE", "PILOT", "PITCH",
                "PLACE", "PLAIN", "PLANE", "PLANT", "PLATE", "PLAZA", "POINT", "POUND", "POWER", "PRESS",
                "PRICE", "PRIDE", "PRIME", "PRINT", "PRIOR", "PRIZE", "PROOF", "PROUD", "PROVE", "QUEEN",
                "QUICK", "QUIET", "QUITE", "RADIO", "RAISE", "RANGE", "RAPID", "RATIO", "REACH", "READY",
                "REALM", "REFER", "RELAX", "REPLY", "RIDER", "RIDGE", "RIFLE", "RIGHT", "RIGID", "RIVER",
                "ROBIN", "ROCKY", "ROGER", "ROMAN", "ROUGH", "ROUND", "ROUTE", "ROYAL", "RURAL", "SCALE",
                "SCENE", "SCOPE", "SCORE", "SCREW", "SENSE", "SERVE", "SEVEN", "SHALL", "SHAPE", "SHARE",
                "SHARP", "SHEET", "SHELF", "SHELL", "SHIFT", "SHINE", "SHIRT", "SHOCK", "SHOOT", "SHORT",
                "SHOWN", "SIGHT", "SILLY", "SIMON", "SINCE", "SIXTH", "SIXTY", "SIZED", "SKILL", "SLASH",
                "SLEEP", "SLIDE", "SLOPE", "SMALL", "SMART", "SMILE", "SMITH", "SMOKE", "SOLID", "SOLVE",
                "SORRY", "SOUND", "SOUTH", "SPACE", "SPARE", "SPEAK", "SPEED", "SPEND", "SPENT", "SPLIT",
                "SPOKE", "SPORT", "STAFF", "STAGE", "STAKE", "STAND", "START", "STATE", "STEAM", "STEEL",
                "STICK", "STILL", "STOCK", "STONE", "STOOD", "STORE", "STORM", "STORY", "STRIP", "STUCK",
                "STUDY", "STUFF", "STYLE", "SUGAR", "SUITE", "SUNNY", "SUPER", "SURGE", "SWEET", "SWIFT",
                "SWING", "SWORD", "TABLE", "TAKEN", "TASTE", "TAXES", "TEACH", "TEETH", "TEMPO", "TENDS",
                "TENTH", "TEXAS", "THANK", "THEFT", "THEIR", "THEME", "THERE", "THESE", "THICK", "THING",
                "THINK", "THIRD", "THOSE", "THREE", "THREW", "THROW", "THUMB", "TIGHT", "TIMER", "TITLE",
                "TODAY", "TOPIC", "TOTAL", "TOUCH", "TOUGH", "TOWER", "TRACK", "TRADE", "TRAIN",
                "TRASH", "TREAT", "TREND", "TRIAL", "TRIBE", "TRICK", "TRIED", "TRIES", "TROOP", "TRUCK",
                "TRULY", "TRUMP", "TRUST", "TRUTH", "TWICE", "TWINS", "UNCLE", "UNDER", "UNDUE", "UNION",
                "UNITY", "UNTIL", "UPPER", "UPSET", "URBAN", "USAGE", "USUAL", "VALID", "VALUE", "VIDEO",
                "VIRUS", "VISIT", "VITAL", "VOCAL", "VOICE", "WASTE", "WATCH", "WATER", "WHEEL", "WHERE",
                "WHICH", "WHILE", "WHITE", "WHOLE", "WHOSE", "WOMAN", "WOMEN", "WORLD", "WORRY", "WORSE",
                "WORST", "WORTH", "WOULD", "WOUND", "WRITE", "WRONG", "WROTE", "YIELD", "YOUNG", "YOURS",
                "YOUTH", "ZEBRA"
        };

        wordVault.clear();
        availableWords.clear();

        for (String word : commonWords) {
            if (word.length() == 5) {
                wordVault.add(word);
                availableWords.add(word);
            }
        }
    }

    public String getRandomWord() {
        if (availableWords.isEmpty()) {
            throw new RuntimeException("No words available in the vault");
        }
        return availableWords.get(new Random().nextInt(availableWords.size()));
    }

    public boolean containsWord(String word) {
        return wordVault.contains(word.toUpperCase());
    }

    public Set<String> getWordVault() {
        return new HashSet<>(wordVault);
    }

    public int getWordCount() {
        return wordVault.size();
    }
}
