package com.unicorn.system.service;

import com.unicorn.system.domain.po.Code;
import com.unicorn.system.reposiory.CodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CodeService {

    @Autowired
    private CodeRepository codeRepository;


    public Code getRootCode() {

        return codeRepository.findByParent(null).get(0);
    }

    public Code getCode(String id) {

        return codeRepository.findOne(id);
    }

    public Code getCodeByTag(String tag) {

        List<Code> codeList = codeRepository.findByTag(tag);
        return codeList.get(0);
    }

    public void saveCode(Code code) {

        if (StringUtils.isEmpty(code.getObjectId())) {
            codeRepository.save(code);
        } else {
            getCode(code.getObjectId()).merge(code);
        }
    }

    public void deleteCode(String objectId) {

        codeRepository.logicDelete(objectId);
    }

}
