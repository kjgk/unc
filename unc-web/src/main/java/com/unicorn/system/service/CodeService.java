package com.unicorn.system.service;

import com.unicorn.core.domain.po.Code;
import com.unicorn.core.repository.CodeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@AllArgsConstructor
public class CodeService {

    private CodeRepository codeRepository;

    public Code getRootCode() {

        return codeRepository.findRoot();
    }

    public Code getCode(Long objectId) {

        return codeRepository.get(objectId);
    }

    public Code getCodeByTag(String tag) {

        List<Code> codeList = codeRepository.findByTag(tag);
        return codeList.get(0);
    }

    public Code saveCode(Code code) {

        Code current;
        if (StringUtils.isEmpty(code.getObjectId())) {
            Integer maxOrderNo = codeRepository.findMaxOrderNo(code.getParentId());
            code.setOrderNo(maxOrderNo == null ? 1 : maxOrderNo + 1);
            current = codeRepository.save(code);
        } else {
            current = codeRepository.get(code.getObjectId());
            current.setName(code.getName());
            current.setTag(code.getTag());
            current.setDescription(code.getDescription());
        }
        return current;
    }

    public void deleteCode(Long objectId) {

        Code code = codeRepository.get(objectId);
        if (!CollectionUtils.isEmpty(code.getChildList())) {
            for (Code child : code.getChildList()) {
                deleteCode(child.getObjectId());
            }
        }
        codeRepository.deleteById(objectId);

        codeRepository.minusOrderNo(code.getParentId(), code.getOrderNo());
    }

    public Map moveCode(Long objectId, Long targetId, Integer position) {

        return codeRepository.move(objectId, targetId, position);
    }
}
