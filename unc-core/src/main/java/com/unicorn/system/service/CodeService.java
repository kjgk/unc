package com.unicorn.system.service;

import com.unicorn.system.domain.po.Code;
import com.unicorn.system.repository.CodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CodeService {

    @Autowired
    private CodeRepository codeRepository;


    public Code getRootCode() {

        return codeRepository.findRoot();
    }

    public Code getCode(String id) {

        return codeRepository.findOne(id);
    }

    public Code getCodeByTag(String tag) {

        List<Code> codeList = codeRepository.findByTag(tag);
        return codeList.get(0);
    }

    public Code saveCode(Code code) {

        Code current;
        if (StringUtils.isEmpty(code.getObjectId())) {
            current = codeRepository.save(code);
        } else {
            current = codeRepository.findOne(code.getObjectId());
            current.setName(code.getName());
            current.setTag(code.getTag());
            current.setDescription(code.getDescription());
        }
        return current;
    }

    public void deleteCode(String objectId) {

        Code code = codeRepository.findOne(objectId);
        if (!CollectionUtils.isEmpty(code.getChildList())) {
            for (Code child : code.getChildList()) {
                deleteCode(child.getObjectId());
            }
        }
        codeRepository.delete(objectId);
    }
}
