package com.unicorn.system.service;

import com.unicorn.system.domain.po.Code;
import com.unicorn.system.repository.CodeRepository;
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

        return codeRepository.findRoot();
    }

    public Code getCode(String id) {

        return codeRepository.findOne(id);
    }

    public Code getCodeByTag(String tag) {

        List<Code> codeList = codeRepository.findByTag(tag);
        return codeList.get(0);
    }

    public Code getCodeByEnum(Enum tag) {

        List<Code> codeList = codeRepository.findByEnum(tag.getClass().getSimpleName(), tag.name());
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
            current.setOrderNo(code.getOrderNo());
            current.setDescription(code.getDescription());
            if (code.getParent() != null) {
                current.setParent(code.getParent());
            }
        }
        return current;
    }

    public void deleteCode(String objectId) {

        codeRepository.logicDelete(objectId);
    }

}
