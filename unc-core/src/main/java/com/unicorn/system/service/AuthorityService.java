package com.unicorn.system.service;

import com.unicorn.core.domain.vo.BasicInfo;
import com.unicorn.core.query.QueryInfo;
import com.unicorn.system.domain.po.Authority;
import com.unicorn.system.repository.AuthorityRepository;
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

    public Authority getAuthority(String id) {

        return authorityRepository.get(id);
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

    public void deleteAuthority(String objectId) {

        authorityRepository.deleteById(objectId);
    }

    public void deleteAuthority(List<String> objectIds) {

        objectIds.forEach(this::deleteAuthority);
    }
}
