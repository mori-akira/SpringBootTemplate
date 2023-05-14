package jp.co.molygray.enums.typeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import jp.co.molygray.enums.GenderEnum;

/**
 * {@link GenderEnum}の型変換ハンドラクラス
 *
 * @author Moriaki Kogure
 * @version 0.0.1
 */
@MappedTypes(GenderEnum.class)
public class GenderTypeHandler extends BaseTypeHandler<GenderEnum> {

  /**
   * {@inheritDoc}
   */
  @Override
  public void setNonNullParameter(PreparedStatement ps, int i, GenderEnum parameter,
      JdbcType jdbcType)
      throws SQLException {
    ps.setInt(i, parameter.getValue());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public GenderEnum getNullableResult(ResultSet rs, String columnName)
      throws SQLException {
    return GenderEnum.of(rs.getInt(columnName));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public GenderEnum getNullableResult(ResultSet rs, int columnIndex)
      throws SQLException {
    return GenderEnum.of(rs.getInt(columnIndex));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public GenderEnum getNullableResult(CallableStatement cs, int columnIndex)
      throws SQLException {
    return GenderEnum.of(cs.getInt(columnIndex));
  }

}
