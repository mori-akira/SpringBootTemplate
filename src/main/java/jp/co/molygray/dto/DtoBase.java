package jp.co.molygray.dto;

import java.time.ZonedDateTime;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Dto共通部
 *
 * @author Moriaki Kogure
 * @version 0.0.1
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class DtoBase {

  /** 削除フラグ */
  private boolean deleteFlg;
  /** 排他フラグ */
  private String exclusiveFlg;
  /** 登録日時 */
  private ZonedDateTime insertDatetime;
  /** 登録者 */
  private long insertUser;
  /** 登録機能 */
  private String insertFunction;
  /** 編集日時 */
  private ZonedDateTime updateDatetime;
  /** 編集者 */
  private long updateUser;
  /** 編集機能 */
  private String updateFunctionp;
  /** 削除日時 */
  private ZonedDateTime deleteDatetime;
  /** 削除者 */
  private Long deleteUser;
  /** 削除機能 */
  private String deleteFunction;
}
