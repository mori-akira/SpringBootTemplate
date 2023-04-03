package jp.co.molygray;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Arrays;
import java.util.List;

import jp.co.molygray.constraints.FormatDate;

public class Debug {

    public static void main(String[] args) {

        Arrays.stream(FormatDate.class.getDeclaredMethods())
                .forEach(System.out::println);
        Arrays.stream(FormatDate.class.getDeclaredFields())
                .forEach(System.out::println);

        String format = "uuuu/MM/dd";
        List<String> values = Arrays.asList("2023/02/29", "2023/04/01");
        for (String value : values) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format)
                        .withResolverStyle(ResolverStyle.STRICT);
                LocalDate date = LocalDate.parse(value, formatter);
                System.out.println(date);
            } catch (DateTimeParseException ex) {
                ex.printStackTrace();
            }
        }

        System.out.println(Long.valueOf("") == null);
    }
}
