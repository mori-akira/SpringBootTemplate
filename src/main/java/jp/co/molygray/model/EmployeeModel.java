package jp.co.molygray.model;

import java.time.OffsetDateTime;
import java.util.List;
import jp.co.molygray.enums.GenderEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 社員モデルクラス
 *
 * @author Moriaki Kogure
 * @version 0.0.1
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeModel {

  /**
   * 社員住所モデルクラス
   *
   * @author Moriaki Kogure
   * @version 0.0.1
   */
  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class EmployeeAddressModel {

    /** 社員住所ID */
    private Long employeeAddressId;
    /** 排他フラグ */
    private String exclusiveFlg;
    /** 国 */
    private String country;
    /** 県 */
    private String prefecture;
    /** 市 */
    private String city;
    /** 区 */
    private String ward;
    /** 詳細1 */
    private String detail1;
    /** 詳細2 */
    private String detail2;
  }

  /** 社員ID */
  private Long employeeId;
  /** 排他フラグ */
  private String exclusiveFlg;
  /** 社員番号 */
  private String employeeNumber;
  /** 姓 */
  private String sei;
  /** 名 */
  private String mei;
  /** 姓カナ */
  private String seiKana;
  /** 名カナ */
  private String meiKana;
  /** 性別 */
  private GenderEnum gender;
  /** 生年月日 */
  private OffsetDateTime birthDate;
  /** 入社日 */
  private OffsetDateTime hireDate;
  /** 部署 */
  private DepartmentModel department;
  /** 社員住所 */
  private EmployeeAddressModel employeeAddress;
  /** 社員所持資格 */
  private List<QualificationModel> qualificationList;
}
