package jp.co.molygray.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.core.io.ClassPathResource;
import org.yaml.snakeyaml.Yaml;

/**
 * テストコード用に、Liquibaseのyamlを読み込み、データを構造化するクラス
 *
 * @author Moriaki Kogure
 * @version 0.0.1
 */
@SuppressWarnings("unchecked")
public class LiquibaseDataLoader {

  public static final String DIR_PATH_LIQUIBASE_TOP = "db/liquibase";
  public static final String DIR_PATH_DML = "dml";
  public static final String FILE_SUFFIX_LIQUIBASE = ".yaml";

  public static Map<String, Object> loadData(String fileName) {
    Map<String, Object> ret = new HashMap<>();
    try {
      InputStream is =
          new ClassPathResource(String.join("/", DIR_PATH_LIQUIBASE_TOP, DIR_PATH_DML, fileName)
              + FILE_SUFFIX_LIQUIBASE)
                  .getInputStream();
      Map<String, Object> data = new Yaml().loadAs(is, Map.class);
      List<Object> changeSetList = List.class.cast(data.get("databaseChangeLog"));
      Map<String, Object> changeSet = Map.class.cast(changeSetList.get(0));
      List<Object> changes = List.class.cast(changeSet.get("changes"));
      Map<String, Object> insert = Map.class.cast(Map.class.cast(changes.get(0))
          .get("insert"));
      List<Object> columns = List.class.cast(insert);
      System.out.println(data);
    } catch (IOException ex) {
      throw new RuntimeException("Failed to load test data.", ex);
    }
    return ret;
  }

  public static <T> T loadData(String fileName,
      Class<T> clazz) {
    return null;
  }

  public static void main(String[] args) {
    loadData("data_department");
  }
}
