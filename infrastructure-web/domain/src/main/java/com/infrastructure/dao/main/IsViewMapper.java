package com.infrastructure.dao.main;

import com.infrastructure.common.repository.BaseMapper;
import com.infrastructure.entity.main.IsView;
import org.springframework.stereotype.Repository;

/**
 * Created by 谭永强 on 2016/5/6.
 */
@Repository
public interface IsViewMapper extends BaseMapper<IsView,String> {

    IsView findByView(IsView isView);

}
