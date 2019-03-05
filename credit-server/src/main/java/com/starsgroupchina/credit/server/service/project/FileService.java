package com.starsgroupchina.credit.server.service.project;

import com.google.common.collect.Lists;
import com.starsgroupchina.common.base.AbstractService;
import com.starsgroupchina.common.utils.Utils;
import com.starsgroupchina.credit.bean.enums.FileType;
import com.starsgroupchina.credit.bean.extend.FileInspectExtend;
import com.starsgroupchina.credit.bean.extend.FileResourceExtend;
import com.starsgroupchina.credit.bean.extend.OrgFileExtend;
import com.starsgroupchina.credit.bean.model.*;
import com.starsgroupchina.credit.server.service.system.OrgService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

/**
  * @Author: QinHaoHao
  * @Description: 文件管理
  * @Date: Created in 13:54 2018/7/2
  * @Modifed By:
  */
@Slf4j
@Service
public class FileService extends AbstractService<FileResource,FileResourceExample> {

    @Value("${image-url}")
    private String imageUrl;

    @Autowired
    private OrgService.OrgFileService orgFileService;
    @Autowired
    private FileInspectService fileInspectService;

    /**
     * 根据projectNo获取当前文件列表
     *
     * @param projectNo
     * @return
     */
    public List<OrgFileExtend> getFiles(String projectNo) {
        FileResourceExample fileResourceExample = new FileResourceExample();
        fileResourceExample.createCriteria().andProjectNoEqualTo(projectNo);
        List<FileResource> files = query(fileResourceExample).collect(toList());
        List<FileResourceExtend> fileResourceExtends = Lists.newArrayList();
        List<OrgFileExtend> result = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(files)) {
            files.stream().filter(fileResource -> !FileType.AUDIT_FILE.getKey().equals(fileResource.getOrgFileId())).
                    filter(fileResource -> !FileType.OTHER_FILE.getKey().equals(fileResource.getOrgFileId())).
                    filter(fileResource -> !FileType.DECLARE_FILE.getKey().equals(fileResource.getOrgFileId())).
                    filter(fileResource -> !FileType.INVESTIGATION_FILEA.getKey().equals(fileResource.getOrgFileId())).
                    filter(fileResource -> !FileType.QUALITY_AUDIT_FILE.getKey().equals(fileResource.getOrgFileId())).
                    filter(fileResource -> !FileType.QUALITY_RECHECK_FILE.getKey().equals(fileResource.getOrgFileId())).forEach(fileResource -> {
                FileResourceExtend fileResourceExtend = new FileResourceExtend(fileResource);
                OrgFile orgFile = orgFileService.getById(fileResource.getOrgFileId());
                fileResourceExtend.setOrgFile(orgFile);
                fileResourceExtend.setFileUrl(imageUrl + fileResource.getFileKey());
                fileResourceExtends.add(fileResourceExtend);
            });
            if (CollectionUtils.isEmpty(fileResourceExtends)) {
                return result;
            }
            Map<Integer, List<FileResourceExtend>> map = fileResourceExtends.stream().collect(groupingBy(fileResourceExtend -> fileResourceExtend.getOrgFile().getId()));
            for (Map.Entry<Integer, List<FileResourceExtend>> entry : map.entrySet()) {
                List<FileResourceExtend> value = entry.getValue();
                OrgFileExtend orgFileExtend = new OrgFileExtend(value.get(0).getOrgFile());
                value.forEach(fileResourceExtend -> {
                    fileResourceExtend.setOrgFile(null);
                });
                orgFileExtend.setFileResourceList(value);
                result.add(orgFileExtend);
            }
        }
        return result;
    }

    /**
     * 根据projectNo获取资料审查列表
     *
     * @param projectNo
     * @return
     */
    public List<FileInspectExtend> getInspectFiles(String projectNo) {
        List<OrgFileExtend> files = getFiles(projectNo);
        ProjectFileInspectExample authAttachmentExample = new ProjectFileInspectExample();
        List<FileInspectExtend> result = Lists.newArrayList();
        files.forEach(orgFileExtend -> {
            FileInspectExtend fileInspectExtend = null;
            authAttachmentExample.clear();
            authAttachmentExample.createCriteria().andProjectNoEqualTo(projectNo).andOrgFileIdEqualTo(orgFileExtend.getId());
            ProjectFileInspect fileInspect = Utils.getFirst(fileInspectService.query(authAttachmentExample));
            if (fileInspect == null) {
                fileInspectExtend = new FileInspectExtend();
            } else {
                fileInspectExtend = new FileInspectExtend(fileInspect);
            }
            fileInspectExtend.setOrgFileExtend(orgFileExtend);
            result.add(fileInspectExtend);
        });
        return result;
    }
}
