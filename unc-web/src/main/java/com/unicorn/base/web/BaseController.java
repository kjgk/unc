package com.unicorn.base.web;

import com.unicorn.system.DateEditor;
import com.unicorn.core.domain.DefaultRecursive;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.util.*;

public class BaseController {

    @InitBinder
    public void initBinder(WebDataBinder binder) {

        binder.registerCustomEditor(Date.class, new DateEditor());
    }

    protected List buildTreeData(List childList, Boolean fetchChild) {

        return buildTreeData(childList, fetchChild, null);
    }

    protected List buildTreeData(List<DefaultRecursive> childList, Boolean fetchChild, List<Long> filter) {
        List result = new ArrayList();
        if (!CollectionUtils.isEmpty(childList)) {
            for (DefaultRecursive child : childList) {
                Map data = new HashMap();
                data.put("objectId", child.getObjectId());
                data.put("name", child.getName());
                data.put("leaf", CollectionUtils.isEmpty(child.getChildList()) ? 1 : 0);
                if (fetchChild && (filter == null || filter.contains(child.getObjectId()))) {
                    data.put("children", buildTreeData(child.getChildList(), fetchChild, filter));
                }
                result.add(data);
            }
        }
        return result;
    }

    private List<DefaultRecursive> getTreePath(DefaultRecursive entity) {

        List<DefaultRecursive> treePath = new ArrayList();
        treePath.add(entity);
        if (entity.getParent() != null) {
            treePath.addAll(getTreePath(entity.getParent()));
        }
        return treePath;
    }

    protected List buildTreeDataBackward(DefaultRecursive entity) {

        if (entity == null || entity.getDeleted() == 1) {
            return new ArrayList();
        }

        List<DefaultRecursive> treePath = getTreePath(entity);
        Collections.reverse(treePath);
        List<Long> filter = new ArrayList();
        for (DefaultRecursive item : treePath) {
            filter.add(item.getObjectId());
        }
        List<DefaultRecursive> list = new ArrayList();
        list.add(treePath.get(0));
        filter.remove(filter.size() - 1);
        return buildTreeData(list, true, filter);
    }
}
