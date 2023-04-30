package jp.co.molygray.dto;

import java.time.OffsetDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Dto共通部
 *
 * @author Moriaki Kogure
 * @version 0.0.1
 */
@Data
@SuperBuilder
@NoArgsConstructor
public class DtoBase {

  /** 削除フラグ */
  private boolean deleteFlg;
  /** 排他フラグ (条件) */
  private String exclusiveFlg;
  /** 排他フラグ (新規) */
  private String newExclusiveFlg;
  /** 登録日時 */
  private OffsetDateTime insertDatetime;
  /** 登録者 */
  private Long insertUser;
  /** 登録機能 */
  private String insertFunction;
  /** 編集日時 */
  private OffsetDateTime updateDatetime;
  /** 編集者 */
  private Long updateUser;
  /** 編集機能 */
  private String updateFunction;
  /** 削除日時 */
  private OffsetDateTime deleteDatetime;
  /** 削除者 */
  private Long deleteUser;
  /** 削除機能 */
  private String deleteFunction;
}
