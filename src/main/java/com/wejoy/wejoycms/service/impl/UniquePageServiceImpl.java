package com.wejoy.wejoycms.service.impl;

import com.wejoy.wejoycms.entity.TSubject;
import com.wejoy.wejoycms.mapper.SubjectMapper;
import com.wejoy.wejoycms.service.UniquePageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UniquePageServiceImpl implements UniquePageService {

    @Autowired
    SubjectMapper subjectMapper;

    @Override
    public Map<String, Object> getSomeSubject(List<Long> idList) {
        Map<String, Object> map = new HashMap<>();
        List<TSubject> someSubject = subjectMapper.getSomeSubject(idList);
        for (TSubject subject :
                someSubject) {
            map.put(subject.getSubjectCode(), subject);
        }
        return map;
    }

}
