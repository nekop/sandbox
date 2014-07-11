package sandbox;

import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Map;
import java.util.LinkedHashMap;

// https://issues.jboss.org/browse/JIPI-32

public class SlowStringReplace {

    // HQL symbol or operators
    private static final String SQL_NE = "<>";
    private static final String NE_BANG = "!=";
    private static final String NE_HAT = "^=";
    private static final String LE = "<=";
    private static final String GE = ">=";
    private static final String CONCAT = "||";
    private static final String LT = "<";
    private static final String EQ = "=";
    private static final String GT = ">";
    private static final String OPEN = "(";
    private static final String CLOSE = ")";
    private static final String OPEN_BRACKET = "[";
    private static final String CLOSE_BRACKET = "]";
    private static final String PLUS = "+";
    private static final String MINUS = "-";
    private static final String STAR = "*";
    private static final String DIV = "/";
    private static final String MOD = "%";
    private static final String COLON = ":";
    private static final String PARAM = "?";
    private static final String COMMA = ",";
    private static final String SPACE = " ";
    private static final String TAB = "\t";
    private static final String NEWLINE = "\n";
    private static final String LINEFEED = "\r";
    private static final String QUOTE = "'";
    private static final String DQUOTE = "\"";
    private static final String TICK = "`";
    private static final String OPEN_BRACE = "{";
    private static final String CLOSE_BRACE = "}";
    private static final String HAT = "^";
    private static final String AMPERSAND = "&";

    // textual representation (not to be localized as we don't won't duplication between any of the values)
    private static final String NOT_EQUAL__ = "_not_equal_";
    private static final String BANG_NOT_EQUAL__ = "_bang_not_equal_";
    private static final String HAT_NOT_EQUAL__ = "_hat_not_equal_";
    private static final String LESS_THAN_EQUAL__ = "_less_than_equal_";
    private static final String GREATER_THAN_EQUAL__ = "_greater_than_equal_";
    private static final String CONCAT__ = "_concat_";
    private static final String LESS_THAN__ = "_less_than_";
    private static final String EQUAL__ = "_equal_";
    private static final String GREATER__ = "_greater_";
    private static final String LEFT_PAREN__ = "_left_paren_";
    private static final String RIGHT_PAREN__ = "_right_paren_";
    private static final String LEFT_BRACKET__ = "_left_bracket_";
    private static final String RIGHT_BRACKET__ = "_right_bracket_";
    private static final String PLUS__ = "_plus_";
    private static final String MINUS__ = "_minus_";
    private static final String STAR__ = "_star_";
    private static final String DIVIDE__ = "_divide_";
    private static final String MODULUS__ = "_modulus_";
    private static final String COLON__ = "_colon_";
    private static final String PARAM__ = "_param_";
    private static final String COMMA__ = "_comma_";
    private static final String SPACE__ = "_space_";
    private static final String TAB__ = "_tab_";
    private static final String NEWLINE__ = "_newline_";
    private static final String LINEFEED__ = "_linefeed_";
    private static final String QUOTE__ = "_quote_";
    private static final String DQUOTE__ = "_double_quote_";
    private static final String TICK__ = "_tick_";
    private static final String OPEN_BRACE__ = "_left_brace_";
    private static final String CLOSE_BRACE__ = "_right_brace_";
    private static final String HAT__ = "_hat_";
    private static final String AMPERSAND__ = "_ampersand_";

    private static String displayable(String query) {
        if (query == null ||
            query.length() == 0) {
            return query;
        }

        // handle two character transforms first
        query = query.replace(SQL_NE, NOT_EQUAL__);
        query = query.replace(NE_BANG, BANG_NOT_EQUAL__);
        query = query.replace(NE_HAT, HAT_NOT_EQUAL__);
        query = query.replace(LE, LESS_THAN_EQUAL__);
        query = query.replace(GE, GREATER_THAN_EQUAL__);
        query = query.replace(CONCAT, CONCAT__);
        query = query.replace(LT, LESS_THAN__);
        query = query.replace(EQ, EQUAL__);
        query = query.replace(GT, GREATER__);
        query = query.replace(OPEN, LEFT_PAREN__);
        query = query.replace(CLOSE, RIGHT_PAREN__);
        query = query.replace(OPEN_BRACKET, LEFT_BRACKET__);
        query = query.replace(CLOSE_BRACKET, RIGHT_BRACKET__);
        query = query.replace(PLUS, PLUS__);
        query = query.replace(MINUS, MINUS__);
        query = query.replace(STAR, STAR__);
        query = query.replace(DIV, DIVIDE__);
        query = query.replace(MOD, MODULUS__);
        query = query.replace(COLON, COLON__);
        query = query.replace(PARAM, PARAM__);
        query = query.replace(COMMA, COMMA__);
        query = query.replace(SPACE, SPACE__);
        query = query.replace(TAB, TAB__);
        query = query.replace(NEWLINE, NEWLINE__);
        query = query.replace(LINEFEED, LINEFEED__);
        query = query.replace(QUOTE, QUOTE__);
        query = query.replace(DQUOTE, DQUOTE__);
        query = query.replace(TICK, TICK__);
        query = query.replace(OPEN_BRACE, OPEN_BRACE__);
        query = query.replace(CLOSE_BRACE, CLOSE_BRACE__);
        query = query.replace(HAT, HAT__);
        query = query.replace(AMPERSAND, AMPERSAND__);
        return query;
    }

    private static String displayable2(String query) {
        if (query == null ||
            query.length() == 0) {
            return query;
        }
        StringBuilder buff = new StringBuilder(query);

        // handle two character transforms first
        subst(buff, SQL_NE, NOT_EQUAL__);
        subst(buff, NE_BANG, BANG_NOT_EQUAL__);
        subst(buff, NE_HAT, HAT_NOT_EQUAL__);
        subst(buff, LE, LESS_THAN_EQUAL__);
        subst(buff, GE, GREATER_THAN_EQUAL__);
        subst(buff, CONCAT, CONCAT__);
        subst(buff, LT, LESS_THAN__);
        subst(buff, EQ, EQUAL__);
        subst(buff, GT, GREATER__);
        subst(buff, OPEN, LEFT_PAREN__);
        subst(buff, CLOSE, RIGHT_PAREN__);
        subst(buff, OPEN_BRACKET, LEFT_BRACKET__);
        subst(buff, CLOSE_BRACKET, RIGHT_BRACKET__);
        subst(buff, PLUS, PLUS__);
        subst(buff, MINUS, MINUS__);
        subst(buff, STAR, STAR__);
        subst(buff, DIV, DIVIDE__);
        subst(buff, MOD, MODULUS__);
        subst(buff, COLON, COLON__);
        subst(buff, PARAM, PARAM__);
        subst(buff, COMMA, COMMA__);
        subst(buff, SPACE, SPACE__);
        subst(buff, TAB, TAB__);
        subst(buff, NEWLINE, NEWLINE__);
        subst(buff, LINEFEED, LINEFEED__);
        subst(buff, QUOTE, QUOTE__);
        subst(buff, DQUOTE, DQUOTE__);
        subst(buff, TICK, TICK__);
        subst(buff, OPEN_BRACE, OPEN_BRACE__);
        subst(buff, CLOSE_BRACE, CLOSE_BRACE__);
        subst(buff, HAT, HAT__);
        subst(buff, AMPERSAND, AMPERSAND__);
        return buff.toString();
    }

    private static void subst(final StringBuilder stringBuilder, final String from, final String to) {
        int begin = 0, end = 0;

        while ((end = stringBuilder.indexOf(from, end)) != -1) {
            stringBuilder.delete(end, end + from.length());
            stringBuilder.insert(end, to);

            // update positions
            begin = end + to.length();
            end = begin;
        }
    }

    static Map<String,String> tokens = new LinkedHashMap<String,String>();
    static Pattern pattern = null;
    static {
        tokens.put(SQL_NE, NOT_EQUAL__);
        tokens.put(NE_BANG, BANG_NOT_EQUAL__);
        tokens.put(NE_HAT, HAT_NOT_EQUAL__);
        tokens.put(LE, LESS_THAN_EQUAL__);
        tokens.put(GE, GREATER_THAN_EQUAL__);
        tokens.put(CONCAT, CONCAT__);
        tokens.put(LT, LESS_THAN__);
        tokens.put(EQ, EQUAL__);
        tokens.put(GT, GREATER__);
        tokens.put(OPEN, LEFT_PAREN__);
        tokens.put(CLOSE, RIGHT_PAREN__);
        tokens.put(OPEN_BRACKET, LEFT_BRACKET__);
        tokens.put(CLOSE_BRACKET, RIGHT_BRACKET__);
        tokens.put(PLUS, PLUS__);
        tokens.put(MINUS, MINUS__);
        tokens.put(STAR, STAR__);
        tokens.put(DIV, DIVIDE__);
        tokens.put(MOD, MODULUS__);
        tokens.put(COLON, COLON__);
        tokens.put(PARAM, PARAM__);
        tokens.put(COMMA, COMMA__);
        tokens.put(SPACE, SPACE__);
        tokens.put(TAB, TAB__);
        tokens.put(NEWLINE, NEWLINE__);
        tokens.put(LINEFEED, LINEFEED__);
        tokens.put(QUOTE, QUOTE__);
        tokens.put(DQUOTE, DQUOTE__);
        tokens.put(TICK, TICK__);
        tokens.put(OPEN_BRACE, OPEN_BRACE__);
        tokens.put(CLOSE_BRACE, CLOSE_BRACE__);
        tokens.put(HAT, HAT__);
        tokens.put(AMPERSAND, AMPERSAND__);

        StringBuilder patternString = new StringBuilder();
        patternString.append("(");
        boolean append = false;
        for (String key : tokens.keySet()) {
            patternString.append(Pattern.quote(key));
            patternString.append("|");
            append = true;
        }
        if (append) {
            // remove training "|" char
            patternString.deleteCharAt(patternString.length() - 1);
        }
        patternString.append(")");
        pattern = Pattern.compile(patternString.toString());
    }

    private static String displayable3(String query) {
        if (query == null ||
            query.length() == 0) {
            return query;
        }

        Matcher matcher = pattern.matcher(query);
        StringBuffer sb = new StringBuffer();
        while(matcher.find()) {
            matcher.appendReplacement(sb, tokens.get(matcher.group(1)));
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    public static void main(String[] args) {

        String test = "select * from employee e (where e.id <> 0 and e.name ='foo' and e.other != 'other'";
        {
            long start = System.currentTimeMillis();
            String result = null;
            for ( int looper = 0; looper < 500000; looper ++) {
                result = displayable3(test);
            }
            long end = System.currentTimeMillis();
            System.out.println("took " + (end- start) + "ms, display name=" + result);
        }
        {
            long start = System.currentTimeMillis();
            String result = null;
            for ( int looper = 0; looper < 500000; looper ++) {
                result = displayable2(test);
            }
            long end = System.currentTimeMillis();
            System.out.println("took " + (end- start) + "ms, display name=" + result);
        }
        {
            long start = System.currentTimeMillis();
            String result = null;
            for ( int looper = 0; looper < 500000; looper ++) {
                result = displayable(test);
            }
            long end = System.currentTimeMillis();
            System.out.println("took " + (end- start) + "ms, display name=" + result);
        }


    }

}
