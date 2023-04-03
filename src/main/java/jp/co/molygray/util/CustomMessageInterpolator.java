package jp.co.molygray.util;

import java.util.Locale;
import java.util.Spliterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hibernate.validator.internal.engine.MessageInterpolatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import jakarta.validation.MessageInterpolator;
import jakarta.validation.Path;
import jakarta.validation.Path.Node;

/**
 * メッセージを構成する処理をカスタムで作成するためのクラス
 * <p>
 * バリデーションメッセージ中のフォーマット識別子がデフォルトのMessageInterpolatorだと置換されないため、独自実装する。
 * </p>
 *
 * @author Moriaki Kogure
 * @version 0.0.1
 */
public class CustomMessageInterpolator implements MessageInterpolator {

    /** カッコ始め */
    public static final String BRACE_OPEN = "{";
    /** カッコ終わり */
    public static final String BRACE_CLOSE = "}";
    /** メッセージ中のフィールドキーワード */
    public static final String KEYWORD_FIELD = "field";
    /** メッセージ中の入力値キーワード */
    public static final String KEYWORD_VALUE = "value";

    /** メッセージソース */
    @Autowired
    private MultiMessageSource messageSource;

    /**
     * {@inheritDoc}
     */
    @Override
    public String interpolate(String messageTemplate, Context context) {
        return interpolate(messageTemplate, context, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String interpolate(String messageTemplate, Context context, Locale locale) {
        String ret = getOriginalMessage(messageTemplate);
        ret = replaceParameter(ret, MessageInterpolatorContext.class.cast(context));
        return ret;
    }

    /**
     * フィールド置換前のオリジナルメッセージを取得するメソッド
     *
     * @param messageTemplate メッセージテンプレート
     * @return オリジナルメッセージ
     * @throws NoSuchMessageExeption メッセージキーに対応するメッセージが存在しない場合
     */
    private String getOriginalMessage(String messageTemplate) {
        // 全体がカッコで囲まれていない場合、そのままメッセージを使用
        if (!messageTemplate.startsWith(BRACE_OPEN) || !messageTemplate.endsWith(BRACE_CLOSE)) {
            return messageTemplate;
        }
        String key = messageTemplate.substring(1, messageTemplate.length() - 1);
        return messageSource.getMessage(key);
    }

    /**
     * メッセージ中のパラメータ識別子を置換するメソッド
     *
     * @param message メッセージ
     * @param context メッセージ構成のコンテキスト
     * @return パラメータ置換後メッセージ
     */
    private String replaceParameter(String message, MessageInterpolatorContext context) {
        String ret = message;
        String fieldName = getFieldName(context.getPropertyPath());
        // メッセージ中にフィールド識別子が存在する場合、フィールドで置換
        String fieldPlaceholder = buildPlaceholder(KEYWORD_FIELD);
        if (ret.contains(fieldPlaceholder)) {
            // メッセージソースからフィールド名を取得
            // フィールド名が見つからない場合、キーをそのままフィールド名とする
            String field = messageSource.getMessage(fieldName);
            ret = ret.replace(fieldPlaceholder, field);
        }

        // メッセージ中に入力値識別子が存在する場合、入力値で置換
        String valuePlaceholder = buildPlaceholder(KEYWORD_VALUE);
        if (ret.contains(valuePlaceholder)) {
            ret = ret.replace(valuePlaceholder, context.getValidatedValue()
                    .toString());
        }

        // メッセージに残ったプレースホルダを順に置換
        String keyword = searchPlaceholder(ret);
        while (keyword != null) {
            // コンテキストからキーワードで値を取得
            String value = context.getConstraintDescriptor()
                    .getAttributes()
                    .get(keyword)
                    .toString();
            if (value != null) {
                // 取得できる場合は取得値で置換
                ret = ret.replace(buildPlaceholder(keyword), value);
            } else {
                // 取得できない場合は空文字で置換
                ret = ret.replace(buildPlaceholder(keyword), "");
            }
            keyword = searchPlaceholder(ret);
        }
        return ret;
    }

    /**
     * {@link Path}からフィールド名を取得するメソッド
     *
     * @param propertyPath プロパティパス
     * @return フィールド名
     */
    private static String getFieldName(Path propertyPath) {
        Spliterator<Node> spliterator = propertyPath.spliterator();
        StringBuilder ret = new StringBuilder("");
        spliterator.tryAdvance(e -> ret.append(e.getName()));
        return ret.toString();
    }

    /**
     * 文字列中のプレースホルダを構成するメソッド
     *
     * @param keyword キーワード
     * @return プレースホルダ
     */
    private static String buildPlaceholder(Object keyword) {
        return BRACE_OPEN.concat(keyword.toString())
                .concat(BRACE_CLOSE);
    }

    /**
     * メッセージ中のプレースホルダのキーワードを取得するメソッド
     * <p>
     * メッセージ中にプレースホルダが存在しない場合、nullを返却する。
     * </p>
     *
     * @param message メッセージ
     * @return プレースホルダのキーワード (プレースホルダが存在しない場合はnull)
     */
    private static String searchPlaceholder(String message) {
        Matcher matcher = Pattern.compile("\\{(.*?)\\}")
                .matcher(message);
        if (matcher.find(1)) {
            return matcher.group(1);
        } else {
            return null;
        }
    }
}
