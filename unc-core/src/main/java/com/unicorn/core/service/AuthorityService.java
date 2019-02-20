package com.unicorn.core.service;

import com.unicorn.common.domain.vo.BasicInfo;
import com.unicorn.common.query.QueryInfo;
import com.unicorn.common.domain.po.Authority;
import com.unicorn.core.repository.AuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class AuthorityService {

    @Autowired
    private AuthorityRepository authorityRepository;

    public Page<Authority> getAuthority(QueryInfo queryInfo) {

        return authorityRepository.findAll(queryInfo);
    }

    public List<BasicInfo> getAuthority() {

        return authorityRepository.list();
    }

    public Authority getAuthority(Long objectId) {

        return authorityRepository.get(objectId);
    }

    public Authority saveAuthority(Authority authority) {

        Authority current;
        if (StringUtils.isEmpty(authority.getObjectId())) {
            current = authorityRepository.save(authority);
        } else {
            current = authorityRepository.get(authority.getObjectId());
            current.setName(authority.getName());
            current.setDescription(authority.getDescription());
            current.setTag(authority.getTag());
        }
        return current;
    }

    public void deleteAuthority(Long objectId) {

        authorityRepository.deleteById(objectId);
    }

    public void deleteAuthority(List<Long> objectIds) {

        objectIds.forEach(this::deleteAuthority);
    }
}
