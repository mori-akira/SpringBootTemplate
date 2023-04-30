package jp.co.molygray.util;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import jp.co.molygray.dto.DtoBase;
import jp.co.molygray.util.accessor.FunctionIdAccessor;
import jp.co.molygray.util.accessor.RequestTimeAccessor;
import jp.co.molygray.util.accessor.UserInfoAccessor;

/**
 * Dtoインスタンスの共通フィールドを設定するクラス
 *
 * @author Moriaki Kogure
 * @version 0.0.1
 */
@Component
public class DtoCommonFieldSetter {

  /** リクエスト時間のアクセサ */
  @Autowired
  private RequestTimeAccessor requestTimeAccessor;
  /** ユーザ情報のアクセサ */
  @Autowired
  private UserInfoAccessor userInfoAccessor;
  /** 機能IDのアクセサ */
  @Autowired
  private FunctionIdAccessor functionIdAccessor;

  /**
   * 登録時の共通フィールドを設定するメソッド
   * <p>
   * 登録情報、更新情報に共通フィールドを設定し、削除情報にnullを設定する。
   * </p>
   *
   * @param dto Dtoインスタンス
   */
  public void setCommonFieldWhenInsert(DtoBase dto) {
    dto.setDeleteFlg(false);
    dto.setNewExclusiveFlg(UUID.randomUUID().toString().replace("-", ""));
    dto.setInsertDatetime(requestTimeAccessor.getRequestTime().toOffsetDateTime());
    dto.setInsertUser(userInfoAccessor.getUserInfo().getUserId());
    dto.setInsertFunction(functionIdAccessor.getFunctionId().toString());
    dto.setUpdateDatetime(requestTimeAccessor.getRequestTime().toOffsetDateTime());
    dto.setUpdateUser(userInfoAccessor.getUserInfo().getUserId());
    dto.setUpdateFunction(functionIdAccessor.getFunctionId().toString());
    dto.setDeleteDatetime(null);
    dto.setDeleteUser(null);
    dto.setDeleteFunction(null);
  }

  /**
   * 更新時の共通フィールドを設定するメソッド
   * <p>
   * 更新情報に共通フィールドを設定する。
   * </p>
   *
   * @param dto Dtoインスタンス
   */
  public void setCommonFieldWhenUpdate(DtoBase dto) {
    dto.setNewExclusiveFlg(UUID.randomUUID().toString().replace("-", ""));
    dto.setUpdateDatetime(requestTimeAccessor.getRequestTime().toOffsetDateTime());
    dto.setUpdateUser(userInfoAccessor.getUserInfo().getUserId());
    dto.setUpdateFunction(functionIdAccessor.getFunctionId().toString());
  }

  /**
   * 削除時の共通フィールドを設定するメソッド
   * <p>
   * 削除情報に共通フィールドを設定する。
   * </p>
   *
   * @param dto Dtoインスタンス
   */
  public void setCommonFieldWhenDelete(DtoBase dto) {
    dto.setDeleteFlg(true);
    dto.setNewExclusiveFlg(UUID.randomUUID().toString().replace("-", ""));
    dto.setDeleteDatetime(requestTimeAccessor.getRequestTime().toOffsetDateTime());
    dto.setDeleteUser(userInfoAccessor.getUserInfo().getUserId());
    dto.setDeleteFunction(functionIdAccessor.getFunctionId().toString());
  }
}
