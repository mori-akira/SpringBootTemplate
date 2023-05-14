package jp.co.molygray.dao.entity;

import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
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
  public Optional<QualificationDto> select(@Param("qualificationId") long qualificationId);

  /**
   * 一覧選択メソッド
   *
   * @return 資格リスト
   */
  public List<QualificationDto> selectList();

  /**
   * 一覧検索メソッド
   *
   * @param qualificationName 資格名
   * @param qualificationAbbreviatedName 資格省略名
   * @param validPeriodYears 有効年数
   * @param provider 提供組織
   * @return 資格リスト
   */
  public List<QualificationDto> searchList(
      @Param("qualificationName") String qualificationName,
      @Param("qualificationAbbreviatedName") String qualificationAbbreviatedName,
      @Param("validPeriodYears") Integer validPeriodYears,
      @Param("provider") String provider);

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
