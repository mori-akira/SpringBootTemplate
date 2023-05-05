package jp.co.molygray.dao.entity;

import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import jp.co.molygray.annotation.WithExclusiveCheck;
import jp.co.molygray.dto.QualificationDto;

/**
 * 資格DAOクラス
 *
 * @author Moriaki Kogure
 * @version 0.0.1
 */
@Mapper
public interface QualificationDao {

  /**
   * 一件選択メソッド
   *
   * @param qualificationId 資格ID
   * @return 資格
   */
  @Select("SELECT * FROM qualification WHERE qualification_id = #{qualificationId} AND delete_flg = FALSE")
  public Optional<QualificationDto> select(@Param("qualificationId") long qualificationId);

  /**
   * 一覧選択メソッド
   *
   * @return 資格リスト
   */
  @Select("SELECT * FROM qualification WHERE delete_flg = FALSE ORDER BY qualification_id")
  public List<QualificationDto> selectList();

  /**
   * 登録メソッド
   *
   * @param dto 資格Dtoインスタンス
   * @return 登録件数
   */
  public Long insert(QualificationDto dto);

  /**
   * 更新メソッド
   *
   * @param dto 資格Dtoインスタンス
   * @return 更新件数
   */
  @WithExclusiveCheck
  public int update(QualificationDto dto);

  /**
   * 削除メソッド
   *
   * @param employeeId 資格ID
   * @param exclusiveFlg 排他フラグ
   * @return 削除件数
   */
  @WithExclusiveCheck
  public int delete(@Param("qualificationId") long qualificationId,
      @Param("exclusiveFlg") String exclusiveFlg);
}
