package com.infrastructure.service.order;

import com.infrastructure.dao.order.InstructionsMapper;
import com.infrastructure.dao.system.SysFileMapper;
import com.infrastructure.entity.order.Instructions;
import com.infrastructure.entity.system.SysFile;
import com.infrastructure.common.able.Searchable;
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
 * 指令-接口实现
 * User: suyl
 * Date: 2016/6/22
 */
@Service
public class InstructionsService extends BaseService<Instructions,String> implements IInstructionsService{

    @Autowired
    private InstructionsMapper instructionsMapper;
    @Autowired
    private SysFileMapper sysFileMapper;

    @Override
    public BaseMapper<Instructions, String> getMapper() {
        return instructionsMapper;
    }

    @Override
    public int addInstructions(Instructions Instructions, SysFile sysFile) {
        if(sysFile != null && sysFile.getId() != null){
            //保存文件信息
            sysFileMapper.insert(sysFile);
        }
        int result = instructionsMapper.insert(Instructions);
        return result;
    }

    @Override
    public int modifyInstructions(Instructions Instructions, SysFile sysFile, String path) {
        if(sysFile != null){
            //查询合同信息
            Instructions con = instructionsMapper.getById(Instructions.getId());
            String attachmentId = con.getAttachment();
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
        }
        return instructionsMapper.update(Instructions);
    }

    @Override
    public int confirm(Map<String, Object> params) {
        return instructionsMapper.confirm(params);
    }

    @Override
    public int deleteBatchInstructions(String[] ids, String path) {
        for (String id : ids) {
            //查询合同信息
            Instructions contract = instructionsMapper.getById(id);
            //查询附件
            SysFile sysFile = sysFileMapper.select(contract.getAttachment());
            if(sysFile != null){
                File file = new File(path,sysFile.getNowFileName());
                //删除附件数据
                sysFileMapper.delete(contract.getAttachment());
                //删除文件
                file.delete();
            }
        }
        //删除合同
        int result = instructionsMapper.deleteBatch(ids);
        return result;
    }

    @Override
    public Instructions getInstructionsById(String id) {
        return instructionsMapper.getById(id);
    }

    @Override
    public List<Instructions> queryInstructionsList(Instructions searchable, Pagination page) {
        return instructionsMapper.queryList(searchable,page);
    }

    @Override
    public List<Instructions> queryInstReceiveObjList(Searchable searchable, Pagination page) {
        return instructionsMapper.queryInstReceiveObjList(searchable,page);
    }

    @Override
    public List<Instructions> queryForwardList(Searchable searchable, Pagination page) {
        return instructionsMapper.queryForwardList(searchable,page);
    }
}
