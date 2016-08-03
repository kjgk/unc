package com.unicorn.system.service;

import com.unicorn.system.domain.po.Code;
import com.unicorn.system.repository.CodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public void saveCode(Code code) {

        codeRepository.save(code);
    }

    public void deleteCode(String objectId) {

        codeRepository.logicDelete(objectId);
    }

}
