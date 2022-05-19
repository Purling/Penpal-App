package com.example.comp2100_assignment;

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

            if (words[0].equals("(")) {
                currentToken = new Token("(", Token.Type.LBRA);
            } else if (words[0].equals(")")) {
                currentToken = new Token(")", Token.Type.RBRA);
            } else if (words[0].equalsIgnoreCase("and")) {
                currentToken = new Token("and", Token.Type.AND);
            } else if (words[0].equalsIgnoreCase("or")) {
                currentToken = new Token("or", Token.Type.OR);
            } else if (words[0].equalsIgnoreCase("not")) {
                currentToken = new Token("not", Token.Type.NOT);
            }else
                currentToken = new Token("word", Token.Type.WORD);
            int tokenLen = currentToken.getToken().length();
            buffer = buffer.substring(tokenLen);
        }

        public Token current() {
            return currentToken;
        }

        public boolean hasNext() {
            return currentToken != null;
        }
}
