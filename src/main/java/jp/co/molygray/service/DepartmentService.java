package jp.co.molygray.service;

import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jp.co.molygray.dao.entity.DepartmentDao;
import jp.co.molygray.dto.DepartmentDto;
import jp.co.molygray.model.DepartmentModel;

/**
 * 部署サービスクラス
 *
 * @author Moriaki Kogure
 * @version 0.0.1
 */
@Service
public class DepartmentService {

  /** 部署Dao */
  @Autowired
  private DepartmentDao departmentDao;

  /**
   * 部署を一件取得するメソッド
   *
   * @param departmentId 部署ID
   * @return 部署 (取得できない場合はnull)
   */
  public DepartmentModel get(long departmentId) {
    return departmentDao.select(departmentId).map(e -> {
      DepartmentModel model = new DepartmentModel();
      BeanUtils.copyProperties(e, model);
      return model;
    }).orElse(null);
  }

  /**
   * 部署を一覧検索するメソッド
   *
   * @param parentparentDepartmentIdList 親部署IDリスト
   * @param departmentName 部署名検索テキスト
   * @param departmentFullName 部署正式名検索テキスト
   * @return 部署リスト (取得できない場合は空リスト)
   */
  public List<DepartmentModel> searchList(List<Long> parentparentDepartmentIdList,
      String departmentName,
      String departmentFullName) {
    List<DepartmentDto> dtoList = departmentDao.searchList(parentparentDepartmentIdList,
        departmentName, departmentFullName);
    return dtoList.stream()
        .map(e -> {
          DepartmentModel model = new DepartmentModel();
          BeanUtils.copyProperties(e, model);
          return model;
        })
        .toList();
  }
}
