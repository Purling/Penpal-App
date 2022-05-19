package com.example.comp2100_assignment;

/***
 * A tokenizer which reads in a string and turns them into tokens
 *
 * @author Xingkun Chen
 */
public class Tokenizer {

        private String buffer;
        private Token currentToken;

        public Tokenizer(String text) {
            buffer = text;
            next();
        }
        public void next() {
            buffer = buffer.trim();

            if (buffer.isEmpty()) {
                currentToken = null;
                return;
            }

            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < buffer.length(); i++) {
                if (buffer.charAt(i) == '(') {
                    stringBuilder.append(buffer.charAt(i));
                    stringBuilder.append(" ");
                } else if (buffer.charAt(i) == ')') {

                    stringBuilder.append(buffer.charAt(i));
                    stringBuilder.append(" ");
                } else
                    stringBuilder.append(buffer.charAt(i));
            }
            String newString = stringBuilder.toString();
            String[] words = newString.split(" ");

            if (words[0].trim().equals("(")) {
                currentToken = new Token("(", Token.Type.LBRA);
            } else if (words[0].equals(")")) {
                currentToken = new Token(")", Token.Type.RBRA);
            } else if (words[0].equalsIgnoreCase("and")) {
                currentToken = new Token("and", Token.Type.AND);
            } else if (words[0].equalsIgnoreCase("or")) {
                currentToken = new Token("or", Token.Type.OR);
            } else if (words[0].equalsIgnoreCase("not")) {
                currentToken = new Token("not", Token.Type.NOT);
            }else if (words[0].equalsIgnoreCase("english")) {
                currentToken = new Token("ENGLISH", Token.Type.ENGLISH);
            }else if (words[0].equalsIgnoreCase("ITALIAN")) {
                currentToken = new Token("ITALIAN", Token.Type.ITALIAN);
            }
            else if (words[0].equalsIgnoreCase("GERMAN")) {
                currentToken = new Token("GERMAN", Token.Type.GERMAN);
            }
            else if (words[0].equalsIgnoreCase("FRENCH")) {
                currentToken = new Token("FRENCH", Token.Type.FRENCH);
            }
            else if (words[0].equalsIgnoreCase("JAPANESE")) {
                currentToken = new Token("JAPANESE", Token.Type.JAPANESE);
            }
            else if (words[0].equalsIgnoreCase("KOREAN")) {
                currentToken = new Token("KOREAN", Token.Type.KOREAN);
            }
            else if (words[0].equalsIgnoreCase("MANDARIN")) {
                currentToken = new Token("MANDARIN", Token.Type.MANDARIN);
            }
            else if (words[0].equalsIgnoreCase("MUSIC")) {
                currentToken = new Token("MUSIC", Token.Type.MUSIC);
            }
            else if (words[0].equalsIgnoreCase("SPORTS")) {
                currentToken = new Token("SPORTS", Token.Type.SPORTS);
            }
            else if (words[0].equalsIgnoreCase("FOOD")) {
                currentToken = new Token("FOOD", Token.Type.FOOD);
            }
            else if (words[0].equalsIgnoreCase("TRAVEL")) {
                currentToken = new Token("TRAVEL", Token.Type.TRAVEL);
            }
            else
                currentToken = new Token("word", Token.Type.WORD);
            if (currentToken.getType() != Token.Type.WORD)  {
                int tokenLen = currentToken.getToken().length();
                buffer = buffer.substring(tokenLen);
            }
        }
        public Token current() {
            return currentToken;
        }

        public boolean hasNext() {
            return currentToken != null;
        }
}
