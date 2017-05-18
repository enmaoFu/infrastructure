package com.infrastructure.service.daily;

import com.infrastructure.dao.daily.RegimeMapper;
import com.infrastructure.dao.system.SysFileMapper;
import com.infrastructure.entity.daily.Regime;
import com.infrastructure.entity.system.SysFile;
import com.infrastructure.common.repository.BaseMapper;
import com.infrastructure.common.service.BaseService;
import net.oschina.archx.mybatis.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Created by 谭永强 on 2016/4/25.
 */
@Service
public class RegimeService extends BaseService<Regime,String> implements IRegimeService{

    @Autowired
    private RegimeMapper regimeMapper;
    @Autowired
    private SysFileMapper sysFileMapper;

    @Override
    public BaseMapper<Regime, String> getMapper() {
        return regimeMapper;
    }

    /**
     * 制度查询
     * @param regime
     * @param page
     * @return
     */
    @Override
    public List<Regime> load(Regime regime, Pagination page) {
        return regimeMapper.load(regime,page);
    }

    /**
     * 新增
     * @param regime
     * @param sysFile
     * @return
     */
    @Override
    public int insert(Regime regime, SysFile sysFile) {
        if(sysFile != null && sysFile.getId() != null){
            //保存文件信息
            sysFileMapper.insert(sysFile);
        }
        return super.insert(regime);
    }

    /**
     * 删除
     * @param ids
     * @param path
     * @return
     */
    @Override
    public int deleteArray(String[] ids, String path) {
        for (String id : ids) {
            //查询申请数据
            Regime regime = super.select(id);
            //查询附件
            SysFile sysFile = sysFileMapper.select(regime.getAttachment());
            if(sysFile != null){
                File file = new File(path,sysFile.getNowFileName());
                //删除附件数据
                sysFileMapper.delete(regime.getAttachment());
                file.delete();
            }
        }
        return regimeMapper.deleteArray(ids);
    }

    /**
     * 修改
     * @param regime
     * @param sysFile
     * @param path
     * @return
     */
    @Override
    public int update(Regime regime, SysFile sysFile, String path) {
        Regime re = super.select(regime.getId());
        String attachmentId = re.getAttachment();
        if(!StringUtils.isEmpty(sysFile.getId())){
            if(!StringUtils.isEmpty(attachmentId) && !"1".equals(attachmentId)){
                SysFile sFile = sysFileMapper.select(attachmentId);
                File file = new File(path,sFile.getNowFileName());
                if(file.exists()) file.delete();
                sysFileMapper.delete(attachmentId);
            }
            if(!"1".equals(sysFile.getId())){
                sysFileMapper.insert(sysFile);
            }
        }
        return super.update(regime);
    }

    /**
     * 根据所属模块查询制度
     * model 模块ID
     * companyId 公司ID
     * @return
     */
    @Override
    public Regime findByModel(Map<String,Object> map) {
        return regimeMapper.findByModel(map);
    }
}
