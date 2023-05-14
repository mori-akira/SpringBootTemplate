package jp.co.molygray.dao.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import jp.co.molygray.enums.GenderEnum;
import jp.co.molygray.model.DepartmentModel;
import jp.co.molygray.model.EmployeeModel;
import jp.co.molygray.model.EmployeeModel.EmployeeAddressModel;
import jp.co.molygray.model.QualificationModel;

/**
 * {@link EmployeeInfoDao}のテストクラス
 *
 * @author Moriaki Kogure
 * @version 0.0.1
 */
@SpringBootTest
public class EmployeeInfoDaoTest {

  /** 社員情報Dao */
  @Autowired
  private EmployeeInfoDao employeeInfoDao;

  /**
   * {@link EmployeeInfoDao#selectEmployeeInfo(Long, String)}のテストメソッド
   */
  @Test
  public void selectEmployeeInfoTest() {
    DepartmentModel department = DepartmentModel.builder()
        .departmentId(3l)
        .exclusiveFlg("xxx")
        .parentDepartmentId(2l)
        .departmentName("東京開発課")
        .departmentFullName("IT事業部　システム開発部　東京開発課")
        .build();
    EmployeeAddressModel employeeAddress = EmployeeAddressModel.builder()
        .employeeAddressId(1l)
        .exclusiveFlg("xxx")
        .country("日本")
        .prefecture("神奈川県")
        .city("川崎市")
        .ward("宮前区")
        .detail1("宮崎1-1-1")
        .detail2("XXXマンション　105号室")
        .build();
    List<QualificationModel> qualificationList = List.of(
        QualificationModel.builder()
            .qualificationId(1l)
            .exclusiveFlg("xxx")
            .qualificationName("ITパスポート試験")
            .qualificationAbbreviatedName("IP")
            .provider("IPA")
            .build(),
        QualificationModel.builder()
            .qualificationId(2l)
            .exclusiveFlg("xxx")
            .qualificationName("基本情報技術者試験")
            .qualificationAbbreviatedName("FE")
            .provider("IPA")
            .build(),
        QualificationModel.builder()
            .qualificationId(4l)
            .exclusiveFlg("xxx")
            .qualificationName("ORACLE MASTER Bronze Oracle Database 12c")
            .provider("Oracle")
            .build(),
        QualificationModel.builder()
            .qualificationId(5l)
            .exclusiveFlg("xxx")
            .qualificationName("Oracle Certified Java Programmer, Silver SE 8")
            .provider("Oracle")
            .build(),
        QualificationModel.builder()
            .qualificationId(6l)
            .exclusiveFlg("xxx")
            .qualificationName("Oracle Certified Java Programmer, Gold SE 8")
            .provider("Oracle")
            .build(),
        QualificationModel.builder()
            .qualificationId(7l)
            .exclusiveFlg("xxx")
            .qualificationName("HTML5プロフェッショナル認定 レベル1")
            .qualificationAbbreviatedName("HTML5(CSS3) レベル1")
            .validPeriodYears(5)
            .provider("LPI")
            .build(),
        QualificationModel.builder()
            .qualificationId(8l)
            .exclusiveFlg("xxx")
            .qualificationName("HTML5プロフェッショナル認定 レベル2")
            .qualificationAbbreviatedName("HTML5(CSS3) レベル2")
            .validPeriodYears(5)
            .provider("LPI")
            .build(),
        QualificationModel.builder()
            .qualificationId(9l)
            .exclusiveFlg("xxx")
            .qualificationName("Python 3 エンジニア認定基礎試験")
            .provider("一般社団法人Pythonエンジニア育成推進協会")
            .build(),
        QualificationModel.builder()
            .qualificationId(10l)
            .exclusiveFlg("xxx")
            .qualificationName("Google Certification Associate Cloud Engineer")
            .validPeriodYears(3)
            .provider("Google")
            .build());
    EmployeeModel expected = EmployeeModel.builder()
        .employeeId(1l)
        .employeeNumber("0000000001")
        .exclusiveFlg("xxx")
        .sei("佐藤")
        .mei("太郎")
        .seiKana("サトウ")
        .meiKana("タロウ")
        .gender(GenderEnum.MAN)
        .birthDate(
            ZonedDateTime.of(1996, 4, 15, 0, 0, 0, 0, ZoneId.systemDefault()).toOffsetDateTime())
        .hireDate(
            ZonedDateTime.of(2022, 1, 1, 0, 0, 0, 0, ZoneId.systemDefault()).toOffsetDateTime())
        .department(department)
        .employeeAddress(employeeAddress)
        .qualificationList(qualificationList)
        .build();
    EmployeeModel actual = employeeInfoDao.selectEmployeeInfo(1l, null).orElse(null);
    assertEquals(expected, actual);
    actual = employeeInfoDao.selectEmployeeInfo(null, "0000000001").orElse(null);
    assertEquals(expected, actual);
    actual = employeeInfoDao.selectEmployeeInfo(1l, "0000000001").orElse(null);
    assertEquals(expected, actual);

    // リスト要素が空の場合
    employeeAddress = EmployeeAddressModel.builder()
        .employeeAddressId(3l)
        .exclusiveFlg("xxx")
        .country("日本")
        .prefecture("東京都")
        .city(null)
        .ward("大田区")
        .detail1("北糀谷1-1-1")
        .detail2("XXXアパート　203号室")
        .build();
    expected = EmployeeModel.builder()
        .employeeId(3l)
        .exclusiveFlg("xxx")
        .employeeNumber("0000000003")
        .sei("青木")
        .mei("愛華")
        .seiKana("アオキ")
        .meiKana("アイカ")
        .gender(GenderEnum.WOMAN)
        .birthDate(
            ZonedDateTime.of(1996, 12, 1, 0, 0, 0, 0, ZoneId.systemDefault()).toOffsetDateTime())
        .hireDate(
            ZonedDateTime.of(2020, 4, 1, 0, 0, 0, 0, ZoneId.systemDefault()).toOffsetDateTime())
        .department(department)
        .employeeAddress(employeeAddress)
        .qualificationList(Collections.emptyList())
        .build();
    actual = employeeInfoDao.selectEmployeeInfo(3l, null).orElse(null);
    assertEquals(expected, actual);
  }
}
