package jp.co.molygray.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import jp.co.molygray.dao.service.EmployeeInfoDao;
import jp.co.molygray.enums.GenderEnum;
import jp.co.molygray.model.DepartmentModel;
import jp.co.molygray.model.EmployeeModel;
import jp.co.molygray.model.EmployeeModel.EmployeeAddressModel;
import jp.co.molygray.model.QualificationModel;

/**
 * {@link EmployeeService}のテストクラス
 *
 * @author Moriaki Kogure
 * @version 0.0.1
 */
@SpringBootTest
public class EmployeeServiceTest {

  /** {@link EmployeeInfoDao}のモック・インスタンス */
  @Mock
  private EmployeeInfoDao employeeInfoDao;
  /** {@link EmployeeService}のモック・インスタンス */
  @InjectMocks
  private EmployeeService employeeService;
  /** Mockitoインスタンス */
  private AutoCloseable closeable;

  /**
   * 前処理メソッド
   */
  @BeforeEach
  public void init() {
    closeable = MockitoAnnotations.openMocks(this);
  }

  /**
   * 後処理メソッド
   *
   * @throws Exception 例外が発生した場合
   */
  @AfterEach
  public void tearDown()
      throws Exception {
    closeable.close();
  }

  /**
   * {@link EmployeeService#getEmployeeInfo(Long, String)}の正常系テストメソッド
   */
  @Test
  public void getEmployeeInfoTestOk() {
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
    EmployeeModel employee = EmployeeModel.builder()
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
    when(employeeInfoDao.selectEmployeeInfo(any(), any()))
        .thenReturn(Optional.of(employee));
    EmployeeModel actual = employeeService.getEmployeeInfo(1l, "001");
    assertEquals(employee, actual);
    verify(employeeInfoDao).selectEmployeeInfo(eq(1l), eq("001"));
    actual = employeeService.getEmployeeInfo(1l, null);
    assertEquals(employee, actual);
    verify(employeeInfoDao).selectEmployeeInfo(eq(1l), eq(null));
    actual = employeeService.getEmployeeInfo(null, "001");
    assertEquals(employee, actual);
    verify(employeeInfoDao).selectEmployeeInfo(eq(null), eq("001"));
  }

  /**
   * {@link EmployeeService#getEmployeeInfo(Long, String)}の異常系テストメソッド
   */
  @Test
  public void getEmployeeInfoTestNg() {
    when(employeeInfoDao.selectEmployeeInfo(any(), any()))
        .thenReturn(Optional.of(new EmployeeModel()));
    assertThrowsExactly(IllegalArgumentException.class, () -> employeeService.getEmployeeInfo(null, null));
    verify(employeeInfoDao, never()).selectEmployeeInfo(any(), any());
  }
}
