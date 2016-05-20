package com.unicorn.system.service;

import com.unicorn.core.query.QueryInfo;
import com.unicorn.system.domain.po.Authority;
import com.unicorn.system.reposiory.AuthorityRepository;
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

    public Authority getAuthority(String id) {

        return authorityRepository.findOne(id);
    }

    public void saveAuthority(Authority authority) {

        if (StringUtils.isEmpty(authority.getObjectId())) {
            authorityRepository.save(authority);
        } else {
            getAuthority(authority.getObjectId()).merge(authority);
        }
    }

    public void deleteAuthority(String objectId) {

        authorityRepository.logicDelete(objectId);
    }

}
