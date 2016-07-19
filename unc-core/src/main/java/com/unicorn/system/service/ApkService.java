package com.unicorn.system.service;

import com.unicorn.core.exception.ServiceException;
import com.unicorn.core.query.QueryInfo;
import com.unicorn.system.domain.po.Apk;
import com.unicorn.system.domain.po.ApkVersion;
import com.unicorn.system.repository.ApkRepository;
import com.unicorn.system.repository.ApkVersionRepository;
import com.unicorn.utils.Identities;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;


@Service
@Transactional
public class ApkService {

    @Autowired
    private ApkRepository apkRepository;

    @Autowired
    private ApkVersionRepository apkVersionRepository;

    @Autowired
    private EnvironmentService environmentService;

    public Page<Apk> getApk(QueryInfo queryInfo) {

        return apkRepository.findAll(queryInfo);
    }

    public Apk getApk(String objectId) {

        return apkRepository.findOne(objectId);
    }

    public void saveApk(Apk apk) throws ServiceException {

        if (checkApkTag(apk)) {
            if (StringUtils.isEmpty(apk.getObjectId())) {
                apkRepository.save(apk);
            } else {
                getApk(apk.getObjectId()).merge(apk);
            }
        } else {
            throw new ServiceException("安装包标识“" + apk.getTag() + "”已经存在！");
        }
    }

    public void deleteApk(String objectId) {

        apkRepository.logicDelete(objectId);
    }

    public void saveApkVersion(ApkVersion apkVersion) throws IOException {

        String filename = "/" + Identities.randomBase62(12);

        File in = new File(environmentService.getTempPath() + "/" + apkVersion.getFilename());
        File out = new File(environmentService.getUploadPath() + "/apk" + filename);
        FileCopyUtils.copy(in, out);

        apkVersion.setFilename(filename);
        apkVersion.setCode(Identities.randomBase62(6));
        apkVersionRepository.save(apkVersion);

    }

    public Apk getApkByCode(String code) {

        Apk apk = apkRepository.findByTag(code);
        if (apk == null) {
            ApkVersion apkVersion = apkVersionRepository.findByCode(code);
            if (apkVersion != null) {
                return getApk(apkVersion.getApkId());
            }
        }
        return apk;
    }

    public File getFileByCode(String code) {

        ApkVersion apkVersion = apkVersionRepository.findByCode(code);
        if (apkVersion != null) {
            return new File(environmentService.getUploadPath() + "/apk" + apkVersion.getFilename());
        }
        return null;
    }

    /**
     * 根据安装包标识获取最新的版本号
     *
     * @param tag
     * @return
     */
    public ApkVersion getNewestVersion(String tag) {

        Apk apk = apkRepository.findByTag(tag);
        if (apk == null || CollectionUtils.isEmpty(apk.getVersionList())) {
            return null;
        } else {
            return apk.getVersionList().get(0);
        }
    }

    private boolean checkApkTag(Apk apk) {

        Apk temp = apkRepository.findByTag(apk.getTag());
        if (temp == null || StringUtils.equals(temp.getObjectId(), apk.getObjectId())) {
            return true;
        }
        return false;
    }

}
