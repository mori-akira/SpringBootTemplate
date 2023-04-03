package jp.co.molygray;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * アプリのメインクラス
 *
 * @author Moriaki Kogure
 * @version 0.0.1
 */
@SpringBootApplication
@MapperScan(basePackages = "jp.co.molygray.dao")
public class App {

    /**
     * メイン関数
     *
     * @param args コマンド引数
     */
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
