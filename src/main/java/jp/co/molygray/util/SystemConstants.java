package jp.co.molygray.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;

/**
 * システムの定数を管理するクラス
 *
 * @author Moriaki Kogure
 * @version 0.0.1
 */
@Component
public class SystemConstants {

    /** 定数を定義するファイルパス */
    public static String FILE_PATH = "system-constants.yaml";
    /** 定数のマッパー */
    private Map<String, Object> constantsMapper;

    /**
     * デフォルトコンストラクタ
     *
     * @throws IOException ファイルの読み込みに失敗した場合
     */
    @SuppressWarnings("unchecked")
    public SystemConstants() throws IOException {
        try (InputStream inputStream = new ClassPathResource("system-constants.yaml")
                .getInputStream()) {
            constantsMapper = new Yaml().loadAs(inputStream, Map.class);
            constantsMapper = flattenYamlTree(constantsMapper);
        }
    }

    /**
     * 定数を取得するメソッド
     *
     * @param key 定数キー
     * @return 定数値
     * @throws NoSuchElementException キーに対応する定数が存在しない場合
     */
    public String getConstant(String key) {
        if (constantsMapper.containsKey(key)) {
            return constantsMapper.get(key)
                    .toString();
        } else {
            throw new NoSuchElementException(String.format("no constants key: %s", key));
        }
    }

    /**
     * デフォルト値を指定し、定数を取得するメソッド
     *
     * @param key 定数キー
     * @param defaultValue デフォルト値
     * @return 定数値 または デフォルト値
     */
    public String getConstantWithDefault(String key, String defaultValue) {
        return constantsMapper.getOrDefault(key, defaultValue)
                .toString();
    }

    /**
     * yamlとして読み込んだ{@link Map}を平坦化するメソッド
     *
     * @param map yamlとして読み込んだ{@link Map}
     * @return 平坦化した{@link Map}
     */
    private static Map<String, Object> flattenYamlTree(Map<?, ?> map) {
        return map.entrySet()
                .stream()
                .flatMap(SystemConstants::flattenYamlTree)
                .collect(Collectors.toMap(
                        e -> e.getKey(), e -> e.getValue(), (
                                oldValue, newValue
                        ) -> newValue));
    }

    /**
     * {@link #flattenYamlTree(Map)}内部で使用するメソッド
     */
    private static Stream<Entry<String, Object>> flattenYamlTree(Entry<?, ?> entry) {
        String key = entry.getKey()
                .toString();
        Object value = entry.getValue();
        if (value instanceof Map) {
            // 値がMapの場合は、キーをドットつなぎ
            Map<?, ?> valueAsMap = (Map<?, ?>) value;
            return valueAsMap.entrySet()
                    .stream()
                    .flatMap(SystemConstants::flattenYamlTree)
                    .map(e -> new SimpleImmutableEntry<>(key + "." + e.getKey(), e.getValue()));
        } else if (value instanceof List) {
            // 値がListの場合は、インデックスを[]で囲んだものをキー
            List<?> valueAsList = (List<?>) value;
            value = valueAsList.stream()
                    .toArray(String[]::new);
            AtomicInteger index = new AtomicInteger();
            return Stream.concat(
                    Stream.of(new SimpleImmutableEntry<>(key, value)), valueAsList.stream()
                            .map(v -> new SimpleImmutableEntry<>(
                                    key + "[" + index.getAndIncrement() + "]", v))
            );
        }
        return Stream.of(new SimpleImmutableEntry<>(key, value));
    }
}
